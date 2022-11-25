package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.R;
import com.example.baetube.recyclerview.item.RecyclerViewOptionItem;
import com.example.baetube.recyclerview.viewholder.OptionViewHolder;

import java.util.ArrayList;

public class RecyclerViewOptionAdapter extends RecyclerView.Adapter<OptionViewHolder>
{
    private Context context;

    private ArrayList<RecyclerViewOptionItem> list = null;

    public RecyclerViewOptionAdapter(ArrayList<RecyclerViewOptionItem> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(context == null)
        {
           context = parent.getContext();
        }

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.recyclerview_option, parent, false);

        OptionViewHolder viewHolder = new OptionViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position)
    {
        RecyclerViewOptionItem item = list.get(position);

        holder.icon.setImageResource(item.getResource());
        holder.option.setText(item.getOption());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

}
