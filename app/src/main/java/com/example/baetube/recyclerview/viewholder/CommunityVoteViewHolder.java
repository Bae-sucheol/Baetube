package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class CommunityVoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public ProgressBar progressBar;
    public TextView option;
    public TextView count;
    public TextView percentage;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public CommunityVoteViewHolder( View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        super(itemView);

        progressBar = itemView.findViewById(R.id.progressbar);
        option = itemView.findViewById(R.id.textview_progress_option);
        count = itemView.findViewById(R.id.textview_progress_count);
        percentage = itemView.findViewById(R.id.textview_progress_percentage);

        this.onRecyclerViewClickListener = onRecyclerViewClickListener;

        itemView.setOnClickListener(this);
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
