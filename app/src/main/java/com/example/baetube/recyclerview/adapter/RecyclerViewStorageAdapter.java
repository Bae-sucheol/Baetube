package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.dto.PlaylistDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.item.RecyclerViewPlaylistItem;
import com.example.baetube.recyclerview.viewholder.StorageViewHolder;

import java.util.ArrayList;

public class RecyclerViewStorageAdapter extends RecyclerView.Adapter<StorageViewHolder> implements OnRecyclerViewClickListener
{

    private Context context;
    private ArrayList<RecyclerViewPlaylistItem> list = null;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public RecyclerViewStorageAdapter(ArrayList<RecyclerViewPlaylistItem> list)
    {
        this.list = list;
    }


    @Override
    public StorageViewHolder onCreateViewHolder( ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_storage, parent, false);

        StorageViewHolder viewHolder = new StorageViewHolder(view, onRecyclerViewClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( StorageViewHolder holder, int position)
    {
        if(position == 0)
        {
            holder.name.setText(context.getString(R.string.add_new_playlist_text));
            holder.count.setVisibility(View.GONE);
            holder.thumbnail.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_add_24));

            return;
        }

        RecyclerViewPlaylistItem item = list.get(position);

        PlaylistDTO playlistDTO = item.getPlaylistDTO();

        Glide.with(context)
                .asBitmap()
                .load(context.getString(R.string.api_url_image_thumbnail) + item.getPlaylistDTO().getThumbnail() + ".jpg") // or URI/path
                .error(ContextCompat.getDrawable(context,R.drawable.ic_baseline_account_circle_24))
                .override(holder.thumbnail.getWidth(), holder.thumbnail.getHeight())
                .centerCrop()
                .into(holder.thumbnail);

        holder.name.setText(playlistDTO.getName());
        holder.count.setText(String.valueOf(playlistDTO.getVideoCount()));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    @Override
    public void onDetachedFromRecyclerView( RecyclerView recyclerView)
    {
        super.onDetachedFromRecyclerView(recyclerView);
        context = null;
    }

    public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    @Override
    public void onItemClick(View view, int position)
    {
        onRecyclerViewClickListener.onItemClick(view, position);
    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }

    @Override
    public void onCastVoteOption(VoteDTO voteData, boolean isCancel)
    {

    }
}
