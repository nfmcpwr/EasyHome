package net.nfmcpwr.EasyHome;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

public class QuickSettingsTileService extends TileService
{
    @Override
    public void onClick()
    {
        super.onClick();
        
        if (Build.VERSION_CODES.UPSIDE_DOWN_CAKE <= Build.VERSION.SDK_INT)
        {
            startActivityAndCollapse(PendingIntent.getActivity(this, 1919, new Intent(this, SettingsActivity.class).addFlags(FLAG_ACTIVITY_NEW_TASK), PendingIntent.FLAG_IMMUTABLE));
        }
        else
        {
            startActivity(new Intent(this, SettingsActivity.class).addFlags(FLAG_ACTIVITY_NEW_TASK));
        }
    }

    @Override
    public void onStartListening()
    {
        super.onStartListening();

        Tile t = getQsTile();
        t.setState(Tile.STATE_ACTIVE);
        t.updateTile();
    }

    @Override
    public void onTileAdded()
    {
        super.onTileAdded();

        Tile t = getQsTile();
        t.setState(Tile.STATE_ACTIVE);
        t.updateTile();
    }
}