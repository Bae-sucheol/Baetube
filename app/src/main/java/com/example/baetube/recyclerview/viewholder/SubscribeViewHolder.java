package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class SubscribeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
{
    public FrameLayout layout;
    public LinearLayout layoutBack;
    public LinearLayout layoutFront;
    public ImageView profile;
    public TextView channelName;

    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public SubscribeViewHolder( View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        super(itemView);

        layout = itemView.findViewById(R.id.recyclerview_subscribe_layout);

        layoutBack = itemView.findViewById(R.id.recyclerview_subscribe_layout_back);
        layoutFront = itemView.findViewById(R.id.recyclerview_subscribe_layout_front);
        profile = itemView.findViewById(R.id.recyclerview_subscribe_image_profile);
        channelName = itemView.findViewById(R.id.recyclerview_subscribe_text_channel_name);

        this.onRecyclerViewClickListener = onRecyclerViewClickListener;

        layout.setOnClickListener(this);
        layout.setOnLongClickListener(this);
        layoutBack.setOnClickListener(this);
        //layoutFront.setOnClickListener(this);
        //layoutFront.setOnLongClickListener(this);
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
