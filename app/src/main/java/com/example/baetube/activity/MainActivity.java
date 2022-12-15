package com.example.baetube.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Property;
import android.view.Display;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.LinearInterpolation;
import com.example.baetube.OnBottomSheetInteractionListener;
import com.example.baetube.OnFragmentInteractionListener;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.TestMotionLayoutFragment;
import com.example.baetube.UserDisplay;
import com.example.baetube.VideoBottomSheetCallback;
import com.example.baetube.ViewType;
import com.example.baetube.WidthProperty;
import com.example.baetube.bottomsheetdialog.AddPlaylistFragment;
import com.example.baetube.bottomsheetdialog.BaseOptionFragment;
import com.example.baetube.bottomsheetdialog.BaseReportFragment;
import com.example.baetube.bottomsheetdialog.CommunityOptionFragment;
import com.example.baetube.bottomsheetdialog.CommunityReportFragment;
import com.example.baetube.bottomsheetdialog.PlaylistOptionFragment;
import com.example.baetube.bottomsheetdialog.PlaylistOptionManageFragment;
import com.example.baetube.bottomsheetdialog.PlaylistVideoOptionManageFragment;
import com.example.baetube.bottomsheetdialog.ReplyFragment;
import com.example.baetube.bottomsheetdialog.ReplyReportFragment;
import com.example.baetube.bottomsheetdialog.UploadOptionFragment;
import com.example.baetube.bottomsheetdialog.VideoFragment;
import com.example.baetube.bottomsheetdialog.VideoOptionFragment;
import com.example.baetube.bottomsheetdialog.VideoOptionManageFragment;
import com.example.baetube.bottomsheetdialog.VideoReportFragment;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.ReplyDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.fragment.HomeFragment;
import com.example.baetube.R;
import com.example.baetube.fragment.SearchFragment;
import com.example.baetube.fragment.StorageFragment;
import com.example.baetube.fragment.SubscribeFragment;
import com.example.baetube.fragment.channel.ChannelBaseFragment;
import com.example.baetube.fragment.upload.UploadVideoListFragment;
import com.example.baetube.recyclerview.adapter.RecyclerViewReplyAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewReplyItem;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener, OnRecyclerViewClickListener,
        View.OnClickListener, OnBottomSheetInteractionListener
{
    // 바텀 네비게이션
    private BottomNavigationView bottomNavigationView;
    private int navigationHeight;
    private int bottomBarHeight;
    // 프래그먼트 매니저
    private FragmentManager fragmentManager;
    // 바텀시트 다이얼로그(동영상)
    private CoordinatorLayout bottomSheetVideo;
    // 바텀시트 비헤이버
    private BottomSheetBehavior bottomSheetVideoBehavior;
    // 바텀 시트 콜백
    private VideoBottomSheetCallback videoBottomSheetCallback;
    // 바텀 시트 피크 높이
    private int bottomSheetPeekHeight;
    // 바텀시트 내부 요소들.
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

    private ConstraintLayout layoutWriteReply;
    private CoordinatorLayout layoutDescription;
    private ConstraintLayout layoutMinMenu;

    /*
     * 관련 동영상을 출력하기 위한 리사이클러뷰, 어댑터, 리스트
     */
    private RecyclerView relatedVideoRecyclerView;
    private RecyclerViewVideoAdapter relatedVideoAdapter;
    private ArrayList<RecyclerViewVideoItem> relatedVideoList = new ArrayList<>();

    // 바텀시트 내부 바텀시트(댓글 답글)
    private FrameLayout bottomSheetReply;
    // 바텀시트 비헤이버
    private BottomSheetBehavior bottomSheetReplyBehavior;

    // 댓글 답글 바텀시트 내부 요소
    private RecyclerView replyRecyclerView;
    private RecyclerViewReplyAdapter replyAdapter;
    private ArrayList<RecyclerViewReplyItem> replyList = new ArrayList<>();

    // 닫기 버튼 뷰
    private ImageView buttonReplyClose;
    private ImageView buttonNestedReplyClose;
    private ImageView buttonNestedReplyBack;

    private ConstraintLayout layoutReply;
    private CoordinatorLayout layoutNestedReply;

    private int animationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 디스플레이 사이즈를 구해서 UserDisplay 클래스에 저장한다.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();

        display.getMetrics(displayMetrics);

        Point size = new Point();

        display.getSize(size);
        UserDisplay.setWidth(size.x);
        UserDisplay.setHeight(size.y);
        UserDisplay.setDensity(displayMetrics.density);

        // 프래그먼트 매니저를 지정
        fragmentManager = getSupportFragmentManager();

        // 프래그먼트 매니저에 HomeFragment를 추가하고 커밋한다. ( 첫 화면 지정 )
        fragmentManager.beginTransaction().replace(R.id.activity_main_layout, new HomeFragment()).addToBackStack(null).commit();
        //fragmentManager.beginTransaction().add(R.id.activity_main_layout, new ChannelBaseFragment()).commit();

        // 바텀 네비게이션 요소를 findViewById를 사용하여 찾는다.
        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation);

        // 바텀바(뒤로가기 홈) 높이
        bottomBarHeight = getResources().getDimensionPixelSize(getResources().getIdentifier(
                "navigation_bar_height", "dimen", "android"));

        // 뷰의 상태가 변하면(그려지면) Height를 구하고 리스너를 제거하여 다시 실행되지 않도록 한다.
        bottomNavigationView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                navigationHeight = bottomNavigationView.getHeight();
                bottomNavigationView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        // 바텀 네비게이션 아이템 선택 이벤트.
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {

                switch(item.getItemId())
                {
                    // 홈 아이콘 클릭 시 해당 프래그먼트로 리플레이스
                    case R.id.menu_bottom_navigation_home :
                        fragmentManager.beginTransaction().replace(R.id.activity_main_layout, new HomeFragment()).commit();
                        break;
                    // 업로드 아이콘 클릭 시.
                    case R.id.menu_bottom_navigation_upload :
                        // 업로드 옵션 프래그먼트를 출력한다.
                        // 출력 시 비디오 업로드, 게시글 작성 2가지 옵션이 출력.
                        UploadOptionFragment uploadOptionFragment = new UploadOptionFragment(getApplicationContext());
                        uploadOptionFragment.show(fragmentManager, uploadOptionFragment.getTag());

                        break;
                    // 구독 아이콘 클릭 시 해당 프래그먼트로 리플레이스
                    case R.id.menu_bottom_navigation_subscribe :
                        fragmentManager.beginTransaction().replace(R.id.activity_main_layout, new SubscribeFragment()).commit();
                        break;
                    // 보관함 아이콘 클릭 시 해당 프래그머늩로 리플레이스
                    case R.id.menu_bottom_navigation_storage :
                        fragmentManager.beginTransaction().replace(R.id.activity_main_layout, new StorageFragment()).commit();
                        break;
                }

                return true;
            }
        });

        // 바텀 시트 요소를 findViewById를 사용하여 찾는다.
        bottomSheetVideo = findViewById(R.id.activity_main_bottomsheet_video);
        // 바텀시트 비헤이버를 from 메소드를 통해 설정.
        bottomSheetVideoBehavior = BottomSheetBehavior.from(bottomSheetVideo);
        bottomSheetVideoBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        // 바텀시트 내부 요소 찾기
        player = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_player);

        title = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_text_title);
        viewCount = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_text_view_count);
        date = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_text_date);
        buttonSubscribe = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_text_button_subscribe);;
        channelName = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_text_channel_name);
        subscribeCount = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_text_subscribe_count);
        replyCount = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_text_reply_count);
        likeCount = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_text_like);
        hateCount = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_text_hate);

        thumbUp = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_image_thumb_up);
        thumbDown = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_image_thumb_down);
        addLibrary = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_image_add_library);
        channelProfile = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_image_channel_profile);
        profile = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_image_profile);

        relatedVideoRecyclerView = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_recyclerview);

        buttonPlayerPlay = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_image_player_play);
        buttonPlayerClose = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_image_player_close);

        reply = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_edit_reply);

        // 댓글 답글 바텀시트 내부 요소
        layoutWriteReply = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_layout_reply);
        layoutDescription = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_layout_description);
        layoutMinMenu = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_min_menu);

        bottomSheetReply = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_bottomsheet_reply);
        // 바텀시트 비헤이버를 from 메소드를 통해 설정.
        bottomSheetReplyBehavior = BottomSheetBehavior.from(bottomSheetReply);
        bottomSheetReplyBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        buttonReplyClose = bottomSheetReply.findViewById(R.id.bottomsheetdialogfragment_reply_image_close);
        buttonNestedReplyClose = bottomSheetReply.findViewById(R.id.bottomsheetdialogfragment_nested_reply_image_close);
        buttonNestedReplyBack = bottomSheetReply.findViewById(R.id.bottomsheetdialogfragment_nested_reply_image_back);

        replyRecyclerView = bottomSheetReply.findViewById(R.id.bottomsheetdialogfragment_reply_recyclerview);

        layoutReply = bottomSheetReply.findViewById(R.id.bottomsheetdialogfragment_reply_layout);
        layoutNestedReply = bottomSheetReply.findViewById(R.id.bottomsheetdialogfragment_nested_reply_layout);

        thumbUp.setOnClickListener(this);
        thumbDown.setOnClickListener(this);
        addLibrary.setOnClickListener(this);
        channelProfile.setOnClickListener(this);
        layoutWriteReply.setOnClickListener(this);

        buttonPlayerPlay.setOnClickListener(this);
        buttonPlayerClose.setOnClickListener(this);

        buttonReplyClose.setOnClickListener(this);
        buttonNestedReplyClose.setOnClickListener(this);
        buttonNestedReplyBack.setOnClickListener(this);

        bottomSheetPeekHeight = (int)(UserDisplay.getWidth() * 0.16);

        videoBottomSheetCallback = new VideoBottomSheetCallback(this,
                player, layoutDescription, bottomSheetVideoBehavior, bottomSheetPeekHeight);

        bottomSheetVideoBehavior.addBottomSheetCallback(videoBottomSheetCallback);

        animationDuration = this.getResources().getInteger(R.integer.animation_duration_reply);

        test();
        test2();
        /*
         * 1. 리사이클러뷰 어댑터 객체 생성
         * 2. 리사이클러뷰 어댑터 설정
         * 3. 리사이클러뷰 레이아웃 매니저 설정
         */

        relatedVideoAdapter = new RecyclerViewVideoAdapter(relatedVideoList);
        relatedVideoRecyclerView.setAdapter(relatedVideoAdapter);
        relatedVideoAdapter.setOnRecyclerViewClickListener(this);
        relatedVideoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        replyAdapter = new RecyclerViewReplyAdapter(replyList);
        replyRecyclerView.setAdapter(replyAdapter);
        replyAdapter.setOnRecyclerViewClickListener(this);
        replyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }



    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    @Override
    public void onVideoItemClick()
    {
        bottomSheetVideoBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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
            videoDTO.setDate("1시간 전");
            videoDTO.setTitle(titles[i]);
            videoDTO.setViews(500);

            relatedVideoList.add(item);
        }

    }

    public void test2()
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

            replyList.add(item);
        }

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.bottomsheetdialogfragment_video_image_player_play :

                // 플레이 or 일시정지 기능
                // icon 이미지도 바꿔야한다.

                break;
            case R.id.bottomsheetdialogfragment_video_image_player_close :

                bottomSheetVideoBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                break;
            case R.id.bottomsheetdialogfragment_video_image_thumb_up :

                // 좋아요 기능

                break;
            case R.id.bottomsheetdialogfragment_video_image_thumb_down :

                // 싫어요 기능

                break;
            case R.id.bottomsheetdialogfragment_video_image_add_library :

                AddPlaylistFragment addPlaylistFragment = new AddPlaylistFragment(this);
                addPlaylistFragment.show(fragmentManager, addPlaylistFragment.getTag());

                break;
            case R.id.bottomsheetdialogfragment_video_image_channel_profile :

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ChannelBaseFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.bottomsheetdialogfragment_video_layout_reply :

                bottomSheetReplyBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                break;
            case R.id.bottomsheetdialogfragment_reply_image_close :

                bottomSheetReplyBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

            case R.id.bottomsheetdialogfragment_nested_reply_image_close :

                bottomSheetReplyBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.bottomsheetdialogfragment_nested_reply_image_back :

                closeNestedReply();

                break;
            default :
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

    @Override
    public void onSlide(View bottomSheet, float slideOffset)
    {
        if(slideOffset >= 0)
        {
            bottomNavigationView.setY((int) UserDisplay.getHeight() - bottomBarHeight / 2 - navigationHeight * (1 - slideOffset));
            bottomSheet.setY(LinearInterpolation.Lerp(0, bottomNavigationView.getY() - bottomSheetPeekHeight, 1 - slideOffset));
        }
    }

}