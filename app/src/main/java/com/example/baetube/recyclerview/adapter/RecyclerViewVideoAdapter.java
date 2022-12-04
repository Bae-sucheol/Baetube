package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.UserDisplay;
import com.example.baetube.ViewType;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;
import com.example.baetube.recyclerview.viewholder.VideoViewHolder;

import java.util.ArrayList;

public class RecyclerViewVideoAdapter extends RecyclerView.Adapter<VideoViewHolder> implements OnRecyclerViewClickListener
{

    private Context context;
    private ArrayList<RecyclerViewVideoItem> list = null;

    public RecyclerViewVideoAdapter(ArrayList<RecyclerViewVideoItem> list)
    {
        this.list = list;
    }

    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;

        switch(viewType)
        {
            case ViewType.VIDEO_LARGE :

                view = inflater.inflate(R.layout.recyclerview_video_large, parent, false);

                break;
            case ViewType.VIDEO_MEDIUM :

                view = inflater.inflate(R.layout.recyclerview_video_medium, parent, false);

                break;
            case ViewType.VIDEO_SMALL :

                view = inflater.inflate(R.layout.recyclerview_video_small, parent, false);

                break;
            case ViewType.VIDEO_DIVIDER :

                view = inflater.inflate(R.layout.recyclerview_video_divider, parent, false);

                break;
            default :

                // 지원하는 뷰타입이 없는 경우.
                view = null; // 임시.

                break;
        }


        VideoViewHolder viewHolder = new VideoViewHolder(view, onRecyclerViewClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position)
    {
        RecyclerViewVideoItem item = list.get(position);

        ChannelDTO channelDTO = item.getChannelDTO();
        VideoDTO videoDTO = item.getVideoDTO();

        switch(item.getViewType())
        {
            case ViewType.VIDEO_LARGE :

                holder.channelName.setText(channelDTO.getName() + " · ");
                //holder.thumbnail.setImageDrawable();
                //holder.profile.setImageDrawable();
                holder.title.setText(videoDTO.getTitle());
                holder.views.setText(String.valueOf(videoDTO.getViews()) + " · ");
                holder.date.setText(videoDTO.getDate());

                break;
            case ViewType.VIDEO_MEDIUM :

            case ViewType.VIDEO_SMALL :

                holder.channelName.setText(channelDTO.getName());
                //holder.thumbnail.setImageDrawable();
                //holder.profile.setImageDrawable();
                holder.title.setText(videoDTO.getTitle());
                holder.views.setText(String.valueOf(videoDTO.getViews()) + " · ");
                holder.date.setText(videoDTO.getDate());

                break;
            case ViewType.VIDEO_DIVIDER :

                holder.date.setText(videoDTO.getDate());

                break;
            default :

                // 지원하지 않는 뷰타입

                break;
        }

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return list.get(position).getViewType();
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
