package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.R;

public class SearchHistoryViewHolder extends RecyclerView.ViewHolder
{
    public ImageView icon;
    public ImageView write;
    public TextView keywords;

    public SearchHistoryViewHolder(@NonNull View itemView)
    {
        super(itemView);

        icon = itemView.findViewById(R.id.recyclerview_search_history_image_icon);
        write = itemView.findViewById(R.id.recyclerview_search_history_image_write);
        keywords = itemView.findViewById(R.id.recyclerview_search_history_text_keywords);
    }
}
