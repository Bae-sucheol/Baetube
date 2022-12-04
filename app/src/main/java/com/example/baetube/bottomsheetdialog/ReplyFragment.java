package com.example.baetube.bottomsheetdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.UserDisplay;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.ReplyDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewReplyAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewReplyItem;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class ReplyFragment extends BottomSheetDialogFragment implements OnRecyclerViewClickListener, View.OnClickListener
{
    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewReplyAdapter adapter;
    private ArrayList<RecyclerViewReplyItem> list = new ArrayList<>();

    // 닫기 버튼 뷰
    private ImageView buttonClose;

    private CoordinatorLayout layoutNestedReply;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.bottomsheetdialogfragment_reply, container, false);

        test();
        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */

        recyclerView = view.findViewById(R.id.bottomsheetdialogfragment_reply_recyclerview);
        adapter = new RecyclerViewReplyAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnRecyclerViewClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        buttonClose = view.findViewById(R.id.bottomsheetdialogfragment_reply_image_close);

        buttonClose.setOnClickListener(this);

        layoutNestedReply = view.findViewById(R.id.bottomsheetdialogfragment_nested_reply_layout);


        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialogInterface)
            {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog)dialogInterface;
                setRatio(bottomSheetDialog);
            }
        });

        return dialog;
    }

    public void setRatio(BottomSheetDialog bottomSheetDialog)
    {
        FrameLayout bottomSheet = (FrameLayout)
                bottomSheetDialog.findViewById(R.id.design_bottom_sheet);

        bottomSheet.setBackgroundResource(R.drawable.bottomsheetdialog_border);

        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = (int) UserDisplay.getHeight();
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (view.getId())
        {
            case R.id.recyclerview_reply_layout_nested_reply :

                layoutNestedReply.setVisibility(View.VISIBLE);
                layoutNestedReply.setAlpha(1.0f);

                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }

    public void test()
    {
        String channel_names[] = {"홍길동", "이순신", "장영실", "김유신", "허준"};
        String comments[] = {"쉽게 배우는 자바", "쉽게 배우는 학익진", "쉽게 배우는 거중기",
                "쉽게 배우는 전투법", "쉽게 배우는 침술"};

        for(int i = 0; i < 30; i++)
        {
            RecyclerViewReplyItem item = new RecyclerViewReplyItem();

            ReplyDTO replyDTO = new ReplyDTO();
            ChannelDTO channelDTO = new ChannelDTO();

            channelDTO.setName(channel_names[i % 5]);
            replyDTO.setDate("1시간 전");
            replyDTO.setComment(comments[i % 5]);
            replyDTO.setLike(50);
            replyDTO.setHate(5);

            item.setChannelDTO(channelDTO);
            item.setReplyDTO(replyDTO);

            list.add(item);
        }

    }

    @Override
    public void onClick(View view)
    {
        this.dismiss();
    }
}
