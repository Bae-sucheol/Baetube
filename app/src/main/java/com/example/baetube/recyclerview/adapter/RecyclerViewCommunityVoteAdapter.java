package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.dto.VoteResultDTO;
import com.example.baetube.recyclerview.item.RecyclerViewVoteItem;
import com.example.baetube.recyclerview.viewholder.CommunityVoteViewHolder;

import java.util.ArrayList;

public class RecyclerViewCommunityVoteAdapter extends RecyclerView.Adapter<CommunityVoteViewHolder> implements OnRecyclerViewClickListener
{
    private Context context;
    private ArrayList<RecyclerViewVoteItem> list = null;
    private OnRecyclerViewClickListener onVoteViewClickListener;
    private int total;
    private int selectIndex;
    private View selectedView;

    public RecyclerViewCommunityVoteAdapter(ArrayList<RecyclerViewVoteItem> list, int total)
    {
        this.list = list;
        this.total = total;
    }


    @Override
    public CommunityVoteViewHolder onCreateViewHolder( ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.layout_progressbar_item, parent, false);

        CommunityVoteViewHolder viewHolder = new CommunityVoteViewHolder(view, onVoteViewClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( CommunityVoteViewHolder holder, int position)
    {
        RecyclerViewVoteItem item = list.get(position);

        VoteDTO voteDTO = item.getVoteDTO();

        holder.option.setText(voteDTO.getOption());
        holder.progressBar.setProgress(getPercentage(voteDTO.getCount(), total));
        holder.count.setText(String.format("%d개", item.getVoteDTO().getCount()));
        holder.percentage.setText(String.format("(%d%%)", getPercentage(voteDTO.getCount(), total)));

        if(item.isSelected())
        {
            holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progressbar_selected_true));
            holder.option.setTextColor(ContextCompat.getColor(context, R.color.progress_blue));
            holder.count.setTextColor(ContextCompat.getColor(context, R.color.progress_blue));
            holder.percentage.setTextColor(ContextCompat.getColor(context, R.color.progress_blue));
            selectedView = holder.itemView.getRootView();
            selectIndex = position;
        }
        else
        {
            holder.progressBar.setProgressDrawable(context.getDrawable(R.drawable.progressbar_selected_false));
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    @Override
    public void onItemClick(View view, int position)
    {

    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }

    @Override
    public void onCastVoteOption(VoteDTO voteData, boolean isCancel)
    {

    }

    public VoteResultDTO selectVote(View view, int position)
    {

        if(selectedView != null)
        {
            ProgressBar progressBar = selectedView.findViewById(R.id.progressbar);
            TextView option = selectedView.findViewById(R.id.textview_progress_option);
            TextView count = selectedView.findViewById(R.id.textview_progress_count);
            TextView percentage = selectedView.findViewById(R.id.textview_progress_percentage);

            if(selectedView == view)
            {
                progressBar.setProgressDrawable(context.getDrawable(R.drawable.progressbar_selected_false));
                option.setTextColor(ContextCompat.getColor(context, R.color.black));
                count.setTextColor(ContextCompat.getColor(context, R.color.black));
                percentage.setTextColor(ContextCompat.getColor(context, R.color.black));
                RecyclerViewVoteItem item = list.get(selectIndex);
                item.getVoteDTO().setCount(item.getVoteDTO().getCount() - 1);
                count.setText(String.format("%d개", item.getVoteDTO().getCount()));
                percentage.setText(String.format("(%d%%)", getPercentage(item.getVoteDTO().getCount(), total - 1)));
                progressBar.setProgress(getPercentage(item.getVoteDTO().getCount(), total - 1));

                selectedView = null;
                selectIndex = 0;

                return new VoteResultDTO(item.getVoteDTO(), true);
            }

            progressBar.setProgressDrawable(context.getDrawable(R.drawable.progressbar_selected_false));
            option.setTextColor(ContextCompat.getColor(context, R.color.black));
            count.setTextColor(ContextCompat.getColor(context, R.color.black));
            percentage.setTextColor(ContextCompat.getColor(context, R.color.black));
            RecyclerViewVoteItem item = list.get(selectIndex);
            item.getVoteDTO().setCount(item.getVoteDTO().getCount() - 1);
            count.setText(String.format("%d개", item.getVoteDTO().getCount()));
            percentage.setText(String.format("(%d%%)", getPercentage(item.getVoteDTO().getCount(), total - 1)));
            progressBar.setProgress(getPercentage(item.getVoteDTO().getCount(), total - 1));
        }

        ProgressBar progressBar = view.findViewById(R.id.progressbar);
        TextView option = view.findViewById(R.id.textview_progress_option);
        TextView count = view.findViewById(R.id.textview_progress_count);
        TextView percentage = view.findViewById(R.id.textview_progress_percentage);
        progressBar.setProgressDrawable(context.getDrawable(R.drawable.progressbar_selected_true));
        option.setTextColor(ContextCompat.getColor(context, R.color.progress_blue));
        count.setTextColor(ContextCompat.getColor(context, R.color.progress_blue));
        percentage.setTextColor(ContextCompat.getColor(context, R.color.progress_blue));
        RecyclerViewVoteItem item = list.get(position);
        item.getVoteDTO().setCount(item.getVoteDTO().getCount() + 1);
        count.setText(String.format("%d개", item.getVoteDTO().getCount()));
        percentage.setText(String.format("(%d%%)", getPercentage(item.getVoteDTO().getCount(), total + 1)));
        progressBar.setProgress(getPercentage(item.getVoteDTO().getCount(), total + 1));

        selectIndex = position;
        selectedView = view;

        return new VoteResultDTO(item.getVoteDTO(), false);
    }

    public void setOnVoteViewClickListener(OnRecyclerViewClickListener onVoteViewClickListener)
    {
        this.onVoteViewClickListener = onVoteViewClickListener;
    }

    @Override
    public void onDetachedFromRecyclerView( RecyclerView recyclerView)
    {
        super.onDetachedFromRecyclerView(recyclerView);
        context = null;
    }

    private int getPercentage(int value, int total)
    {
        return (int)(((double)value / (double)total) * 100);
    }
}
