package net.nfmcpwr.EasyHome;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class AppLauncherViewAdapter extends RecyclerView.Adapter<AppLauncherItemViewHolder>
{
    List<PackageInfo> packageList;
    Activity activity;
    public View.OnClickListener OnClick;
    
    public AppLauncherViewAdapter(Activity activity, List<PackageInfo> list)
    {
        this.activity = activity;
        this.packageList = list;
    }
    
    @NonNull
    @Override
    public AppLauncherItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.launcher_item, parent, false);
        
        return new AppLauncherItemViewHolder(itemView);
    }
    
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull AppLauncherItemViewHolder holder, int position)
    {
        try
        {
            holder.appIcon.setImageDrawable(activity.getPackageManager()
                .getApplicationIcon(packageList.get(position).packageName));
        }
        catch (PackageManager.NameNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        
        if (packageList.get(position).applicationInfo != null)
        {
            holder.appName.setText(activity.getPackageManager()
                .getApplicationLabel(Objects.requireNonNull(packageList.get(position).applicationInfo)));
        }
        else
        {
            holder.appName.setText("");
        }
        
        holder.packageName.setText(String.format("%s %s (%d)", packageList.get(position).packageName, packageList.get(position).versionName, packageList.get(position)
            .getLongVersionCode()));
        
        int i = position;
        holder.launchButton.setOnClickListener(v ->
        {
            activity.stopLockTask();
            activity.startActivity(activity.getPackageManager()
                .getLaunchIntentForPackage(packageList.get(i).packageName));
        });
    }
    
    @Override
    public int getItemCount()
    {
        return packageList.size();
    }
}
