package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class ChannelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public ImageView profile;
    public ImageView option;
    public TextView name;
    public TextView subCount;
    public TextView buttonSub;
    public ConstraintLayout layout;

    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public ChannelViewHolder( View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        super(itemView);

        profile = itemView.findViewById(R.id.recyclerview_channel_image_profile);
        option = itemView.findViewById(R.id.recyclerview_channel_image_option);
        name = itemView.findViewById(R.id.recyclerview_channel_text_channel_name);
        subCount = itemView.findViewById(R.id.recyclerview_channel_text_channel_subs);
        buttonSub = itemView.findViewById(R.id.recyclerview_channel_text_button_sub);
        layout = itemView.findViewById(R.id.recyclerview_channel_layout_information);

        this.onRecyclerViewClickListener = onRecyclerViewClickListener;

        profile.setOnClickListener(this);
        option.setOnClickListener(this);
        layout.setOnClickListener(this);
        buttonSub.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        int position = getAdapterPosition();

        if(position != RecyclerView.NO_POSITION)
        {
            if (onRecyclerViewClickListener != null)
            {
                onRecyclerViewClickListener.onItemClick(view, position);
            }
        }
    }
}
