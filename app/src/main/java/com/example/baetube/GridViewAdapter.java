package com.example.baetube;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.baetube.dto.PlaylistDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.recyclerview.viewholder.VideoViewHolder;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter implements OnRecyclerViewClickListener
{
    private Context context;
    private ArrayList<VideoDTO> list;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public GridViewAdapter(ArrayList<VideoDTO> list)
    {
        this.list = list;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int i)
    {
        return list.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if(context == null)
        {
            context = viewGroup.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        VideoListViewHolder viewHolder;

        if(view == null)
        {
            view = inflater.inflate(R.layout.gridview_video, viewGroup, false);

            viewHolder = new VideoListViewHolder(view);

            int margin = context.getResources().getDimensionPixelSize(R.dimen.margin_medium);

            ViewGroup.LayoutParams layoutParams = viewHolder.thumbnail.getLayoutParams();
            layoutParams.width = (int)( (UserDisplay.getWidth() - margin * 2) / 3);
            layoutParams.height = layoutParams.width;

            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (VideoListViewHolder)view.getTag();
        }

        return view;
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

}
