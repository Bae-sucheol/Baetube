package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder
{
    public ImageView profile;
    public ImageView thumbnail;
    public ImageView option;
    public TextView title;
    public TextView date;

    public NotificationViewHolder(@NonNull View itemView)
    {
        super(itemView);

        profile = itemView.findViewById(R.id.recyclerview_notification_image_profile);
        thumbnail = itemView.findViewById(R.id.recyclerview_notification_image_thumbnail);
        option = itemView.findViewById(R.id.recyclerview_notification_image_option);
        title = itemView.findViewById(R.id.recyclerview_notification_text_title);
        date = itemView.findViewById(R.id.recyclerview_notification_text_date);
    }
}
