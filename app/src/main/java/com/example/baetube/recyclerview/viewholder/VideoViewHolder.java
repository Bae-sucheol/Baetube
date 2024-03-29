package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public FrameLayout layoutPlayer;
    public StyledPlayerView player;
    public ImageView thumbnail;
    public ImageView profile;
    public ImageView option;
    public TextView title;
    public TextView views;
    public TextView date;
    public TextView channelName;
    public LinearLayout layout;

    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public VideoViewHolder( View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        super(itemView);

        layoutPlayer = itemView.findViewById(R.id.recyclerview_video_layout_player);
        player = itemView.findViewById(R.id.recyclerview_video_player);
        thumbnail = itemView.findViewById(R.id.recyclerview_video_image_thumbnail);
        profile = itemView.findViewById(R.id.recyclerview_video_image_profile);
        option = itemView.findViewById(R.id.recyclerview_video_image_option);
        title = itemView.findViewById(R.id.recyclerview_video_text_title);
        views = itemView.findViewById(R.id.recyclerview_video_text_views);
        date = itemView.findViewById(R.id.recyclerview_video_text_date);
        channelName = itemView.findViewById(R.id.recyclerview_video_text_channel_name);
        layout = itemView.findViewById(R.id.recyclerview_video_layout_information);

        this.onRecyclerViewClickListener = onRecyclerViewClickListener;

        layoutPlayer.setOnClickListener(this);
        player.getVideoSurfaceView().setOnClickListener(this);
        thumbnail.setOnClickListener(this);
        player.findViewById(R.id.exo_artwork).setOnClickListener(this);
        profile.setOnClickListener(this);
        option.setOnClickListener(this);
        layout.setOnClickListener(this);
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
