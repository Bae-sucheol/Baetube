package com.example.baetube.bottomsheetdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.UserDisplay;
import com.example.baetube.ViewType;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.channel.ChannelBaseFragment;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;
import java.util.ArrayList;

public class VideoFragment extends BottomSheetDialogFragment implements OnRecyclerViewClickListener, View.OnClickListener, View.OnTouchListener
{
    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewVideoAdapter adapter;
    private ArrayList<RecyclerViewVideoItem> list = new ArrayList<>();

    private VideoView player;
    private TextView title;
    private TextView viewCount;
    private TextView date;
    private TextView buttonSubscribe;
    private TextView channelName;
    private TextView subscribeCount;
    private TextView replyCount;
    private TextView likeCount;
    private TextView hateCount;

    private ImageView thumbUp;
    private ImageView thumbDown;
    private ImageView addLibrary;
    private ImageView channelProfile;
    private ImageView profile;
    private ImageView buttonPlayerPlay;
    private ImageView buttonPlayerClose;

    private EditText reply;

    private ConstraintLayout layoutReply;
    private CoordinatorLayout layoutDescription;
    private ConstraintLayout layoutMinMenu;

    private OnCallbackResponseListener onCallbackResponseListener;

    public VideoFragment(OnCallbackResponseListener onCallbackResponseListener)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.bottomsheetdialogfragment_video, container, false);

        test();
        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */

        recyclerView = view.findViewById(R.id.bottomsheetdialogfragment_video_recyclerview);
        adapter = new RecyclerViewVideoAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnRecyclerViewClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        player = view.findViewById(R.id.bottomsheetdialogfragment_video_player);
        title = view.findViewById(R.id.bottomsheetdialogfragment_video_text_title);
        viewCount = view.findViewById(R.id.bottomsheetdialogfragment_video_text_view_count);
        date = view.findViewById(R.id.bottomsheetdialogfragment_video_text_date);
        buttonSubscribe = view.findViewById(R.id.bottomsheetdialogfragment_video_text_button_subscribe);;
        channelName = view.findViewById(R.id.bottomsheetdialogfragment_video_text_channel_name);
        subscribeCount = view.findViewById(R.id.bottomsheetdialogfragment_video_text_subscribe_count);
        replyCount = view.findViewById(R.id.bottomsheetdialogfragment_video_text_reply_count);
        likeCount = view.findViewById(R.id.bottomsheetdialogfragment_video_text_like);
        hateCount = view.findViewById(R.id.bottomsheetdialogfragment_video_text_hate);

        thumbUp = view.findViewById(R.id.bottomsheetdialogfragment_video_image_thumb_up);
        thumbDown = view.findViewById(R.id.bottomsheetdialogfragment_video_image_thumb_down);
        addLibrary = view.findViewById(R.id.bottomsheetdialogfragment_video_image_add_library);
        channelProfile = view.findViewById(R.id.bottomsheetdialogfragment_video_image_channel_profile);
        profile = view.findViewById(R.id.bottomsheetdialogfragment_video_image_profile);

        buttonPlayerPlay = view.findViewById(R.id.bottomsheetdialogfragment_video_image_player_play);
        buttonPlayerClose = view.findViewById(R.id.bottomsheetdialogfragment_video_image_player_close);

        buttonPlayerClose.setOnClickListener(this);

        reply = view.findViewById(R.id.bottomsheetdialogfragment_video_edit_reply);

        layoutReply = view.findViewById(R.id.bottomsheetdialogfragment_video_layout_reply);
        layoutDescription = view.findViewById(R.id.bottomsheetdialogfragment_video_layout_description);
        layoutMinMenu = view.findViewById(R.id.bottomsheetdialogfragment_video_min_menu);

        thumbUp.setOnClickListener(this);
        thumbDown.setOnClickListener(this);
        addLibrary.setOnClickListener(this);
        channelProfile.setOnClickListener(this);
        layoutReply.setOnClickListener(this);

        this.setCancelable(false);

        return view;
    }


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

        int peekHeight = (int)(UserDisplay.getWidth() * 0.16);

        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = (int)UserDisplay.getHeight();
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        behavior.setPeekHeight(peekHeight);
       // behavior.addBottomSheetCallback(new VideoBottomSheetCallback(player.getHeight(), player, layoutDescription, behavior));

    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (view.getId())
        {


            default :
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

    public void test()
    {
        String channel_names[] = {"홍길동", "이순신", "장영실", "김유신", "허준"};
        String titles[] = {"쉽게 배우는 자바", "쉽게 배우는 학익진", "쉽게 배우는 거중기",
                "쉽게 배우는 전투법", "쉽게 배우는 침술"};

        for(int i = 0; i < 5; i++)
        {
            RecyclerViewVideoItem item = new RecyclerViewVideoItem();

            ChannelDTO channelDTO = new ChannelDTO();
            VideoDTO videoDTO = new VideoDTO();

            item.setChannelDTO(channelDTO);
            item.setVideoDTO(videoDTO);
            item.setViewType(ViewType.VIDEO_LARGE);

            channelDTO.setName(channel_names[i]);
            videoDTO.setDate(new Timestamp(System.currentTimeMillis()));
            videoDTO.setTitle(titles[i]);
            videoDTO.setViews(500);

            list.add(item);
        }

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.bottomsheetdialogfragment_video_image_thumb_up :

                // 좋아요 기능

                break;
            case R.id.bottomsheetdialogfragment_video_image_thumb_down :

                // 싫어요 기능

                break;
            case R.id.bottomsheetdialogfragment_video_image_add_library :

               //AddPlaylistFragment addPlaylistFragment = new AddPlaylistFragment(getContext());
                //addPlaylistFragment.show(getParentFragmentManager(), addPlaylistFragment.getTag());

                break;
            case R.id.bottomsheetdialogfragment_video_image_channel_profile :

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ChannelBaseFragment(getContext(), onCallbackResponseListener, 1));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.bottomsheetdialogfragment_video_layout_reply :

                ReplyFragment replyFragment = new ReplyFragment();
                replyFragment.show(getParentFragmentManager(), replyFragment.getTag());

                break;
            case R.id.bottomsheetdialogfragment_video_image_player_close :

                dismiss();

                break;
            default :
                break;
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {

        return false;
    }
}
