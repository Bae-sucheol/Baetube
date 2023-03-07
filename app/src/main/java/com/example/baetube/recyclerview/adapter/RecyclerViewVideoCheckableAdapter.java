package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.item.RecyclerViewVideoCheckableItem;
import com.example.baetube.recyclerview.viewholder.VideoCheckableViewHolder;

import java.util.ArrayList;

public class RecyclerViewVideoCheckableAdapter extends RecyclerView.Adapter<VideoCheckableViewHolder> implements OnRecyclerViewClickListener
{
    private Context context;
    private ArrayList<RecyclerViewVideoCheckableItem> list = null;

    public RecyclerViewVideoCheckableAdapter(ArrayList<RecyclerViewVideoCheckableItem> list)
    {
        this.list = list;
    }

    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    @NonNull
    @Override
    public VideoCheckableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if (context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_video_checkable, parent, false);

        VideoCheckableViewHolder viewHolder = new VideoCheckableViewHolder(view, onRecyclerViewClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoCheckableViewHolder holder, int position)
    {
        RecyclerViewVideoCheckableItem item = list.get(position);

        ChannelDTO channelDTO = item.getChannelDTO();
        VideoDTO videoDTO = item.getVideoDTO();
        boolean isChecked = item.isChecked();

        holder.channelName.setText(channelDTO.getName());

        Glide.with(context)
                .asBitmap()
                .load("http://192.168.0.4:9090/Baetube_backEnd/api/image/thumbnail/" + item.getVideoDTO().getThumbnail() + ".jpg") // or URI/path
                .error(ContextCompat.getDrawable(context, R.drawable.ic_baseline_account_circle_24))
                .override(holder.thumbnail.getWidth(), holder.thumbnail.getHeight())
                .centerCrop()
                .into(holder.thumbnail);

        holder.title.setText(videoDTO.getTitle());
        holder.views.setText(String.valueOf(videoDTO.getViews()) + " · ");
        holder.date.setText(videoDTO.getDate().toString());
        holder.thumbnail.bringToFront();
        holder.checkBox.setChecked(isChecked);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    @Override
    public long getItemId(int position)
    {
        return super.getItemId(position);
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
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView)
    {
        super.onDetachedFromRecyclerView(recyclerView);
        context = null;
    }
}
