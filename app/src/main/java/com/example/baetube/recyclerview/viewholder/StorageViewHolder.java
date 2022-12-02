package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.bottomsheetdialog.PlaylistOptionFragment;

public class StorageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public LinearLayout layoutMain;
    public ImageView thumbnail;
    public TextView name;
    public TextView count;

    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public StorageViewHolder(@NonNull View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        super(itemView);

        layoutMain = itemView.findViewById(R.id.recyclerview_storage_layout_main);
        thumbnail = itemView.findViewById(R.id.recyclerview_storage_image_thumbnail);
        name = itemView.findViewById(R.id.recyclerview_storage_text_name);
        count = itemView.findViewById(R.id.recyclerview_storage_text_count);

        this.onRecyclerViewClickListener = onRecyclerViewClickListener;

        layoutMain.setOnClickListener(this);

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
