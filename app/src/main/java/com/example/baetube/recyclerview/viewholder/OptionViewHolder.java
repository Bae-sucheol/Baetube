package com.example.baetube.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class OptionViewHolder extends RecyclerView.ViewHolder
{
    public ConstraintLayout layout;
    public ImageView icon;
    public TextView option;

    public OptionViewHolder(@NonNull View itemView, OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        super(itemView);

        layout = itemView.findViewById(R.id.recyclerview_option_layout);
        icon = itemView.findViewById(R.id.recyclerview_option_image_icon);
        option = itemView.findViewById(R.id.recyclerview_option_text_option);

        layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION)
                {
                    if(onRecyclerViewClickListener != null)
                    {
                        onRecyclerViewClickListener.onItemClick(view, position);
                    }
                }

            }
        });

    }

}
