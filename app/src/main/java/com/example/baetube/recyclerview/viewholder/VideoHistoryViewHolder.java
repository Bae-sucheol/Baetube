package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.R;

public class VideoHistoryViewHolder extends RecyclerView.ViewHolder
{
    public ImageView thumbnail;
    public TextView title;
    public TextView channel_name;

    public VideoHistoryViewHolder(@NonNull View itemView)
    {
        super(itemView);

        thumbnail = itemView.findViewById(R.id.recyclerview_video_history_thumbnail);
        title = itemView.findViewById(R.id.recyclerview_video_history_title);
        channel_name = itemView.findViewById(R.id.recyclerview_video_history_channel_name);
    }
}
