package net.nfmcpwr.EasyHome;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;

import io.github.z4kn4fein.semver.StringExtensionsKt;
import io.github.z4kn4fein.semver.VersionFormatException;

public class CheckUpdateActivity extends ComponentActivity
{
    private TextView updateStatus;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);
        
        setContentView(R.layout.activity_check_update);
        
        this.updateStatus = findViewById(R.id.updateStatus);
        
        boolean skip = false;
        try
        {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);
            this.updateStatus.append(String.format(Locale.getDefault(), "Current version: %s(%d) \n", info.versionName, info.getLongVersionCode()));
        }
        catch (PackageManager.NameNotFoundException e)
        {
            Log.e(CheckUpdateActivity.class.getSimpleName(), e.getMessage(), e);
            skip = true;
        }
        
        if (!skip)
        {
            boolean result = CheckUpdate();
            if (result)
            {
                this.updateStatus.append("Update completed\n");
            }
        }
        
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        
        ImageButton prevButton = findViewById(R.id.prev_button);
        prevButton.setVisibility(View.VISIBLE);
        prevButton.setOnClickListener(view -> finish());
    }
    
    private boolean CheckUpdate()
    {
        this.updateStatus.append("Checking update\n");
        GitHubAPIResponse response = GitHubAPIResponse.GetLatestRelease();
        
        if (response.IsError)
        {
            this.updateStatus.append(String.format("API request failed: %s\n", response.ErrorMessage));
            return false;
        }
        
        this.updateStatus.append(String.format("Latest release: %s\n", response.Name));
        
        try
        {
            String currentVersion = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_META_DATA).versionName;
            if (StringExtensionsKt.toVersion(response.Name, false)
                .compareTo(StringExtensionsKt.toVersion(currentVersion, false)) <= 0)
            {
                this.updateStatus.append("Latest version installed\n");
                return false;
            }
        }
        catch (PackageManager.NameNotFoundException | VersionFormatException e)
        {
            Log.e(CheckUpdateActivity.class.getSimpleName(), e.getMessage(), e);
            return false;
        }
        
        return UpdateApp(response);
    }
    
    private boolean UpdateApp(GitHubAPIResponse response)
    {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
        {
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (Build.VERSION_CODES.R <= Build.VERSION.SDK_INT)
        {
            if (!Environment.isExternalStorageManager())
            {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        
        File updateDir = new File(Environment.getExternalStorageDirectory()
            .getPath(), "EasyHome/Update");
        if (!updateDir.exists())
        {
            if (!updateDir.mkdirs())
            {
                Toast.makeText(this, "Create directory failed", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        
        this.updateStatus.append("Downloading update\n");
        
        File updateApk = new File(Environment.getExternalStorageDirectory()
            .getPath(), "EasyHome/Update/" + response.Assets.get(0).FileName);
        
        try (DataInputStream is = new DataInputStream(new URL(response.Assets.get(0).DownloadUrl).openStream()))
        {
            byte[] buffer = new byte[1024];
            FileOutputStream os = new FileOutputStream(updateApk);
            while (0 < is.read(buffer))
            {
                os.write(buffer);
            }
            
            os.close();
        }
        catch (IOException e)
        {
            Toast.makeText(this, "Failed to download update file", Toast.LENGTH_SHORT).show();
            Log.e(CheckUpdateActivity.class.getSimpleName(), e.getMessage(), e);
            return false;
        }
        
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(FileProvider.getUriForFile(this, getPackageName() + ".provider", updateApk), "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        
        startActivity(i);
        
        return true;
    }
}
