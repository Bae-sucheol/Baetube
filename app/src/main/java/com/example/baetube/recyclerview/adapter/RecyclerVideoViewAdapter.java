package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.R;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.recyclerview.item.RecyclerVideoViewItem;
import com.example.baetube.recyclerview.viewholder.VideoViewHolder;

import java.util.ArrayList;

public class RecyclerVideoViewAdapter extends RecyclerView.Adapter<VideoViewHolder>
{

    private ArrayList<RecyclerVideoViewItem> list = null;

    public RecyclerVideoViewAdapter(ArrayList<RecyclerVideoViewItem> list)
    {
        this.list = list;
    }


    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_video, parent, false);
        VideoViewHolder viewHolder = new VideoViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position)
    {
        RecyclerVideoViewItem item = list.get(position);

        ChannelDTO channelDTO = item.getChannelDTO();
        VideoDTO videoDTO = item.getVideoDTO();

        //holder.thumbnail.setImageDrawable();
        //holder.profile.setImageDrawable();
        holder.title.setText(videoDTO.getTitle());
        holder.channel_name.setText(channelDTO.getName());
        holder.views.setText(String.valueOf(videoDTO.getViews()));
        holder.date.setText(videoDTO.getDate());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
