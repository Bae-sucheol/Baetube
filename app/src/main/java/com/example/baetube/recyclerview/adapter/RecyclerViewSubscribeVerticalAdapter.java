package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.R;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.recyclerview.item.RecyclerViewSubscribeItem;
import com.example.baetube.recyclerview.viewholder.SubscribeVerticalViewHolder;

import java.util.ArrayList;

public class RecyclerViewSubscribeVerticalAdapter extends RecyclerView.Adapter<SubscribeVerticalViewHolder>
{
    private Context context;
    private ArrayList<RecyclerViewSubscribeItem> list = null;

    public RecyclerViewSubscribeVerticalAdapter(ArrayList<RecyclerViewSubscribeItem> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public SubscribeVerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_subscribe_vertical, parent, false);

        SubscribeVerticalViewHolder viewHolder = new SubscribeVerticalViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubscribeVerticalViewHolder holder, int position)
    {
        RecyclerViewSubscribeItem item = list.get(position);

        ChannelDTO channelDTO = item.getChannelDTO();

        //holder.profile.setImageDrawable();
        holder.channel_name.setText(channelDTO.getName());

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
