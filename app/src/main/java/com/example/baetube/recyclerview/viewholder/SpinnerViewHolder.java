package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.R;


public class SpinnerViewHolder  extends RecyclerView.ViewHolder
{
    public ImageView icon;
    public TextView title;
    public TextView description;

    public SpinnerViewHolder(@NonNull View itemView)
    {
        super(itemView);

        icon = itemView.findViewById(R.id.spinner_dropdown_image_icon);
        title = itemView.findViewById(R.id.spinner_dropdown_text_title);
        description = itemView.findViewById(R.id.spinner_dropdown_text_description);
    }
}
