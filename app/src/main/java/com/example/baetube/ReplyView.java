package com.example.baetube;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.activity.MainActivity;
import com.example.baetube.dto.NestedReplyDTO;
import com.example.baetube.dto.ReplyDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewNestedReplyAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewReplyAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewNestedReplyItem;
import com.example.baetube.recyclerview.item.RecyclerViewReplyItem;

import java.util.ArrayList;
import java.util.List;

public class ReplyView implements OnRecyclerViewClickListener, View.OnClickListener, View.OnFocusChangeListener
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

    private EditText editReply;
    private EditText editNestedReply;

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
        editNestedReply = view.findViewById(R.id.bottomsheetdialogfragment_nested_reply_edit_reply);
        editReply = view.findViewById(R.id.bottomsheetdialogfragment_reply_edit_reply);

        buttonClose.setOnClickListener(this);
        buttonCloseNested.setOnClickListener(this);
        buttonBackNested.setOnClickListener(this);
        editNestedReply.setOnFocusChangeListener(this);
        editReply.setOnFocusChangeListener(this);

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

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/reply/select/" + contentsId;

        System.out.println("url : " + url);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_REPLY);

        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    public void requestNestedReply(Integer replyId)
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/nestedreply/select/" + replyId;

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_NESTED_REPLY);

        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    public void clearReplyList()
    {
        replyList.clear();
    }

    public void clearNestedReplyList()
    {
        nestedReplyList.clear();
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

        System.out.println("다시 불러왔습니다.");

        for(int i = 0; i < nestedReplyItems.size(); i++)
        {
            RecyclerViewNestedReplyItem item = new RecyclerViewNestedReplyItem();
            item.setNestedReplyDTO(nestedReplyItems.get(i));

            nestedReplyList.add(item);
        }

        ((Activity)context).runOnUiThread(new Runnable()
        {
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
                requestNestedReply(replyList.get(position).getReplyDTO().getReplyId());
                ((MainActivity)context).setCurrentReplyData(replyList.get(position));

                break;

            case R.id.recyclerview_reply_image_write_nested_reply :

                // 댓글에 답글(대댓글)을 달기 위한 버튼.
                ((MainActivity)context).setCurrentReplyData(replyList.get(position));
                openNestedReply();
                editNestedReply.requestFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                // 해당 댓글에 대댓글이 존재한다면(1개 이상) 해당 댓글에 달린 대댓글을 불러와야 한다.
                if(replyList.get(position).getReplyDTO().getNestedCount() > 0)
                {
                    requestNestedReply(replyList.get(position).getReplyDTO().getReplyId());
                }

                break;

            case R.id.recyclerview_reply_image_option :

                // 옵션 클릭
                // 대댓글(답글)레이아웃의 visibility가 Gone이면 댓글창 이므로 댓글에 대한 정보를 메인 엑티비티에 넘겨준다.
                if(layoutNestedReply.getVisibility() == View.GONE)
                {

                }
                else
                {

                }

                

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

    @Override
    public void onFocusChange(View view, boolean b)
    {
        // 메인 엑티비티로 넘겨서 관리한다.
        ((MainActivity)context).onFocusChange(view, b);
    }
}
