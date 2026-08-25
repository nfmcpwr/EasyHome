package net.nfmcpwr.EasyHome;

import android.content.Intent;
import android.os.Bundle;

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
    }
}
