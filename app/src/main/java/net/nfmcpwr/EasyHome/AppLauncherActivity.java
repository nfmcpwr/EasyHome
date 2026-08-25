package net.nfmcpwr.EasyHome;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppLauncherActivity extends ComponentActivity
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        List<PackageInfo> l = getPackageManager().getInstalledPackages(PackageManager.GET_META_DATA);
        l.removeIf(info -> getPackageManager().getLaunchIntentForPackage(info.packageName) == null);
        
        RecyclerView launcherItemsView = findViewById(R.id.launcherItems);
        launcherItemsView.setAdapter(new AppLauncherViewAdapter(this, l));
        launcherItemsView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        
        ImageButton prevButton = findViewById(R.id.prev_button);
        prevButton.setOnClickListener(view ->
        {
            finish();
        });
    }
}
