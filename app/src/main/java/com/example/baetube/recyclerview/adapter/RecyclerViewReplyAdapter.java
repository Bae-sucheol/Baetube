package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.baetube.DateToStringUtil;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.dto.ReplyDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.item.RecyclerViewReplyItem;
import com.example.baetube.recyclerview.viewholder.ReplyViewHolder;

import java.util.ArrayList;

public class RecyclerViewReplyAdapter extends RecyclerView.Adapter<ReplyViewHolder> implements OnRecyclerViewClickListener
{
    private Context context;
    private ArrayList<RecyclerViewReplyItem> list = null;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public RecyclerViewReplyAdapter(ArrayList<RecyclerViewReplyItem> list)
    {
        this.list = list;
    }


    @Override
    public ReplyViewHolder onCreateViewHolder( ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_reply, parent, false);

        ReplyViewHolder viewHolder = new ReplyViewHolder(view, onRecyclerViewClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ReplyViewHolder holder, int position)
    {
        RecyclerViewReplyItem item = list.get(position);

        ReplyDTO replyDTO = item.getReplyDTO();
        //ChannelDTO channelDTO = item.getChannelDTO();

        holder.channelName.setText(replyDTO.getName());
        holder.date.setText(DateToStringUtil.dateToString(replyDTO.getDate()));
        //holder.nestedReplyLayout.setVisibility(View.GONE);
        holder.comment.setText(replyDTO.getComment());

        Glide.with(context)
                .asBitmap()
                .load(context.getString(R.string.api_url_image_profile) + item.getReplyDTO().getProfile() + ".jpg")
                .error(ContextCompat.getDrawable(context,R.drawable.ic_baseline_account_circle_24))
                .override(holder.profile.getWidth(), holder.profile.getHeight())
                .centerCrop()
                .apply(new RequestOptions().circleCrop())
                .into(holder.profile);

        holder.like.setText(String.valueOf(replyDTO.getLike()));
        holder.hate.setText(String.valueOf(replyDTO.getHate()));

        // 댓글에 대댓글(답글)이 달려있다면 대댓글(답글)의 수를 출력해주어야한다.
        // 댓글에 대댓글이 달려있지 않다면 해당 뷰들의 visibility를 Gone으로 설정하여 출력하지 않는다.
        if(replyDTO.getNestedCount() == 0)
        {
            holder.nestedReplyLayout.setVisibility(View.GONE);
        }
        else
        {
            holder.nestedReplyCount.setText(String.valueOf(replyDTO.getNestedCount()) + "개");
       }
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
