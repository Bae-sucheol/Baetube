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
import com.example.baetube.recyclerview.viewholder.SubscribeVerticalViewHolder;

import java.util.ArrayList;

public class RecyclerViewSubscribeAdapter extends RecyclerView.Adapter<SubscribeVerticalViewHolder> implements OnRecyclerViewClickListener
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
    public SubscribeVerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
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

        SubscribeVerticalViewHolder viewHolder = new SubscribeVerticalViewHolder(view, onRecyclerViewClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubscribeVerticalViewHolder holder, int position)
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

}
