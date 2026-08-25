package net.nfmcpwr.EasyHome;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

import java.io.IOException;

public class HomeSettingsActivity extends ComponentActivity
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);
        
        setContentView(R.layout.activity_homesettings);
        
        ImageButton prevButton = findViewById(R.id.prev_button);
        prevButton.setOnClickListener(view ->
        {
            finish();
        });
        
        ConfigStore.CheckAndLoad(this);
        
        EditText button1Text = findViewById(R.id.button1Text);
        button1Text.setText(ConfigStore.Config.Button1Text);
        
        EditText button1TelNumber = findViewById(R.id.button1TelNumber);
        button1TelNumber.setText(ConfigStore.Config.Button1TelNumber);
        
        ColorPicker button1Color = findViewById(R.id.button1Color);
        button1Color.setColor(ConfigStore.Config.Button1Color);
        
        ColorPicker button1TextColor = findViewById(R.id.button1TextColor);
        button1TextColor.setColor(ConfigStore.Config.Button1TextColor);
        
        EditText button2Text = findViewById(R.id.button2Text);
        button2Text.setText(ConfigStore.Config.Button2Text);
        
        EditText button2TelNumber = findViewById(R.id.button2TelNumber);
        button2TelNumber.setText(ConfigStore.Config.Button2TelNumber);
        
        ColorPicker button2Color = findViewById(R.id.button2Color);
        button2Color.setColor(ConfigStore.Config.Button2Color);
        
        ColorPicker button2TextColor = findViewById(R.id.button2TextColor);
        button2TextColor.setColor(ConfigStore.Config.Button2TextColor);
    }
    
    @Override
    protected void onStop()
    {
        super.onStop();
        
        try
        {
            ConfigStore.Config.Save();
        }
        catch (IOException e)
        {
            Toast.makeText(this, R.string.error_save_config, Toast.LENGTH_SHORT).show();
            Log.e(HomeSettingsActivity.class.getSimpleName(), e.getMessage(), e);
        }
    }
}
