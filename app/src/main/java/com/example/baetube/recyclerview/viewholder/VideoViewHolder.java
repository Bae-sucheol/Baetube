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
    public ImageView option;
    public TextView title;
    public TextView views;
    public TextView date;
    public TextView channelName;

    public VideoViewHolder(@NonNull View itemView)
    {
        super(itemView);

        thumbnail = itemView.findViewById(R.id.recyclerview_video_image_thumbnail);
        profile = itemView.findViewById(R.id.recyclerview_video_image_profile);
        option = itemView.findViewById(R.id.recyclerview_video_image_option);
        title = itemView.findViewById(R.id.recyclerview_video_text_title);
        views = itemView.findViewById(R.id.recyclerview_video_text_views);
        date = itemView.findViewById(R.id.recyclerview_video_text_date);
        channelName = itemView.findViewById(R.id.recyclerview_video_text_channel_name);

    }
}
