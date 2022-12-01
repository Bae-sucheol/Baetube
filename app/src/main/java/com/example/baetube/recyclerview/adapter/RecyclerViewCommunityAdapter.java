package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.CommunityDTO;
import com.example.baetube.recyclerview.item.RecyclerViewCommunityItem;
import com.example.baetube.recyclerview.viewholder.CommunityViewHolder;

import java.util.ArrayList;

public class RecyclerViewCommunityAdapter extends RecyclerView.Adapter<CommunityViewHolder> implements OnRecyclerViewClickListener
{
    private Context context;
    private ArrayList<RecyclerViewCommunityItem> list = null;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public RecyclerViewCommunityAdapter(ArrayList<RecyclerViewCommunityItem> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public CommunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_community, parent, false);

        CommunityViewHolder viewHolder = new CommunityViewHolder(view, onRecyclerViewClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityViewHolder holder, int position)
    {
        RecyclerViewCommunityItem item = list.get(position);

        CommunityDTO communityDTO = item.getCommunityDTO();
        ChannelDTO channelDTO = item.getChannelDTO();

        holder.channelName.setText(channelDTO.getName());
        holder.date.setText(communityDTO.getDate());
        holder.comment.setText(communityDTO.getComment());
        holder.likeCount.setText(String.valueOf(communityDTO.getLikeCount()));
        holder.hateCount.setText(String.valueOf(communityDTO.getHateCount()));
        holder.replyCount.setText(String.valueOf(communityDTO.getReplyCount()));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    @Override
    public void onItemClick(View view, int position)
    {

    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }

    public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView)
    {
        super.onDetachedFromRecyclerView(recyclerView);
        context = null;
    }
}
