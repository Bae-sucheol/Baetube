package com.example.baetube;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideoListViewHolder extends RecyclerView.ViewHolder
{
    public ImageView thumbnail;

    public VideoListViewHolder(@NonNull View itemView)
    {
        super(itemView);

        thumbnail = itemView.findViewById(R.id.gridview_image_thumbnail);
    }
}
