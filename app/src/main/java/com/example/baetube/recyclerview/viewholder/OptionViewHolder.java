package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.R;

public class OptionViewHolder extends RecyclerView.ViewHolder
{
    public ImageView icon;
    public TextView option;

    public OptionViewHolder(@NonNull View itemView)
    {
        super(itemView);

        icon = itemView.findViewById(R.id.recyclerview_option_image_icon);
        option = itemView.findViewById(R.id.recyclerview_option_text_option);
    }

}
