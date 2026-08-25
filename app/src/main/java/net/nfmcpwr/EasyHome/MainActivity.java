package net.nfmcpwr.EasyHome;

import static android.app.admin.DevicePolicyManager.LOCK_TASK_FEATURE_HOME;
import static android.app.admin.DevicePolicyManager.LOCK_TASK_FEATURE_OVERVIEW;
import static android.app.admin.DevicePolicyManager.LOCK_TASK_FEATURE_SYSTEM_INFO;

import android.Manifest;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        DevicePolicyManager dpm = getSystemService(DevicePolicyManager.class);
        ComponentName cpn = new ComponentName(this, DeviceOwnerReceiver.class);
        
        dpm.setLockTaskPackages(cpn, new String[]{
            getPackageName()
        });
        
        dpm.setLockTaskFeatures(cpn, LOCK_TASK_FEATURE_HOME | LOCK_TASK_FEATURE_OVERVIEW | LOCK_TASK_FEATURE_SYSTEM_INFO);
        
        try
        {
            ConfigStore.CheckAndLoad(this);
        }
        catch (SecurityException e)
        {
            requestPermissions(new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            }, 114514);
        }
        
        setContentView(R.layout.activity_main);
        
        View.OnClickListener onclick = v ->
        {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            {
                Intent i = new Intent(Intent.ACTION_CALL);
                
                if (v.getId() == R.id.callButton1)
                {
                    i.setData(Uri.fromParts("tel", ConfigStore.Config.Button1TelNumber, null));
                }
                else
                {
                    i.setData(Uri.fromParts("tel", ConfigStore.Config.Button2TelNumber, null));
                }
                
                stopLockTask();
                startActivity(i);
            }
            else
            {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 114514);
            }
        };
        
        Button callButton1 = findViewById(R.id.callButton1);
        callButton1.setBackgroundColor(Color.parseColor(ConfigStore.Config.Button1Color));
        callButton1.setText(ConfigStore.Config.Button1Text);
        callButton1.setTextColor(Color.parseColor(ConfigStore.Config.Button1TextColor));
        callButton1.setOnClickListener(onclick);
        
        Button callButton2 = findViewById(R.id.callButton2);
        callButton2.setBackgroundColor(Color.parseColor(ConfigStore.Config.Button2Color));
        callButton2.setText(ConfigStore.Config.Button2Text);
        callButton2.setTextColor(Color.parseColor(ConfigStore.Config.Button2TextColor));
        callButton2.setOnClickListener(onclick);
        
        if (dpm.isLockTaskPermitted(getPackageName()))
        {
            startLockTask();
        }
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        
        if (hasFocus && getSystemService(ActivityManager.class).getLockTaskModeState() == ActivityManager.LOCK_TASK_MODE_NONE)
        {
            try
            {
                startLockTask();
            }
            catch (IllegalArgumentException e)
            {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}