package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.R;

public class PlaylistViewHolder extends RecyclerView.ViewHolder
{
    public ImageView thumbnail;
    public TextView title;
    public TextView channelName;
    public TextView videoCount;

    public PlaylistViewHolder(@NonNull View itemView)
    {
        super(itemView);

        thumbnail = itemView.findViewById(R.id.recyclerview_playlist_image_thumbnail);
        title = itemView.findViewById(R.id.recyclerview_playlist_text_title);
        channelName = itemView.findViewById(R.id.recyclerview_playlist_text_channel_name);
        videoCount = itemView.findViewById(R.id.recyclerview_playlist_text_video_count);
    }
}
