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
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.PlaylistDTO;
import com.example.baetube.dto.VoteDTO;
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


    @Override
    public PlaylistViewHolder onCreateViewHolder( ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_playlist, parent, false);

        PlaylistViewHolder viewHolder = new PlaylistViewHolder(view, onRecyclerViewClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( PlaylistViewHolder holder, int position)
    {
        RecyclerViewPlaylistItem item = list.get(position);

        PlaylistDTO playlistDTO = item.getPlaylistDTO();
        ChannelDTO channelDTO = item.getChannelDTO();

        Glide.with(context)
                .asBitmap()
                .load(context.getString(R.string.api_url_image_thumbnail) + item.getPlaylistDTO().getThumbnail() + ".jpg") // or URI/path
                .error(ContextCompat.getDrawable(context, R.drawable.ic_baseline_account_circle_24))
                .override(holder.thumbnail.getWidth(), holder.thumbnail.getHeight())
                .centerCrop()
                .into(holder.thumbnail);

        holder.title.setText(playlistDTO.getName());
        holder.channelName.setText(channelDTO.getName());
        holder.videoCountThumbnail.setText(playlistDTO.getVideoCount().toString());

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("동영상 ");
        stringBuffer.append(playlistDTO.getVideoCount());
        stringBuffer.append("개");

        holder.videoCount.setText(stringBuffer.toString());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
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

    public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener)
    {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    @Override
    public void onDetachedFromRecyclerView( RecyclerView recyclerView)
    {
        super.onDetachedFromRecyclerView(recyclerView);
        context = null;
    }
}
