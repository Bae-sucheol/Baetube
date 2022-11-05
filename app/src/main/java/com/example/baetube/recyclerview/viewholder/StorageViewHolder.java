package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.R;

public class StorageViewHolder extends RecyclerView.ViewHolder
{
    public ImageView thumbnail;
    public TextView name;
    public TextView count;

    public StorageViewHolder(@NonNull View itemView)
    {
        super(itemView);

        thumbnail = itemView.findViewById(R.id.recyclerview_storage_thumbnail);
        name = itemView.findViewById(R.id.recyclerview_storage_name);
        count = itemView.findViewById(R.id.recyclerview_storage_count);
    }
}
