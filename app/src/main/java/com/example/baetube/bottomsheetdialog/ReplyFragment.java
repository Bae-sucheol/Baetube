package com.example.baetube.bottomsheetdialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.UserDisplay;
import com.example.baetube.WidthProperty;
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
    private ImageView buttonCloseNested;
    private ImageView buttonBackNested;

    private ConstraintLayout layoutReply;
    private CoordinatorLayout layoutNestedReply;

    private int animationDuration;

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
        buttonCloseNested = view.findViewById(R.id.bottomsheetdialogfragment_nested_reply_image_close);
        buttonBackNested = view.findViewById(R.id.bottomsheetdialogfragment_nested_reply_image_back);

        buttonClose.setOnClickListener(this);
        buttonCloseNested.setOnClickListener(this);
        buttonBackNested.setOnClickListener(this);

        layoutReply = view.findViewById(R.id.bottomsheetdialogfragment_reply_layout);
        layoutNestedReply = view.findViewById(R.id.bottomsheetdialogfragment_nested_reply_layout);

        animationDuration = getContext().getResources().getInteger(R.integer.animation_duration_reply);

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

                openNestedReply();

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
        switch (view.getId())
        {
            case R.id.bottomsheetdialogfragment_reply_image_close :


            case R.id.bottomsheetdialogfragment_nested_reply_image_close :

                this.dismiss();

                break;
            case R.id.bottomsheetdialogfragment_nested_reply_image_back :

                closeNestedReply();

                break;
            default :

                // 의도하지 않은 문제

                break;
        }
    }

    /**
     * 답글 화면 출력하는 메소드
     */
    private void openNestedReply()
    {
        // 먼저 Visible이 Gone인 답글 화면을 Visible로 전환.
        layoutNestedReply.setVisibility(View.VISIBLE);
        /*
         * width, alpha 값을 순차적으로 변화시킨다.
         * width는 0부터 댓글 화면 사이즈(즉 match_parent) 까지
         * alpha는 0.0f부터 1.0f까지
         */
        PropertyValuesHolder widthProperty = PropertyValuesHolder.ofInt(new WidthProperty(), 0, layoutReply.getWidth());
        PropertyValuesHolder alphaProperty = PropertyValuesHolder.ofFloat("alpha", 1.0f);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(layoutNestedReply, widthProperty, alphaProperty);
        objectAnimator.setDuration(animationDuration);
        objectAnimator.start();
    }

    /**
     * 답글 화면을 종료하고 댓글 화면을 출력하는 메소드
     */
    private void closeNestedReply()
    {
        /*
         * width, alpha 값을 순차적으로 변화시킨다.
         * width는 댓글 화면 사이즈(즉 match_parent) 부터 0 까지
         * alpha는 1.0f부터 0.0f까지
         */
        PropertyValuesHolder widthProperty = PropertyValuesHolder.ofInt(new WidthProperty(), layoutReply.getWidth(), 0);
        PropertyValuesHolder alphaProperty = PropertyValuesHolder.ofFloat("alpha", 0.0f);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(layoutNestedReply, widthProperty, alphaProperty);
        objectAnimator.setDuration(animationDuration);
        objectAnimator.start();

        // 애니메이션이 끝나는 순간 답글 화면의 Visible을 Gone으로 변화시켜야 한다.
        objectAnimator.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animator)
            {

            }

            /*
             * 따라서 onAnimationEnd 메소드를 사용하여 애니메이션이 끝나는 순간
             * 답글 화면의 Visible를 Gone으로 만든다.
             */
            @Override
            public void onAnimationEnd(Animator animator)
            {
                layoutNestedReply.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animator)
            {

            }
        });
    }
}
