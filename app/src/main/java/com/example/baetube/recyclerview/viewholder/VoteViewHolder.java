package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class VoteViewHolder extends RecyclerView.ViewHolder
{
    public TextView comment;

    public VoteViewHolder(@NonNull View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        super(itemView);

        comment = itemView.findViewById(R.id.recyclerview_vote_text_comment);

        comment.setOnClickListener(new View.OnClickListener()
        {
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
        });

        comment.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {

                int position = getAdapterPosition();

                if(position != RecyclerView.NO_POSITION)
                {
                    if (onRecyclerViewClickListener != null)
                    {
                        onRecyclerViewClickListener.onItemLongClick(view, position);
                    }
                }
                return false;
            }
        });
    }
}
