package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class SearchHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
{
    public ConstraintLayout layout;
    public ImageView icon;
    public ImageView write;
    public TextView keywords;

    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public SearchHistoryViewHolder(View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        super(itemView);

        layout = itemView.findViewById(R.id.recyclerview_search_history_layout);
        icon = itemView.findViewById(R.id.recyclerview_search_history_image_icon);
        write = itemView.findViewById(R.id.recyclerview_search_history_image_write);
        keywords = itemView.findViewById(R.id.recyclerview_search_history_text_keywords);

        this.onRecyclerViewClickListener = onRecyclerViewClickListener;

        layout.setOnClickListener(this);
        layout.setOnLongClickListener(this);
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

    @Override
    public boolean onLongClick(View view)
    {
        int position = getAdapterPosition();

        if(position != RecyclerView.NO_POSITION)
        {
            if(onRecyclerViewClickListener != null)
            {
                onRecyclerViewClickListener.onItemLongClick(view, position);
            }
        }

        return false;
    }
}
