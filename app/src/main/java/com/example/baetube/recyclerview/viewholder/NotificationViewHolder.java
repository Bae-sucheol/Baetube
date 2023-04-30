package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public ImageView profile;
    public ImageView thumbnail;
    public ImageView option;
    public TextView title;
    public TextView date;

    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public NotificationViewHolder( View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        super(itemView);

        profile = itemView.findViewById(R.id.recyclerview_notification_image_profile);
        thumbnail = itemView.findViewById(R.id.recyclerview_notification_image_thumbnail);
        option = itemView.findViewById(R.id.recyclerview_notification_image_option);
        title = itemView.findViewById(R.id.recyclerview_notification_text_title);
        date = itemView.findViewById(R.id.recyclerview_notification_text_date);

        this.onRecyclerViewClickListener = onRecyclerViewClickListener;

        profile.setOnClickListener(this);
        thumbnail.setOnClickListener(this);
        option.setOnClickListener(this);
        title.setOnClickListener(this);
        date.setOnClickListener(this);
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
}
