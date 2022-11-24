package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.R;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.ReplyDTO;
import com.example.baetube.recyclerview.item.RecyclerViewReplyItem;
import com.example.baetube.recyclerview.viewholder.PlaylistViewHolder;
import com.example.baetube.recyclerview.viewholder.ReplyViewHolder;

import java.util.ArrayList;

public class RecyclerViewReplyAdapter extends RecyclerView.Adapter<ReplyViewHolder>
{
    private Context context;
    private ArrayList<RecyclerViewReplyItem> list = null;

    public RecyclerViewReplyAdapter(ArrayList<RecyclerViewReplyItem> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_reply, parent, false);

        ReplyViewHolder viewHolder = new ReplyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyViewHolder holder, int position)
    {
        RecyclerViewReplyItem item = list.get(position);

        ReplyDTO replyDTO = item.getReplyDTO();
        ChannelDTO channelDTO = item.getChannelDTO();

        holder.channelName.setText(channelDTO.getName());
        holder.date.setText(replyDTO.getDate());
        //holder.nestedReplyLayout.setVisibility(View.GONE);
        holder.comment.setText(replyDTO.getComment());

        holder.like.setText(String.valueOf(replyDTO.getLike()));
        holder.hate.setText(String.valueOf(replyDTO.getHate()));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
