package net.nfmcpwr.EasyHome;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

public class HomeSettingsActivity extends ComponentActivity
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
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
        
        Config.Save(this, ConfigStore.Config);
    }
}
