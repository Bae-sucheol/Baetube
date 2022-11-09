package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.R;
import com.example.baetube.dto.PlaylistDTO;
import com.example.baetube.recyclerview.item.RecyclerViewPlaylistItem;
import com.example.baetube.recyclerview.viewholder.StorageViewHolder;

import java.util.ArrayList;

public class RecyclerViewStorageAdapter extends RecyclerView.Adapter<StorageViewHolder>
{

    private Context context;
    private ArrayList<RecyclerViewPlaylistItem> list = null;

    public RecyclerViewStorageAdapter(ArrayList<RecyclerViewPlaylistItem> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public StorageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_storage, parent, false);

        StorageViewHolder viewHolder = new StorageViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StorageViewHolder holder, int position)
    {
        RecyclerViewPlaylistItem item = list.get(position);

        PlaylistDTO playlistDTO = item.getPlaylistDTO();

        //holder.thumbnail.setImageDrawable();
        holder.name.setText(playlistDTO.getName());
        holder.count.setText(String.valueOf(playlistDTO.getVideo_count()));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView)
    {
        super.onDetachedFromRecyclerView(recyclerView);
        context = null;
    }
}
