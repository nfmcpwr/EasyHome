package net.nfmcpwr.EasyHome;

import androidx.annotation.DrawableRes;

public class BatteryIcon
{
    public static @DrawableRes int GetBatteryIcon(int batLevel)
    {
        int icon = (int) Math.floor(batLevel / 13.0);
        
        switch (icon)
        {
            case 0:
                return R.drawable.battery_android_0_24px;
            
            case 1:
                return R.drawable.battery_android_1_24px;
            
            case 2:
                return R.drawable.battery_android_2_24px;
            
            case 3:
                return R.drawable.battery_android_3_24px;
            
            case 4:
                return R.drawable.battery_android_4_24px;
            
            case 5:
                return R.drawable.battery_android_5_24px;
            
            case 6:
                return R.drawable.battery_android_6_24px;
            
            case 7:
                return R.drawable.battery_android_7_24px;
            
            default:
                return R.drawable.battery_android_question_24px;
        }
    }
}
