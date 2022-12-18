package com.example.baetube;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.baetube.recyclerview.adapter.RecyclerViewReplyAdapter;

import org.w3c.dom.Text;

public class DescriptionView implements View.OnClickListener
{
    private Context context;
    private View view;

    private ImageView buttonClose;

    private TextView videoTitle;
    private TextView likeCount;
    private TextView viewCount;
    private TextView date;
    private TextView dateYear;
    private TextView content;

    private OnAttachViewListener onAttachViewListener;

    public DescriptionView(Context context)
    {
        this.context = context;
        onAttachViewListener = (OnAttachViewListener)context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container)
    {
        view = inflater.inflate(R.layout.bottomsheetdialogfragment_description, container, false);

        buttonClose = view.findViewById(R.id.bottomsheetdialogfragment_description_image_close);
        videoTitle = view.findViewById(R.id.bottomsheetdialogfragment_description_text_video_title);
        likeCount = view.findViewById(R.id.bottomsheetdialogfragment_description_text_like_count);
        viewCount = view.findViewById(R.id.bottomsheetdialogfragment_description_text_view_count);
        date = view.findViewById(R.id.bottomsheetdialogfragment_description_text_date);
        dateYear = view.findViewById(R.id.bottomsheetdialogfragment_description_text_date_year);
        content = view.findViewById(R.id.bottomsheetdialogfragment_description_text_content);

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
