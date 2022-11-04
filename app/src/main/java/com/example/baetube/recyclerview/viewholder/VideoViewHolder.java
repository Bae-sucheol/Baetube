package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.R;

public class VideoViewHolder extends RecyclerView.ViewHolder
{

    public ImageView thumbnail;
    public ImageView profile;
    public TextView title;
    public TextView views;
    public TextView date;
    public TextView channel_name;

    public VideoViewHolder(@NonNull View itemView)
    {
        super(itemView);

        thumbnail = itemView.findViewById(R.id.recyclerview_video_thumbnail);
        profile = itemView.findViewById(R.id.recyclerview_video_profile);
        title = itemView.findViewById(R.id.recyclerview_video_title);
        views = itemView.findViewById(R.id.recyclerview_video_views);
        date = itemView.findViewById(R.id.recyclerview_video_date);
        channel_name = itemView.findViewById(R.id.recyclerview_video_channel_name);

    }
}
