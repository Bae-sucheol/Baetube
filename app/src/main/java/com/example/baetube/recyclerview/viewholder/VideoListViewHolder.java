package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class VideoListViewHolder extends RecyclerView.ViewHolder
{
    public ImageView thumbnail;

    public VideoListViewHolder(@NonNull View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        super(itemView);

        thumbnail = itemView.findViewById(R.id.recyclerview_video_list_thumbnail);

        thumbnail.setOnClickListener(new View.OnClickListener()
        {
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
        });
    }
}
