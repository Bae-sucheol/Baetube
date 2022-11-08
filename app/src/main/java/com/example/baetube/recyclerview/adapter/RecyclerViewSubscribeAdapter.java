package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.UserDisplay;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.recyclerview.item.RecyclerViewSubscribeItem;
import com.example.baetube.recyclerview.viewholder.SubscribeViewHolder;

import java.util.ArrayList;

public class RecyclerViewSubscribeAdapter extends RecyclerView.Adapter<SubscribeViewHolder> implements OnRecyclerViewClickListener
{
    private Context context;
    private ArrayList<RecyclerViewSubscribeItem> list = null;

    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public RecyclerViewSubscribeAdapter(ArrayList<RecyclerViewSubscribeItem> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public SubscribeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;

        if(viewType == (int)context.getResources().getInteger(R.integer.view_type_subscribe_vertical))
        {
            view = inflater.inflate(R.layout.recyclerview_subscribe_vertical, parent, false);
        }
        else
        {
            view = inflater.inflate(R.layout.recyclerview_subscribe_horizontal, parent, false);
        }

        SubscribeViewHolder viewHolder = new SubscribeViewHolder(view, onRecyclerViewClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubscribeViewHolder holder, int position)
    {
        RecyclerViewSubscribeItem item = list.get(position);

        ChannelDTO channelDTO = item.getChannelDTO();

        holder.layout.getLayoutParams().width = (int)UserDisplay.getWidth();

        //holder.profile.setImageDrawable();
        holder.channel_name.setText(channelDTO.getName());

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return list.get(position).getViewType();
    }

    public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    @Override
    public void onItemClick(View view, int position)
    {
        if(onRecyclerViewClickListener != null)
        {
            onRecyclerViewClickListener.onItemClick(view, position);
        }
    }

    @Override
    public void onItemLongClick(View view, int position)
    {
        if(onRecyclerViewClickListener != null)
        {
            onRecyclerViewClickListener.onItemLongClick(view, position);
        }
    }

}
