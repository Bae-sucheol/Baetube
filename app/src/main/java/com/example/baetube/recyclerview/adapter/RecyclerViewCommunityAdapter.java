package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.baetube.DateToStringUtil;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.CommunityDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.dto.VoteResultDTO;
import com.example.baetube.recyclerview.item.RecyclerViewCommunityItem;
import com.example.baetube.recyclerview.item.RecyclerViewVoteItem;
import com.example.baetube.recyclerview.viewholder.CommunityViewHolder;

import java.util.ArrayList;

public class RecyclerViewCommunityAdapter extends RecyclerView.Adapter<CommunityViewHolder> implements OnRecyclerViewClickListener
{
    private Context context;
    private ArrayList<RecyclerViewCommunityItem> list = null;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;
    private OnRecyclerViewClickListener onVoteViewClickListener;

    public RecyclerViewCommunityAdapter(ArrayList<RecyclerViewCommunityItem> list)
    {
        this.list = list;
        this.onVoteViewClickListener = new OnRecyclerViewClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                RecyclerView recyclerView = (RecyclerView)view.getParent();
                RecyclerViewCommunityVoteAdapter recyclerViewAdapter = (RecyclerViewCommunityVoteAdapter)recyclerView.getAdapter();
                ProgressBar progressBar = view.findViewById(R.id.progressbar);
                progressBar.setProgressDrawable(context.getDrawable(R.drawable.progressbar_selected_true));
                VoteResultDTO voteResultData = recyclerViewAdapter.selectVote(view, position);
                VoteDTO voteData = voteResultData.getVoteDTO();
                boolean isCancel = voteResultData.isCancel();

                onRecyclerViewClickListener.onCastVoteOption(voteData, isCancel);
            }

            @Override
            public void onItemLongClick(View view, int position)
            {

            }

            @Override
            public void onCastVoteOption(VoteDTO voteData, boolean isCancel)
            {

            }
        };
    }


    @Override
    public CommunityViewHolder onCreateViewHolder( ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_community, parent, false);

        CommunityViewHolder viewHolder = new CommunityViewHolder(view, onRecyclerViewClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( CommunityViewHolder holder, int position)
    {
        RecyclerViewCommunityItem item = list.get(position);

        CommunityDTO communityDTO = item.getCommunityDTO();
        ChannelDTO channelDTO = item.getChannelDTO();

        Glide.with(context)
                .asBitmap()
                .load(context.getString(R.string.api_url_image_community) + communityDTO.getImageUrl() + ".jpg") // or URI/path
                .error(ContextCompat.getDrawable(context,R.drawable.ic_baseline_account_circle_24))
                .override(holder.content.getWidth(), holder.content.getHeight())
                .centerCrop()
                .into(holder.content);
        Glide.with(context)
                .asBitmap()
                .load(context.getString(R.string.api_url_image_profile) + channelDTO.getProfile() + ".jpg")
                .error(ContextCompat.getDrawable(context,R.drawable.ic_baseline_account_circle_24))
                .override(holder.profile.getWidth(), holder.profile.getHeight())
                .centerCrop()
                .apply(new RequestOptions().circleCrop())
                .into(holder.profile);

        holder.channelName.setText(channelDTO.getName());
        holder.date.setText(DateToStringUtil.dateToString(communityDTO.getDate()));
        holder.comment.setText(communityDTO.getComment());
        holder.likeCount.setText(String.valueOf(communityDTO.getLikeCount()));
        holder.hateCount.setText(String.valueOf(communityDTO.getHateCount()));
        holder.replyCount.setText(String.valueOf(communityDTO.getReplyCount()));

        ArrayList<RecyclerViewVoteItem> voteList = item.getVoteList();

        if(voteList == null)
        {
            return;
        }

        // 총 합을 먼저 구한다.
        int total = 0;
        ArrayList<RecyclerViewVoteItem> list = new ArrayList<>();

        for (int i = 0; i < voteList.size(); i++)
        {
            total += voteList.get(i).getVoteDTO().getCount();
        }

        RecyclerViewCommunityVoteAdapter adapter = new RecyclerViewCommunityVoteAdapter(voteList, total);
        adapter.setOnVoteViewClickListener(onVoteViewClickListener);
        holder.recyclerViewVote.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerViewVote.setAdapter(adapter);

        /*

        for (int i = 0; i < voteList.size(); i++)
        {
            FrameLayout layoutProgress = (FrameLayout) layoutInflater.inflate(R.layout.layout_progressbar_item, holder.layoutVote, false);

            ProgressBar progressBar = layoutProgress.findViewById(R.id.progressbar);
            //progressBar.setProgressDrawable(context.getDrawable(R.drawable.progressbar_selected_true));
            progressBar.setProgress(getPercentage(voteList.get(i).getCount(), total), false);
            TextView textViewProgress = layoutProgress.findViewById(R.id.textview_progress_option);
            textViewProgress.setText("테스트");
            //textViewProgress.setTextColor(context.getColor(R.color.progress_blue));

            holder.layoutVote.addView(layoutProgress);
        }

         */

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
