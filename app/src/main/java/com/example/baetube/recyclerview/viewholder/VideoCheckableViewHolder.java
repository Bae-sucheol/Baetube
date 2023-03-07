package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class VideoCheckableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public ImageView thumbnail;
    public TextView title;
    public TextView views;
    public TextView date;
    public TextView channelName;
    public LinearLayout layout;
    public CheckBox checkBox;

    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public VideoCheckableViewHolder(@NonNull View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        super(itemView);

        thumbnail = itemView.findViewById(R.id.recyclerview_video_image_thumbnail);
        title = itemView.findViewById(R.id.recyclerview_video_text_title);
        views = itemView.findViewById(R.id.recyclerview_video_text_views);
        date = itemView.findViewById(R.id.recyclerview_video_text_date);
        channelName = itemView.findViewById(R.id.recyclerview_video_text_channel_name);
        layout = itemView.findViewById(R.id.recyclerview_video_layout_information);
        checkBox = itemView.findViewById(R.id.recyclerview_video_checkbox);

        this.onRecyclerViewClickListener = onRecyclerViewClickListener;

        itemView.setOnClickListener(this);
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
