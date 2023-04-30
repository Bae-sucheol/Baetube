package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.dto.CategoryDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.viewholder.CategoryViewHolder;

import java.util.ArrayList;

public class RecyclerViewCategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> implements OnRecyclerViewClickListener
{
    private Context context;
    private ArrayList<CategoryDTO> list = null;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public RecyclerViewCategoryAdapter(ArrayList<CategoryDTO> list)
    {
        this.list = list;
    }


    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_category, parent, false);

        CategoryViewHolder viewHolder = new CategoryViewHolder(view, onRecyclerViewClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( CategoryViewHolder holder, int position)
    {
        CategoryDTO item = list.get(position);

        holder.category.setText(item.getName());
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
