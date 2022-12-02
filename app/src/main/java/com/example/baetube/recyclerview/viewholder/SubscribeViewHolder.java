package com.example.baetube.recyclerview.viewholder;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class SubscribeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
{
    public LinearLayout layout;
    public ImageView profile;
    public TextView channel_name;

    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public SubscribeViewHolder(@NonNull View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        super(itemView);

        layout = itemView.findViewById(R.id.recyclerview_subscribe_layout);
        profile = itemView.findViewById(R.id.recyclerview_subscribe_image_profile);
        channel_name = itemView.findViewById(R.id.recyclerview_subscribe_text_channel_name);

        this.onRecyclerViewClickListener = onRecyclerViewClickListener;

        layout.setOnClickListener(this);
        layout.setOnLongClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        int position = getAdapterPosition();

        if(position != RecyclerView.NO_POSITION)
        {
            if(onRecyclerViewClickListener != null)
            {
                onRecyclerViewClickListener.onItemClick(view, position);
            }
        }
    }

    @Override
    public boolean onLongClick(View view)
    {
        int position = getAdapterPosition();

        if(position != RecyclerView.NO_POSITION)
        {
            if(onRecyclerViewClickListener != null)
            {
                onRecyclerViewClickListener.onItemLongClick(view, position);
            }
        }

        return false;
    }
}
