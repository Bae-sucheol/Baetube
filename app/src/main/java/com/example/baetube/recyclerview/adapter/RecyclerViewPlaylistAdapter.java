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
import com.example.baetube.dto.PlaylistDTO;
import com.example.baetube.recyclerview.item.RecyclerViewPlaylistItem;
import com.example.baetube.recyclerview.viewholder.PlaylistViewHolder;

import java.util.ArrayList;

public class RecyclerViewPlaylistAdapter extends RecyclerView.Adapter<PlaylistViewHolder> implements OnRecyclerViewClickListener
{
    private Context context;
    private ArrayList<RecyclerViewPlaylistItem> list = null;

    public RecyclerViewPlaylistAdapter(ArrayList<RecyclerViewPlaylistItem> list)
    {
        this.list = list;
    }

    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_playlist, parent, false);

        PlaylistViewHolder viewHolder = new PlaylistViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position)
    {
        RecyclerViewPlaylistItem item = list.get(position);

        PlaylistDTO playlistDTO = item.getPlaylistDTO();
        ChannelDTO channelDTO = item.getChannelDTO();

        //holder.thumbnail.setImageDrawable();
        holder.title.setText(playlistDTO.getName());
        holder.channelName.setText(channelDTO.getName());
        holder.videoCount.setText(String.valueOf(playlistDTO.getVideoCount()));
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
