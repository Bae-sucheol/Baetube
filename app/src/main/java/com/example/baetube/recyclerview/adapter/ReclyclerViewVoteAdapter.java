package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.recyclerview.viewholder.VoteViewHolder;

import java.util.ArrayList;

public class ReclyclerViewVoteAdapter extends RecyclerView.Adapter<VoteViewHolder> implements OnRecyclerViewClickListener
{
    private Context context;
    private ArrayList<String> list;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public ReclyclerViewVoteAdapter(ArrayList<String> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public VoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.recyclerview_vote, parent, false);

        VoteViewHolder viewHolder = new VoteViewHolder(view, onRecyclerViewClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VoteViewHolder holder, int position)
    {
        String item = list.get(position);

        holder.comment.setText(item);
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

    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }
}
