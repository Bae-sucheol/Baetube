package com.example.baetube.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.ViewType;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.CommunityDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.item.RecyclerViewNotificationItem;
import com.example.baetube.recyclerview.viewholder.NotificationViewHolder;

import java.util.ArrayList;

public class RecyclerViewNotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> implements OnRecyclerViewClickListener
{

    private Context context;
    private ArrayList<RecyclerViewNotificationItem> list = null;

    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public RecyclerViewNotificationAdapter(ArrayList<RecyclerViewNotificationItem> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(context == null)
        {
            context = parent.getContext();
        }

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;

        switch(viewType)
        {

            case ViewType.NOTIFICATION_VIDEO :
            case ViewType.NOTIFICATION_COMMUNITY :

                view = inflater.inflate(R.layout.recyclerview_notification, parent, false);

                break;

            case ViewType.NOTIFICATION_DIVIDER :

                view = inflater.inflate(R.layout.recyclerview_notification_divider, parent, false);

                break;

            default :

                view = null;

                break;
        }

        NotificationViewHolder viewHolder = new NotificationViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position)
    {
        RecyclerViewNotificationItem item = list.get(position);

        switch(item.getViewType())
        {

            case ViewType.NOTIFICATION_VIDEO:

                ChannelDTO channelDTO = item.getChannelDTO();
                VideoDTO videoDTO = item.getVideoDTO();

                //holder.profile.setImageResource();
                //holder.thumbnail.setImageResource();

                StringBuffer stringBuffer = new StringBuffer();

                stringBuffer.append(channelDTO.getName());
                stringBuffer.append(context.getString(R.string.notification_text_video));
                stringBuffer.append(videoDTO.getTitle());

                holder.title.setText(stringBuffer.toString());
                holder.date.setText(videoDTO.getDate().toString());

                break;

            case ViewType.NOTIFICATION_COMMUNITY:

                channelDTO = item.getChannelDTO();
                CommunityDTO communityDTO = item.getCommunityDTO();

                //holder.profile.setImageResource();
                //holder.thumbnail.setImageResource();

                stringBuffer = new StringBuffer();

                stringBuffer.append(channelDTO.getName());
                stringBuffer.append(context.getString(R.string.notification_text_communication));
                stringBuffer.append(communityDTO.getComment());

                holder.title.setText(stringBuffer.toString());
                holder.date.setText(communityDTO.getDate().toString());

                break;

            case ViewType.NOTIFICATION_DIVIDER:

                holder.date.setText(item.getDivideString());

                break;

            default:

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
