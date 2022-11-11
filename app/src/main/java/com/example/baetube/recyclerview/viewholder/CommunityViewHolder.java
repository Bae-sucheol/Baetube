package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.R;

public class CommunityViewHolder extends RecyclerView.ViewHolder
{
    public ImageView profile;
    public ImageView content;
    public TextView channelName;
    public TextView date;
    public TextView comment;
    public TextView likeCount;
    public TextView hateCount;
    public TextView replyCount;

    public CommunityViewHolder(@NonNull View itemView)
    {
        super(itemView);

        profile = itemView.findViewById(R.id.recyclerview_community_image_profile);
        content = itemView.findViewById(R.id.recyclerview_community_image_content);
        channelName = itemView.findViewById(R.id.recyclerview_community_text_channel_name);
        date = itemView.findViewById(R.id.recyclerview_community_text_date);
        comment = itemView.findViewById(R.id.recyclerview_community_text_comment);
        likeCount = itemView.findViewById(R.id.recyclerview_community_text_like_count);
        hateCount = itemView.findViewById(R.id.recyclerview_community_text_hate_count);
        replyCount = itemView.findViewById(R.id.recyclerview_community_text_reply_count);
    }
}
