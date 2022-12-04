package com.example.baetube.recyclerview.viewholder;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class ReplyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public ImageView profile;
    public ImageView option;
    public ImageView thumbUp;
    public ImageView thumbDown;
    public ImageView writeNestedReply;
    public TextView channelName;
    public TextView date;
    public TextView comment;
    public TextView like;
    public TextView hate;
    public TextView nestedReplyCount;
    public LinearLayout nestedReplyLayout;

    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public ReplyViewHolder(@NonNull View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        super(itemView);

        profile = itemView.findViewById(R.id.recyclerview_reply_image_profile);
        option = itemView.findViewById(R.id.recyclerview_reply_image_option);
        thumbUp = itemView.findViewById(R.id.recyclerview_reply_image_thumb_up);
        thumbDown = itemView.findViewById(R.id.recyclerview_reply_image_thumb_down);
        writeNestedReply = itemView.findViewById(R.id.recyclerview_reply_image_write_nested_reply);
        channelName = itemView.findViewById(R.id.recyclerview_reply_text_channel_name);
        date = itemView.findViewById(R.id.recyclerview_reply_text_date);
        comment = itemView.findViewById(R.id.recyclerview_reply_text_comment);
        like = itemView.findViewById(R.id.recyclerview_reply_text_like);
        hate = itemView.findViewById(R.id.recyclerview_reply_text_hate);
        nestedReplyCount = itemView.findViewById(R.id.recyclerview_reply_text_nested_reply_count);
        nestedReplyLayout = itemView.findViewById(R.id.recyclerview_reply_layout_nested_reply);

        this.onRecyclerViewClickListener = onRecyclerViewClickListener;

        nestedReplyLayout.setOnClickListener(this);
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
