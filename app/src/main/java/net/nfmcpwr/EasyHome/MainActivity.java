package net.nfmcpwr.EasyHome;

import static android.app.admin.DevicePolicyManager.LOCK_TASK_FEATURE_NONE;

import android.Manifest;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
{
    private int count = 0;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable reset = () -> count = 0;
    
    private final BroadcastReceiver batteryReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (Objects.equals(intent.getAction(), Intent.ACTION_BATTERY_CHANGED))
            {
                int batLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                
                if (0 <= batLevel && 0 < scale)
                {
                    TextView batteryText = findViewById(R.id.batteryText);
                    batteryText.setText(String.format(Locale.getDefault(), "%d%%", batLevel * 100 / scale));
                    batteryText.setCompoundDrawablesWithIntrinsicBounds(BatteryIcon.GetBatteryIcon(batLevel * 100 / scale), 0, 0, 0);
                }
            }
        }
    };
    
    private final BroadcastReceiver phoneStateReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (Objects.equals(intent.getAction(), TelephonyManager.ACTION_PHONE_STATE_CHANGED) &&
                Objects.equals(intent.getStringExtra(TelephonyManager.EXTRA_STATE), TelephonyManager.EXTRA_STATE_RINGING))
            {
                stopLockTask();
            }
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        DevicePolicyManager dpm = getSystemService(DevicePolicyManager.class);
        ComponentName cpn = new ComponentName(this, DeviceOwnerReceiver.class);
        
        dpm.setLockTaskPackages(cpn, new String[]{
            getPackageName()
        });
        
        dpm.setLockTaskFeatures(cpn, LOCK_TASK_FEATURE_NONE);
        
        try
        {
            ConfigStore.CheckAndLoad(this);
        }
        catch (SecurityException e)
        {
            List<String> permissions = new ArrayList<String>();
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            
            if (Build.VERSION_CODES.R <= Build.VERSION.SDK_INT)
            {
                permissions.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE);
            }
            
            requestPermissions(permissions.toArray(new String[0]), 114514);
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
        
        this.count = 0;
        Button tapButton = findViewById(R.id.tapButton);
        tapButton.setOnClickListener(view ->
        {
            handler.removeCallbacks(reset);
            
            count++;
            
            if (10 <= count)
            {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            }
            
            handler.postDelayed(reset, 3000);
        });
        
        if (dpm.isLockTaskPermitted(getPackageName()))
        {
            startLockTask();
        }
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        
        if (getSystemService(ActivityManager.class).getLockTaskModeState() == ActivityManager.LOCK_TASK_MODE_NONE)
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
    
    @Override
    protected void onStart()
    {
        super.onStart();
        
        requestPermissions(new String[]{
            Manifest.permission.READ_PHONE_STATE
        }, 1919810);
        
        if (Build.VERSION_CODES.TIRAMISU <= Build.VERSION.SDK_INT)
        {
            registerReceiver(this.batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED), Context.RECEIVER_EXPORTED);
            registerReceiver(this.phoneStateReceiver, new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED), Context.RECEIVER_EXPORTED);
        }
        else
        {
            registerReceiver(this.batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            registerReceiver(this.phoneStateReceiver, new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED));
        }
    }
    
    @Override
    protected void onStop()
    {
        super.onStop();
        
        unregisterReceiver(this.batteryReceiver);
        unregisterReceiver(this.phoneStateReceiver);
    }
}