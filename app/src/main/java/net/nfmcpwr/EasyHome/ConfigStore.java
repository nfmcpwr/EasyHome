package net.nfmcpwr.EasyHome;

import android.content.Context;

public class ConfigStore
{
    public static Config Config = null;

    public static void CheckAndLoad(Context context) throws SecurityException
    {
        if (ConfigStore.Config == null)
        {
            ConfigStore.Config = net.nfmcpwr.EasyHome.Config.Load(context);
        }
    }
}
