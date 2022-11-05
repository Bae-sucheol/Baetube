package com.example.baetube.recyclerview.viewholder;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.R;

public class SubscribeVerticalViewHolder extends RecyclerView.ViewHolder
{

    public ImageView profile;
    public TextView channel_name;

    public SubscribeVerticalViewHolder(@NonNull View itemView)
    {
        super(itemView);

        profile = itemView.findViewById(R.id.recyclerview_subscribe_vertical_profile);
        channel_name = itemView.findViewById(R.id.recyclerview_subscribe_vertical_channel_name);
    }
}
