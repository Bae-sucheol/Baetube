package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.baetube.CountToStringUtil;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.viewholder.ChannelViewHolder;

import java.util.ArrayList;

public class RecyclerViewChannelAdapter extends RecyclerView.Adapter<ChannelViewHolder> implements OnRecyclerViewClickListener
{
    private Context context;
    private ArrayList<ChannelDTO> list = null;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public RecyclerViewChannelAdapter(ArrayList<ChannelDTO> list)
    {
        this.list = list;
    }

    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_channel, parent, false);

        ChannelViewHolder viewHolder = new ChannelViewHolder(view, onRecyclerViewClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ChannelViewHolder holder, int position)
    {
        ChannelDTO item = list.get(position);

        Glide.with(context)
                .asBitmap()
                .load(context.getString(R.string.api_url_image_profile) + item.getProfile() + ".jpg")
                .error(ContextCompat.getDrawable(context,R.drawable.ic_baseline_account_circle_24))
                .override(holder.profile.getWidth(), holder.profile.getHeight())
                .centerCrop()
                .apply(new RequestOptions().circleCrop())
                .into(holder.profile);

        holder.name.setText(item.getName());
        holder.subCount.setText("구독자 " + CountToStringUtil.countToString(item.getSubs()));
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
