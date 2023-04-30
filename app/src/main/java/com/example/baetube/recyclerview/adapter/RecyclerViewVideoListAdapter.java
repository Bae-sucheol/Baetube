package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.UserDisplay;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.viewholder.VideoListViewHolder;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewVideoListAdapter extends RecyclerView.Adapter<VideoListViewHolder> implements OnRecyclerViewClickListener
{
    private Context context;
    private ArrayList<File> list;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public RecyclerViewVideoListAdapter(ArrayList<File> list)
    {
        this.list = list;
    }


    @Override
    public VideoListViewHolder onCreateViewHolder( ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.recyclerview_video_list, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        int size = (int)UserDisplay.getWidth() / 3;

        layoutParams.height = size;
        layoutParams.width = size;

        view.setLayoutParams(layoutParams);

        VideoListViewHolder viewHolder = new VideoListViewHolder(view, onRecyclerViewClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( VideoListViewHolder holder, int position)
    {
        File item = list.get(position);

        Glide.with(context)
                .asBitmap()
                .load(item.getAbsoluteFile()) // or URI/path
                .override(holder.thumbnail.getWidth(), holder.thumbnail.getHeight())
                .centerCrop()
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
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

    @Override
    public void onDetachedFromRecyclerView( RecyclerView recyclerView)
    {
        super.onDetachedFromRecyclerView(recyclerView);
        context = null;
    }
}
