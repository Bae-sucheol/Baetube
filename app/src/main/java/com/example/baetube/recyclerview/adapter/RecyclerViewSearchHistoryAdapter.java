package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.recyclerview.item.RecyclerViewSearchHistoryItem;
import com.example.baetube.recyclerview.viewholder.SearchHistoryViewHolder;

import java.util.ArrayList;

public class RecyclerViewSearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryViewHolder> implements OnRecyclerViewClickListener
{
    private Context context;
    private ArrayList<RecyclerViewSearchHistoryItem> list = null;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public RecyclerViewSearchHistoryAdapter(ArrayList<RecyclerViewSearchHistoryItem> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public SearchHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.recyclerview_search_history, parent, false);

        SearchHistoryViewHolder viewHolder = new SearchHistoryViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHistoryViewHolder holder, int position)
    {
        RecyclerViewSearchHistoryItem item = list.get(position);

        holder.keywords.setText(item.getKeywords());
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
