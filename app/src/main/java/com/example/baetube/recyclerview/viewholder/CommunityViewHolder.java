package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class CommunityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public ImageView profile;
    public ImageView content;
    public ImageView option;
    public ImageView like;
    public ImageView hate;
    public ImageView reply;
    public TextView channelName;
    public TextView date;
    public TextView comment;
    public TextView likeCount;
    public TextView hateCount;
    public TextView replyCount;

    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public CommunityViewHolder(@NonNull View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        super(itemView);

        profile = itemView.findViewById(R.id.recyclerview_community_image_profile);
        content = itemView.findViewById(R.id.recyclerview_community_image_content);
        option = itemView.findViewById(R.id.recyclerview_community_image_option);
        like = itemView.findViewById(R.id.recyclerview_community_image_like);
        hate = itemView.findViewById(R.id.recyclerview_community_image_hate);
        reply = itemView.findViewById(R.id.recyclerview_community_image_reply);
        channelName = itemView.findViewById(R.id.recyclerview_community_text_channel_name);
        date = itemView.findViewById(R.id.recyclerview_community_text_date);
        comment = itemView.findViewById(R.id.recyclerview_community_text_comment);
        likeCount = itemView.findViewById(R.id.recyclerview_community_text_like_count);
        hateCount = itemView.findViewById(R.id.recyclerview_community_text_hate_count);
        replyCount = itemView.findViewById(R.id.recyclerview_community_text_reply_count);

        this.onRecyclerViewClickListener = onRecyclerViewClickListener;

        like.setOnClickListener(this);
        hate.setOnClickListener(this);
        option.setOnClickListener(this);
        reply.setOnClickListener(this);

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
