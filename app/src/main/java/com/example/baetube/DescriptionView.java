package com.example.baetube;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.sql.Timestamp;
import java.util.Calendar;

public class DescriptionView implements View.OnClickListener
{
    private Context context;
    private View view;

    private ImageView buttonClose;
    private ImageView profile;

    private TextView channelName;
    private TextView videoTitle;
    private TextView likeCount;
    private TextView viewCount;
    private TextView date;
    private TextView dateYear;
    private TextView content;

    private RecyclerViewVideoItem currentVideoItem;
    private OnAttachViewListener onAttachViewListener;

    public DescriptionView(Context context, RecyclerViewVideoItem currentVideoItem)
    {
        this.context = context;
        this.currentVideoItem = currentVideoItem;
        onAttachViewListener = (OnAttachViewListener)context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container)
    {
        view = inflater.inflate(R.layout.bottomsheetdialogfragment_description, container, false);

        profile = view.findViewById(R.id.bottomsheetdialogfragment_description_image_profile);
        buttonClose = view.findViewById(R.id.bottomsheetdialogfragment_description_image_close);
        channelName = view.findViewById(R.id.bottomsheetdialogfragment_description_text_channel_name);
        videoTitle = view.findViewById(R.id.bottomsheetdialogfragment_description_text_video_title);
        likeCount = view.findViewById(R.id.bottomsheetdialogfragment_description_text_like_count);
        viewCount = view.findViewById(R.id.bottomsheetdialogfragment_description_text_view_count);
        date = view.findViewById(R.id.bottomsheetdialogfragment_description_text_date);
        dateYear = view.findViewById(R.id.bottomsheetdialogfragment_description_text_date_year);
        content = view.findViewById(R.id.bottomsheetdialogfragment_description_text_content);

        channelName.setText(currentVideoItem.getChannelDTO().getName());
        videoTitle.setText(currentVideoItem.getVideoDTO().getTitle());
        likeCount.setText(String.valueOf(currentVideoItem.getVideoDTO().getLike()));
        viewCount.setText(String.valueOf(currentVideoItem.getVideoDTO().getViews()));
        content.setText(currentVideoItem.getVideoDTO().getInfo());

        Timestamp timestamp = currentVideoItem.getVideoDTO().getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());

        date.setText((calendar.get(Calendar.MONTH) + 1) + "월" + " " + calendar.get(Calendar.DATE) + "일");
        dateYear.setText(calendar.get(Calendar.YEAR) + "년");

        buttonClose.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.bottomsheetdialogfragment_description_image_close :

                onAttachViewListener.onAttachViewClick(view);

                break;
            default :

                // 의도하지 않은 문제

                break;
        }

    }


}
