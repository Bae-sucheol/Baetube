package com.example.baetube;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.dto.NestedReplyDTO;
import com.example.baetube.dto.ReplyDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewNestedReplyAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewReplyAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewNestedReplyItem;
import com.example.baetube.recyclerview.item.RecyclerViewReplyItem;

import java.util.ArrayList;
import java.util.List;

public class ReplyView implements OnRecyclerViewClickListener, View.OnClickListener
{
    private Context context;
    private View view;
    private RecyclerView recyclerViewReply;
    private RecyclerViewReplyAdapter replyAdapter;
    private ArrayList<RecyclerViewReplyItem> replyList = new ArrayList<>();

    // 닫기 버튼 뷰
    private ImageView buttonClose;
    private ImageView buttonCloseNested;
    private ImageView buttonBackNested;

    private ConstraintLayout layoutReply;
    private CoordinatorLayout layoutNestedReply;

    // 대댓글 관련 속성
    private RecyclerView recyclerViewNestedReply;
    private RecyclerViewNestedReplyAdapter nestedReplyAdapter;
    private ArrayList<RecyclerViewNestedReplyItem> nestedReplyList = new ArrayList<>();

    private OnAttachViewListener onAttachViewListener;

    private int animationDuration;

    private Long contentsId;

    private OnCallbackResponseListener onCallbackResponseListener;

    private OkHttpUtil okHttpUtil;

    public ReplyView(Context context, Long contentsId, OnCallbackResponseListener onCallbackResponseListener)
    {
        this.context = context;
        this.contentsId = contentsId;
        this.onCallbackResponseListener = onCallbackResponseListener;
        onAttachViewListener = (OnAttachViewListener)context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container)
    {
        view = inflater.inflate(R.layout.bottomsheetdialogfragment_reply, container, false);

        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */

        recyclerViewReply = view.findViewById(R.id.bottomsheetdialogfragment_reply_recyclerview);
        replyAdapter = new RecyclerViewReplyAdapter(replyList);
        recyclerViewReply.setAdapter(replyAdapter);
        replyAdapter.setOnRecyclerViewClickListener(this);
        recyclerViewReply.setLayoutManager(new LinearLayoutManager(context));

        buttonClose = view.findViewById(R.id.bottomsheetdialogfragment_reply_image_close);
        buttonCloseNested = view.findViewById(R.id.bottomsheetdialogfragment_nested_reply_image_close);
        buttonBackNested = view.findViewById(R.id.bottomsheetdialogfragment_nested_reply_image_back);

        buttonClose.setOnClickListener(this);
        buttonCloseNested.setOnClickListener(this);
        buttonBackNested.setOnClickListener(this);

        layoutReply = view.findViewById(R.id.bottomsheetdialogfragment_reply_layout);
        layoutNestedReply = view.findViewById(R.id.bottomsheetdialogfragment_nested_reply_layout);

        recyclerViewNestedReply = layoutNestedReply.findViewById(R.id.bottomsheetdialogfragment_nested_reply_recyclerview);
        nestedReplyAdapter = new RecyclerViewNestedReplyAdapter(nestedReplyList);
        recyclerViewNestedReply.setAdapter(nestedReplyAdapter);
        nestedReplyAdapter.setOnRecyclerViewClickListener(this);
        recyclerViewNestedReply.setLayoutManager(new LinearLayoutManager(context));

        animationDuration = context.getResources().getInteger(R.integer.animation_duration_reply);

        requestReply();

        return view;
    }

    public void requestReply()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/reply/select/82";

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_REPLY);

        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    private void requestNestedReply()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/nestedreply/select/26";

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_NESTED_REPLY);

        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    public void setRecyclerViewReply(List<ReplyDTO> replyItems)
    {
        if(replyItems == null)
        {
            return;
        }

        for(int i = 0; i < replyItems.size(); i++)
        {
            RecyclerViewReplyItem item = new RecyclerViewReplyItem();
            item.setReplyDTO(replyItems.get(i));

            replyList.add(item);
        }

        ((Activity)context).runOnUiThread(new Runnable(){
            @Override
            public void run()
            {
                replyAdapter.notifyDataSetChanged();
            }
        });
    }

    public void setRecyclerViewNestedReply(List<NestedReplyDTO> nestedReplyItems)
    {
        if(nestedReplyItems == null)
        {
            return;
        }

        for(int i = 0; i < nestedReplyItems.size(); i++)
        {
            RecyclerViewNestedReplyItem item = new RecyclerViewNestedReplyItem();
            item.setNestedReplyDTO(nestedReplyItems.get(i));

            nestedReplyList.add(item);
        }

        ((Activity)context).runOnUiThread(new Runnable(){
            @Override
            public void run()
            {
                nestedReplyAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.bottomsheetdialogfragment_reply_image_close :


            case R.id.bottomsheetdialogfragment_nested_reply_image_close :

                onAttachViewListener.onAttachViewClick(view);

                break;
            case R.id.bottomsheetdialogfragment_nested_reply_image_back :

                closeNestedReply();
                nestedReplyList.clear();

                break;
            default :

                // 의도하지 않은 문제

                break;
        }

    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (view.getId())
        {
            case R.id.recyclerview_reply_layout_nested_reply :

                openNestedReply();
                requestNestedReply();

                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }

    @Override
    public void onCastVoteOption(VoteDTO voteData, boolean isCancel)
    {

    }

    /**
     * 답글 화면 출력하는 메소드
     */
    public void openNestedReply()
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
    public void closeNestedReply()
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
