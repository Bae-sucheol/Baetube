package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.R;
import com.example.baetube.UserDisplay;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;
import com.example.baetube.recyclerview.viewholder.VideoViewHolder;

import java.util.ArrayList;

public class RecyclerViewVideoAdapter extends RecyclerView.Adapter<VideoViewHolder>
{

    private Context context;
    private ArrayList<RecyclerViewVideoItem> list = null;

    public RecyclerViewVideoAdapter(ArrayList<RecyclerViewVideoItem> list)
    {
        this.list = list;
    }


    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_video, parent, false);

        VideoViewHolder viewHolder = new VideoViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position)
    {
        RecyclerViewVideoItem item = list.get(position);

        ChannelDTO channelDTO = item.getChannelDTO();
        VideoDTO videoDTO = item.getVideoDTO();

        holder.thumbnail.getLayoutParams().height = (int)(UserDisplay.getWidth() * UserDisplay.getRatio());

        //holder.thumbnail.setImageDrawable();
        //holder.profile.setImageDrawable();
        holder.title.setText(videoDTO.getTitle());
        holder.channel_name.setText(channelDTO.getName() + " · ");
        holder.views.setText(String.valueOf(videoDTO.getViews()) + " · ");
        holder.date.setText(videoDTO.getDate());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
