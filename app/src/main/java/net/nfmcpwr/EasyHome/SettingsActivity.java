package net.nfmcpwr.EasyHome;

import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends ComponentActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_settings);
        
        ImageButton prevButton = findViewById(R.id.prev_button);
        prevButton.setOnClickListener(view ->
        {
            finish();
        });
        
        MaterialButton homeSettingsButton = findViewById(R.id.home_settings_button);
        homeSettingsButton.setOnClickListener(view ->
        {
            startActivity(new Intent(this, HomeSettingsActivity.class));
        });
        
        MaterialButton launcherButton = findViewById(R.id.launcher_button);
        launcherButton.setOnClickListener(view ->
        {
            startActivity(new Intent(this, AppLauncherActivity.class));
        });
        
        MaterialButton checkUpdateButton = findViewById(R.id.check_update_button);
        checkUpdateButton.setOnClickListener(view ->
        {
            startActivity(new Intent(this, CheckUpdateActivity.class));
        });
        
        MaterialButton restartButton = findViewById(R.id.restart_button);
        restartButton.setOnClickListener(view ->
        {
            new AlertDialog.Builder(this)
                .setTitle("Restart?")
                .setNegativeButton("Cancel", (dialogInterface, i) ->
                {
                    
                })
                .setPositiveButton("OK", (dialogInterface, i) ->
                {
                    DevicePolicyManager dpm = getSystemService(DevicePolicyManager.class);
                    ComponentName cpn = new ComponentName(this, DeviceOwnerReceiver.class);
                    
                    dpm.reboot(cpn);
                })
                .show();
        });
    }
}
