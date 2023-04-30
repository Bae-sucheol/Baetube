package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class PlaylistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public LinearLayout layoutMain;
    public ImageView thumbnail;
    public TextView title;
    public TextView channelName;
    public TextView videoCount;
    public ImageView option;

    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public PlaylistViewHolder( View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        super(itemView);

        layoutMain = itemView.findViewById(R.id.recyclerview_playlist_layout_main);
        thumbnail = itemView.findViewById(R.id.recyclerview_playlist_image_thumbnail);
        title = itemView.findViewById(R.id.recyclerview_playlist_text_title);
        channelName = itemView.findViewById(R.id.recyclerview_playlist_text_channel_name);
        videoCount = itemView.findViewById(R.id.recyclerview_playlist_text_video_count);
        option = itemView.findViewById(R.id.recyclerview_playlist_image_option);

        this.onRecyclerViewClickListener = onRecyclerViewClickListener;

        layoutMain.setOnClickListener(this);
        option.setOnClickListener(this);

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
