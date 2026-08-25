package net.nfmcpwr.EasyHome;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AppLauncherItemViewHolder extends RecyclerView.ViewHolder
{
    ImageView appIcon;
    TextView appName;
    TextView packageName;
    Button launchButton;
    
    public AppLauncherItemViewHolder(@NonNull View itemView)
    {
        super(itemView);
        
        this.appIcon = itemView.findViewById(R.id.appIcon);
        this.appName = itemView.findViewById(R.id.appName);
        this.packageName = itemView.findViewById(R.id.packageName);
        this.launchButton = itemView.findViewById(R.id.launchButton);
    }
}
