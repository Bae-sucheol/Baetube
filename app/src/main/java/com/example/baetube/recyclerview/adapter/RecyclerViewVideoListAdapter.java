package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.UserDisplay;
import com.example.baetube.recyclerview.viewholder.VideoListViewHolder;

import java.util.ArrayList;

public class RecyclerViewVideoListAdapter extends RecyclerView.Adapter<VideoListViewHolder> implements OnRecyclerViewClickListener
{
    private Context context;
    private ArrayList<Drawable> list;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public RecyclerViewVideoListAdapter(ArrayList<Drawable> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public VideoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
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
    public void onBindViewHolder(@NonNull VideoListViewHolder holder, int position)
    {
        Drawable item = list.get(position);

        holder.thumbnail.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        holder.thumbnail.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;

        holder.thumbnail.setImageDrawable(item);
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
}
