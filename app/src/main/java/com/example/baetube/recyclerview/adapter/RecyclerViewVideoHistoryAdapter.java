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
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;
import com.example.baetube.recyclerview.viewholder.VideoHistoryViewHolder;

import java.util.ArrayList;

public class RecyclerViewVideoHistoryAdapter extends RecyclerView.Adapter<VideoHistoryViewHolder>
{
    private Context context;
    private ArrayList<RecyclerViewVideoItem> list = null;

    public RecyclerViewVideoHistoryAdapter(ArrayList<RecyclerViewVideoItem> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public VideoHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_video_history, parent, false);

        VideoHistoryViewHolder viewHolder = new VideoHistoryViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHistoryViewHolder holder, int position)
    {
        RecyclerViewVideoItem item = list.get(position);

        ChannelDTO channelDTO = item.getChannelDTO();
        VideoDTO videoDTO = item.getVideoDTO();

        //holder.thumbnail.getLayoutParams().height = (int)(UserDisplay.getWidth() * UserDisplay.getRatio());

        //holder.thumbnail.setImageDrawable();
        //holder.profile.setImageDrawable();
        holder.title.setText(videoDTO.getTitle());
        holder.channel_name.setText(channelDTO.getName());
        //holder.views.setText(String.valueOf(videoDTO.getViews()) + " · ");
        //holder.date.setText(videoDTO.getDate());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

}
