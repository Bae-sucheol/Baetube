package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView category;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public CategoryViewHolder( View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        super(itemView);

        category = itemView.findViewById(R.id.recyclerview_category_text_category);

        this.onRecyclerViewClickListener = onRecyclerViewClickListener;

        category.setOnClickListener(this);
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
