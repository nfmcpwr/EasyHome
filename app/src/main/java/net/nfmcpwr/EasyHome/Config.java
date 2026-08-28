package net.nfmcpwr.EasyHome;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Config
{
    public String Button1Text;
    @JsonProperty("Button1BackgroundColor")
    public String Button1Color;
    public String Button1TextColor;
    public String Button1TelNumber;
    public String Button2Text;
    @JsonProperty("Button2BackgroundColor")
    public String Button2Color;
    public String Button2TextColor;
    public String Button2TelNumber;
    
    public Config()
    {
    
    }
    
    public static Config Load(Context context) throws SecurityException
    {
        if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
            context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
        {
            throw new SecurityException();
        }
        
        if (Build.VERSION_CODES.R <= Build.VERSION.SDK_INT)
        {
            if (!Environment.isExternalStorageManager())
            {
                throw new SecurityException();
            }
        }
        
        File configDir = new File(Environment.getExternalStorageDirectory()
            .getPath(), "EasyHome/Config");
        if (!configDir.exists())
        {
            if (!configDir.mkdirs())
            {
                throw new RuntimeException("Create directory failed");
            }
        }
        
        File configFile = new File(Environment.getExternalStorageDirectory()
            .getPath(), "EasyHome/Config/CallButtonConfig");
        
        if (!configFile.exists())
        {
            try (InputStream iStream = context.getResources()
                .openRawResource(R.raw.callbuttonconfig);
                 ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                 FileWriter writer = new FileWriter(configFile))
            {
                byte[] buffer = new byte[1024];
                int len;
                
                while ((len = iStream.read(buffer)) != -1)
                {
                    outStream.write(buffer, 0, len);
                }
                
                String defaultConfigText = outStream.toString("UTF-8");
                
                writer.write(defaultConfigText);
            }
            catch (IOException e)
            {
                Toast.makeText(context, R.string.error_save_default_config, Toast.LENGTH_SHORT)
                    .show();
                Log.e(Config.class.getSimpleName(), e.getMessage(), e);
            }
        }
        
        try
        {
            return new ObjectMapper().readValue(configFile, Config.class);
        }
        catch (Exception e)
        {
            Toast.makeText(context, R.string.error_read_config, Toast.LENGTH_SHORT).show();
            Log.e(Config.class.getSimpleName(), e.getMessage(), e);
            
            return null;
        }
    }
    
    public void Save() throws IOException
    {
        File configFile = new File(Environment.getExternalStorageDirectory()
            .getPath(), "EasyHome/Config/CallButtonConfig");
        String configJson = new ObjectMapper().writeValueAsString(this);
        
        new FileWriter(configFile).write(configJson);
    }
}
