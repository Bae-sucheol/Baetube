package com.example.baetube.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.DateToStringUtil;
import com.example.baetube.DescriptionView;
import com.example.baetube.FragmentTagUtil;
import com.example.baetube.KeyboardState;
import com.example.baetube.LinearInterpolation;
import com.example.baetube.OkHttpUtil;
import com.example.baetube.OnAttachViewListener;
import com.example.baetube.OnBottomSheetInteractionListener;
import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.OnDialogInteractionListener;
import com.example.baetube.OnFragmentInteractionListener;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.PreferenceManager;
import com.example.baetube.R;
import com.example.baetube.ReplyView;
import com.example.baetube.TimestampUtil;
import com.example.baetube.UserDisplay;
import com.example.baetube.VideoBottomSheetCallback;
import com.example.baetube.ViewType;
import com.example.baetube.bottomsheetdialog.AddPlaylistFragment;
import com.example.baetube.bottomsheetdialog.PlayerOptionFragment;
import com.example.baetube.bottomsheetdialog.UploadOptionFragment;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.CommunityDTO;
import com.example.baetube.dto.ContentsDTO;
import com.example.baetube.dto.HistoryDTO;
import com.example.baetube.dto.NestedReplyDTO;
import com.example.baetube.dto.PlaylistDTO;
import com.example.baetube.dto.PlaylistItemDTO;
import com.example.baetube.dto.RateDTO;
import com.example.baetube.dto.ReplyDTO;
import com.example.baetube.dto.SubscribersDTO;
import com.example.baetube.dto.UserDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.dto.ViewPagerFragmentData;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.AddStorageFragment;
import com.example.baetube.fragment.HomeFragment;
import com.example.baetube.fragment.PlaylistDetailFragment;
import com.example.baetube.fragment.StorageFragment;
import com.example.baetube.fragment.SubscribeFragment;
import com.example.baetube.fragment.channel.ChannelBaseFragment;
import com.example.baetube.fragment.channel.ChannelCommunityFragment;
import com.example.baetube.fragment.channel.ChannelHomeFragment;
import com.example.baetube.fragment.channel.ChannelManageVideoFragment;
import com.example.baetube.fragment.channel.ChannelPlaylistFragment;
import com.example.baetube.fragment.channel.ChannelVideoFragment;
import com.example.baetube.fragment.modify.ModifyVideoFragment;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewReplyItem;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener, OnRecyclerViewClickListener,
        View.OnClickListener, OnBottomSheetInteractionListener, OnAttachViewListener, View.OnFocusChangeListener
{
    // 바텀 네비게이션
    private BottomNavigationView bottomNavigationView;
    private int navigationHeight;
    private int bottomBarHeight;
    // 프래그먼트 매니저
    private FragmentManager fragmentManager;
    //

    // 바텀시트 다이얼로그(동영상)
    private CoordinatorLayout bottomSheetVideo;
    // 바텀시트 비헤이버
    private BottomSheetBehavior bottomSheetVideoBehavior;
    // 바텀 시트 콜백
    private VideoBottomSheetCallback videoBottomSheetCallback;
    // 바텀 시트 피크 높이
    private int bottomSheetPeekHeight;
    // 바텀시트 내부 요소들.
    private StyledPlayerView player;

    private TextView title;
    private TextView viewCount;
    private TextView date;
    private TextView buttonSubscribe;
    private TextView channelName;
    private TextView subscribeCount;
    private TextView replyCount;
    private TextView likeCount;
    private TextView hateCount;

    private ImageView buttonDetail;
    private ImageView thumbUp;
    private ImageView thumbDown;
    private ImageView addLibrary;
    private ImageView channelProfile;
    private ImageView profile;
    private ImageView bottomsheetPlayerPlayButton;
    private ImageView bottomsheetPlayerCloseButton;

    private EditText reply;

    private ConstraintLayout layoutWriteReply;
    private CoordinatorLayout layoutDescription;
    private ConstraintLayout layoutMinMenu;

    private ExoPlayer exoPlayer;

    /*
     * 관련 동영상을 출력하기 위한 리사이클러뷰, 어댑터, 리스트
     */
    private RecyclerView relatedVideoRecyclerView;
    private RecyclerViewVideoAdapter relatedVideoAdapter;
    private ArrayList<RecyclerViewVideoItem> relatedVideoList = new ArrayList<>();

    // 바텀시트 내부 바텀시트(댓글 답글, 동영상 상세 정보)
    private FrameLayout bottomSheetSub;
    // 바텀시트 비헤이버
    private BottomSheetBehavior bottomSheetSubBehavior;

    // 커스텀 콜백 리스너(모든 http 관련 핸들링은 여기서 처리)
    private OnCallbackResponseListener onCallbackResponseListener;

    // 리사이클러뷰에서 포커싱 된 플레이어 뷰를 저장하기 위한 변수.
    // 초기에는 바텀시트에 있는 플레이어를 사용하여 초기화를 한다.
    private StyledPlayerView focusedPlayer;
    private ImageView focusedThumbnail;

    // 플레이중인 동영상의 정보를 저장하기 위한 변수
    private RecyclerViewVideoItem currentVideoItem;

    // 댓글, 설명뷰를 바텀시트 서브에 추가하기 위한 변수.
    private ReplyView replyView;
    private DescriptionView descriptionView;

    // 플레이어 내부 요소
    private ImageView playerPlayButton;
    private ImageView playerPauseButton;
    private ImageView playerFullscreenButton;
    private ImageView playerSeekBack;
    private ImageView playerSeekForward;
    private ImageView playerOptionButton;

    // 플레이어에 기능을 추가하기 위한 제스쳐
    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener;
    private GestureDetector gestureDetector;

    // 플레이어 컨트롤러의 visibility 상태.
    private int playerControllerVisibility;

    // 플레이어 전체화면 여부
    private int playerScreenMode;

    // 다이얼로그 관련 내용을 전달받기 위한 리스너
    private OnDialogInteractionListener onDialogInteractionListener;

    // 현재 동영상 URL을 저장하기 위한 변수
    private String playerUrl;
    private String currentPlayerResolution;
    private OkHttpUtil okHttpUtil;

    /* 댓글 답글 등 EditText를 입력할 때 키보드에 가려지게 되는데
        * 화면의 UI 사이즈를 조절하는 것 보다.
        * 가상키보드 위에 임시로 EditText를 생성하고 해당 EditText에 입력하면 화면이 가려져도
        * 현재 작성하고 있는 내용을 바로바로 확인할 수 있다.
    */
    // 가상 키보드위에 출력할 레이아웃
    private ConstraintLayout layoutKeyboardEdit;
    // 작성 버튼
    private ImageButton keyboardEditButton;
    // 가상키보드로 입력할 EditText
    private EditText keyboardEditInput;
    // 가상키보드가 열리고 닫히는 것을 탐지하기 위한 리스너
    private KeyboardState.HeightListener heightListener;
    // 댓글인지 답글인지 구분하기 위한 boolean 변수
    private boolean isReplyFocused;
    // 현재 포커싱된 댓글의 data를 저장하기 위한 변수
    private RecyclerViewReplyItem currentReplyItem;
    // 동영상을 관리하거나 신고할 때 선택된 동영상을 저장하기 위한 변수
    private RecyclerViewVideoItem managedVideoItem;

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

        setOnCallbackResponseListener();

        // 프래그먼트 매니저를 지정
        fragmentManager = getSupportFragmentManager();

        //HomeFragment homeFragment = new HomeFragment(onCallbackResponseListener);
        //homeFragment.setActivityResultLauncher(activityResultLauncher);

        fragmentManager.beginTransaction().replace(R.id.activity_main_layout, new HomeFragment(onCallbackResponseListener), FragmentTagUtil.FRAGMENT_TAG_HOME).addToBackStack(null).commit();

        // 프래그먼트 매니저에 HomeFragment를 추가하고 커밋한다. ( 첫 화면 지정 )
        //fragmentManager.beginTransaction().replace(R.id.activity_main_layout, homeFragment).addToBackStack(null).commit();
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

        MainActivity activity = this;

        // 바텀 네비게이션 아이템 선택 이벤트.
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    // 홈 아이콘 클릭 시 해당 프래그먼트로 리플레이스
                    case R.id.menu_bottom_navigation_home:
                        fragmentManager.beginTransaction().replace(R.id.activity_main_layout, new HomeFragment(onCallbackResponseListener),
                                FragmentTagUtil.FRAGMENT_TAG_HOME).commit();
                        break;
                    // 업로드 아이콘 클릭 시.
                    case R.id.menu_bottom_navigation_upload:

                        String[] permissions = {
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        };

                        // 배열로 저장한 권한 중 허용되지 않은 권한이 있는지 체크
                        for (String pm : permissions)
                        {
                            int result = ContextCompat.checkSelfPermission(getApplicationContext(), pm);

                            if (result != PackageManager.PERMISSION_GRANTED)
                            {
                                ActivityCompat.requestPermissions(activity, permissions, 1);
                                break;
                            }
                        }

                        // 업로드 옵션 프래그먼트를 출력한다.
                        // 출력 시 비디오 업로드, 게시글 작성 2가지 옵션이 출력.
                        UploadOptionFragment uploadOptionFragment = new UploadOptionFragment(getApplicationContext());
                        uploadOptionFragment.show(fragmentManager, uploadOptionFragment.getTag());

                        break;
                    // 구독 아이콘 클릭 시 해당 프래그먼트로 리플레이스
                    case R.id.menu_bottom_navigation_subscribe:
                        fragmentManager.beginTransaction().replace(R.id.activity_main_layout, new SubscribeFragment(onCallbackResponseListener),
                                getResources().getString(R.string.fragment_tag_subscribe)).commit();
                        System.out.println("프래그먼트 커밋");
                        break;
                    // 보관함 아이콘 클릭 시 해당 프래그머늩로 리플레이스
                    case R.id.menu_bottom_navigation_storage:
                        fragmentManager.beginTransaction().replace(R.id.activity_main_layout, new StorageFragment(onCallbackResponseListener),
                                FragmentTagUtil.FRAGMENT_TAG_STORAGE).commit();
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
        buttonSubscribe = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_text_button_subscribe);

        channelName = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_text_channel_name);
        subscribeCount = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_text_subscribe_count);
        replyCount = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_text_reply_count);
        likeCount = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_text_like);
        hateCount = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_text_hate);

        buttonDetail = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_image_detail);
        thumbUp = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_image_thumb_up);
        thumbDown = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_image_thumb_down);
        addLibrary = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_image_add_library);
        channelProfile = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_image_channel_profile);
        profile = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_image_profile);

        relatedVideoRecyclerView = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_recyclerview);

        bottomsheetPlayerPlayButton = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_image_player_play);
        bottomsheetPlayerCloseButton = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_image_player_close);

        reply = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_edit_reply);

        // 댓글 답글 바텀시트 내부 요소
        layoutWriteReply = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_layout_reply);
        layoutDescription = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_layout_description);
        layoutMinMenu = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_min_menu);

        bottomSheetSub = bottomSheetVideo.findViewById(R.id.bottomsheetdialogfragment_video_bottomsheet_sub);
        // 바텀시트 비헤이버를 from 메소드를 통해 설정.
        bottomSheetSubBehavior = BottomSheetBehavior.from(bottomSheetSub);
        bottomSheetSubBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        // 플레이어 내부 요소
        playerPlayButton = player.findViewById(R.id.exo_play);
        playerPauseButton = player.findViewById(R.id.exo_pause);
        playerFullscreenButton = player.findViewById(R.id.exo_fullscreen_button);
        playerSeekBack = player.findViewById(R.id.exo_rew);
        playerSeekForward = player.findViewById(R.id.exo_ffwd);
        playerOptionButton = player.findViewById(R.id.exo_option);

        layoutKeyboardEdit = findViewById(R.id.activity_main_layout_keyboard_edit);

        keyboardEditButton = findViewById(R.id.activity_main_keyboard_edit_button_send);
        keyboardEditInput = findViewById(R.id.activity_main_keyboard_edit_input);

        playerScreenMode = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        buttonSubscribe.setOnClickListener(this);
        buttonDetail.setOnClickListener(this);
        thumbUp.setOnClickListener(this);
        thumbDown.setOnClickListener(this);
        addLibrary.setOnClickListener(this);
        channelProfile.setOnClickListener(this);
        layoutWriteReply.setOnClickListener(this);

        bottomsheetPlayerPlayButton.setOnClickListener(this);
        bottomsheetPlayerCloseButton.setOnClickListener(this);

        playerPlayButton.setOnClickListener(this);
        playerPauseButton.setOnClickListener(this);
        playerFullscreenButton.setOnClickListener(this);
        playerOptionButton.setOnClickListener(this);
        keyboardEditButton.setOnClickListener(this);
        reply.setOnFocusChangeListener(this);

        bottomSheetPeekHeight = (int) (UserDisplay.getWidth() * 0.16);

        videoBottomSheetCallback = new VideoBottomSheetCallback(this,
                player, layoutDescription, bottomSheetVideoBehavior, bottomSheetPeekHeight);

        bottomSheetVideoBehavior.addBottomSheetCallback(videoBottomSheetCallback);

        //test();
        /*
         * 1. 리사이클러뷰 어댑터 객체 생성
         * 2. 리사이클러뷰 어댑터 설정
         * 3. 리사이클러뷰 레이아웃 매니저 설정
         */
        relatedVideoAdapter = new RecyclerViewVideoAdapter(relatedVideoList);
        relatedVideoRecyclerView.setAdapter(relatedVideoAdapter);
        relatedVideoAdapter.setOnRecyclerViewClickListener(this);
        relatedVideoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 초기 컨트롤러의 visibility 는 Gone 이다.
        playerControllerVisibility = View.GONE;

        player.setControllerVisibilityListener(new StyledPlayerControlView.VisibilityListener()
        {
            @Override
            public void onVisibilityChange(int visibility)
            {
                playerControllerVisibility = visibility;
            }
        });

        setSimpleOnGestureListener();
        gestureDetector = new GestureDetector(this, simpleOnGestureListener);
        player.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                if (gestureDetector != null)
                {
                    return gestureDetector.onTouchEvent(motionEvent);
                }

                return true;
            }
        });

        setOnDialogInteractionListener();

        heightListener = new KeyboardState.HeightListener()
        {
            @Override
            public void onHeightChanged(int height)
            {
                if(height == 0)
                {
                    layoutKeyboardEdit.setVisibility(View.GONE);
                    keyboardEditInput.setText(null);
                }
                else
                {
                    Fragment loginFragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_LOGIN);
                    Fragment signInFragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_SIGN_IN);

                    if(loginFragment != null || signInFragment != null)
                    {
                        layoutKeyboardEdit.setVisibility(View.GONE);
                        keyboardEditInput.setText(null);
                    }
                    else
                    {
                        layoutKeyboardEdit.setVisibility(View.VISIBLE);
                        keyboardEditInput.requestFocus();
                    }
                }
            }
        };

        new KeyboardState(this).init().setHeightListener(heightListener);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    public void setManagedVideoItem(RecyclerViewVideoItem managedVideoItem)
    {
        this.managedVideoItem = managedVideoItem;
    }

    @Override
    public void onVideoItemClick(RecyclerViewVideoItem videoItem)
    {
        currentVideoItem = videoItem;

        setBottomSheetVideoInfo(videoItem.getVideoDTO(), videoItem.getChannelDTO());

        // 이전에 재생되던 항목의 썸네일을 visible로 플레이어는 보이지 않게 invisible로 설정.
        if (focusedThumbnail != null)
        {
            focusedThumbnail.setVisibility(View.VISIBLE);
        }

        bottomSheetVideoBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        releasePlayer(focusedPlayer);

        initializePlayer(player);

        setMediaSource(currentVideoItem.getVideoDTO().getUrl(), "1080");

        // 임시로 20으로
        requestViewVideo(20, videoItem.getVideoDTO().getVideoId());

        requestRelatedVideo(videoItem.getVideoDTO().getVideoId());

        // 임시로 4, 5
        requestSelectSubscribe(videoItem.getChannelDTO().getChannelId(), 5);
    }

    @Override
    public void onCompletelyVisible(FrameLayout layout, String uuid)
    {
        // 화면에 바텀시트가 출력되어있다면 해당 기능을 사용하면 안된다.
        if (bottomSheetVideoBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN)
        {
            return;
        }

        // 프레임 레이아웃에 들어있는 플레이어뷰를 찾아온다.
        StyledPlayerView selectedPlayer = layout.findViewById(R.id.recyclerview_video_player);

        // 지금 포커싱된 플레이어가 이전에 포커싱된 플레이어라면(같은 플레이어라면) 무시
        if (this.focusedPlayer == selectedPlayer)
        {
            return;
        }

        // 포커싱된 플레이어가 같은 플레이어가 아니라면 이미지도 찾아온다.
        ImageView thumbnail = layout.findViewById(R.id.recyclerview_video_image_thumbnail);

        // 이전에 재생되던 항목의 썸네일을 visible로 플레이어는 보이지 않게 invisible로 설정.
        if (focusedThumbnail != null)
        {
            focusedThumbnail.setVisibility(View.VISIBLE);
        }

        focusedThumbnail = thumbnail;
        // 플레이어를 보이게하고 썸네일을 보이지 않게한다.
        focusedThumbnail.setVisibility(View.INVISIBLE);

        releasePlayer(focusedPlayer);
        initializePlayer(selectedPlayer);

        setMediaSource(uuid, "1080");
    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (view.getId())
        {
            case R.id.recyclerview_reply_layout_nested_reply :

                System.out.println("클릭했다!");

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

    public void setRelatedVideoRecyclerView(List<VideoDTO> list)
    {
        for (VideoDTO video : list)
        {
            RecyclerViewVideoItem item = new RecyclerViewVideoItem();
            item.setVideoDTO(video);
            relatedVideoList.add(item);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.bottomsheetdialogfragment_video_image_player_play:

                // 플레이 or 일시정지 기능
                // icon 이미지도 바꿔야한다.

                break;
            case R.id.bottomsheetdialogfragment_video_image_player_close:

                bottomSheetVideoBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                releasePlayer(player);
                relatedVideoList.clear();

                break;
            case R.id.bottomsheetdialogfragment_video_image_thumb_up:

                // 좋아요 기능
                if (okHttpUtil == null)
                {
                    okHttpUtil = new OkHttpUtil();
                }

                // 평가 request
                String url = "http://192.168.0.4:9090/Baetube_backEnd/api/rate";
                RateDTO rate = new RateDTO(currentVideoItem.getVideoDTO().getContentsId(), 4, 1);
                ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_RATE);

                okHttpUtil.sendPostRequest(rate, url, returnableCallback);

                // 좋아요를 누른 동영상 재생목록에 추가를 request
                url = "http://192.168.0.4:9090/Baetube_backEnd/api/playlist/item/insert/like";
                VideoDTO video = new VideoDTO();
                video.setChannelId(4);
                video.setVideoId(currentVideoItem.getVideoDTO().getVideoId());

                returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

                okHttpUtil.sendPostRequest(video, url, returnableCallback);

                break;
            case R.id.bottomsheetdialogfragment_video_image_thumb_down:

                // 싫어요 기능
                if (okHttpUtil == null)
                {
                    okHttpUtil = new OkHttpUtil();
                }

                url = "http://192.168.0.4:9090/Baetube_backEnd/api/rate";
                rate = new RateDTO(currentVideoItem.getVideoDTO().getContentsId(), 4, 0);
                returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_RATE);

                okHttpUtil.sendPostRequest(rate, url, returnableCallback);

                // 좋아요를 누른 동영상 재생목록에 추가를 request
                url = "http://192.168.0.4:9090/Baetube_backEnd/api/playlist/item/delete/like";
                video = new VideoDTO();
                video.setChannelId(4);
                video.setVideoId(currentVideoItem.getVideoDTO().getVideoId());

                returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

                okHttpUtil.sendPostRequest(video, url, returnableCallback);

                break;
            case R.id.bottomsheetdialogfragment_video_image_add_library:

                /*
                AddPlaylistFragment addPlaylistFragment = new AddPlaylistFragment(this);
                addPlaylistFragment.show(fragmentManager, addPlaylistFragment.getTag());
                */

                requestSelectPlaylist();

                break;
            case R.id.bottomsheetdialogfragment_video_image_channel_profile:

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ChannelBaseFragment(onCallbackResponseListener), FragmentTagUtil.FRAGMENT_TAG_CHANNEL_BASE);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.bottomsheetdialogfragment_video_layout_reply:

                attachReplyView();
                bottomSheetSubBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                break;
            case R.id.bottomsheetdialogfragment_video_image_detail:

                attachDescriptionView(currentVideoItem);
                bottomSheetSubBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                break;
            case R.id.exo_play:
                // 플레이어 플레이 버튼
                playerPlayButton.setVisibility(View.GONE);
                playerPauseButton.setVisibility(View.VISIBLE);
                // 플레이어 플레이.
                exoPlayer.play();

                break;
            case R.id.exo_pause:
                // 플레이어 일시정지 버튼
                playerPauseButton.setVisibility(View.GONE);
                playerPlayButton.setVisibility(View.VISIBLE);
                // 플레이어 일시정지
                exoPlayer.pause();

                break;
            case R.id.exo_fullscreen_button:
                // 플레이어 전체화면 버튼

                // 일반모드(세로모드) 인 경우 가로 모드로 바꿔줘야 한다.
                if (playerScreenMode == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                {
                    playerScreenMode = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    playerFullscreenButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_fullscreen_exit_24));

                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                    if (getSupportActionBar() != null)
                    {
                        getSupportActionBar().hide();
                    }

                    ViewGroup.LayoutParams layoutParams = player.getLayoutParams();

                    layoutParams.width = layoutParams.MATCH_PARENT;
                    layoutParams.height = layoutParams.MATCH_PARENT;

                    layoutMinMenu.setVisibility(View.GONE);
                    layoutDescription.setVisibility(View.GONE);
                    relatedVideoRecyclerView.setVisibility(View.GONE);

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                }
                else // 랜드스케이프 모드(가로 모드)인 경우 세로 모드로 바꿔줘야 한다.
                {
                    playerScreenMode = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
                    playerFullscreenButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_fullscreen_24));

                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                    if (getSupportActionBar() != null)
                    {
                        getSupportActionBar().show();
                    }

                    ViewGroup.LayoutParams layoutParams = player.getLayoutParams();

                    layoutParams.width = 0;
                    layoutParams.height = 0;

                    layoutMinMenu.setVisibility(View.VISIBLE);
                    layoutDescription.setVisibility(View.VISIBLE);
                    relatedVideoRecyclerView.setVisibility(View.VISIBLE);

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

                }

                break;

            case R.id.exo_option:

                PlayerOptionFragment playerOptionFragment = new PlayerOptionFragment(this, onDialogInteractionListener, currentPlayerResolution);
                playerOptionFragment.show(fragmentManager, playerOptionFragment.getTag());

                break;

            case R.id.bottomsheetdialogfragment_video_text_button_subscribe :

                if(okHttpUtil == null)
                {
                    okHttpUtil = new OkHttpUtil();
                }

                Integer currentCount = Integer.parseInt(subscribeCount.getText().toString());

                if(buttonSubscribe.getCurrentTextColor() == ContextCompat.getColor(this, R.color.red))
                {
                    url = "http://192.168.0.4:9090/Baetube_backEnd/api/subscribe/subscribe";
                    SubscribersDTO subscribers = new SubscribersDTO(currentVideoItem.getChannelDTO().getChannelId(), 5);
                    returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SUBSCRIBE);
                    okHttpUtil.sendPostRequest(subscribers, url, returnableCallback);

                    subscribeCount.setText(String.valueOf(currentCount + 1));
                }
                else
                {
                    url = "http://192.168.0.4:9090/Baetube_backEnd/api/subscribe/unsubscribe";
                    SubscribersDTO subscribers = new SubscribersDTO(currentVideoItem.getChannelDTO().getChannelId(), 5);
                    returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_UNSUBSCRIBE);
                    okHttpUtil.sendPostRequest(subscribers, url, returnableCallback);

                    subscribeCount.setText(String.valueOf(currentCount - 1));
                }

                break;

            case R.id.activity_main_keyboard_edit_button_send :

                    String comment = keyboardEditInput.getText().toString().trim();

                    if(isReplyFocused)
                    {
                        ReplyDTO reply = new ReplyDTO(currentVideoItem.getVideoDTO().getContentsId(), currentVideoItem.getChannelDTO().getChannelId(), comment);
                        url = "http://192.168.0.4:9090/Baetube_backEnd/api/reply/insert";
                        returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_REPLY);
                        okHttpUtil.sendPostRequest(reply, url, returnableCallback);
                        keyboardEditInput.setText(null);
                    }
                    else
                    {
                        // 임시로 작성하는 채널id를 5로 한다.
                        NestedReplyDTO nestedReply = new NestedReplyDTO(currentReplyItem.getReplyDTO().getReplyId(), 5, comment, currentReplyItem.getReplyDTO().getAttachedId());
                        url = "http://192.168.0.4:9090/Baetube_backEnd/api/nestedreply/insert";
                        returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NESTED_REPLY);
                        okHttpUtil.sendPostRequest(nestedReply, url, returnableCallback);
                        keyboardEditInput.setText(null);
                    }

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                break;

            default:
                break;
        }
    }

    public void requestSelectPlaylist()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String playlistUrl = "http://192.168.0.4:9090/Baetube_backEnd/api/playlist/channel/4";

        ReturnableCallback playlistCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_PLAYLIST);

        okHttpUtil.sendGetRequest(playlistUrl, playlistCallback);
    }

    private void requestViewVideo(Integer userId, Integer videoId)
    {
        if (okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/video/view_video/" + userId + "/" + videoId;

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_VIEW_VIDEO);

        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    private void requestSelectSubscribe(Integer channelId, Integer subscriberId)
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/subscribe/select/" + channelId + "/" + subscriberId;

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_SUBSCRIBE);

        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    public void requestDeleteVideo()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/contents/delete";
        ContentsDTO contents = new ContentsDTO(managedVideoItem.getVideoDTO().getContentsId(), 0);


        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(contents, url, returnableCallback);

        setManagedVideoItem(null);
    }

    public void requestDeleteHistoryVideo()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/history/delete";
        // 임시로 유저id를 20으로 설정
        HistoryDTO history = new HistoryDTO(20, managedVideoItem.getVideoDTO().getVideoId());


        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(history, url, returnableCallback);

        setManagedVideoItem(null);
    }

    public void requestSignIn(UserDTO user)
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/user/regist";

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SIGN_IN);

        okHttpUtil.sendPostRequest(user, url, returnableCallback);
    }

    public void requestRelatedVideo(Integer videoId)
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/video/related/" + videoId;
        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_RELATED_VIDEO);
        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    private void setBottomSheetVideoInfo(VideoDTO video, ChannelDTO channel)
    {
        title.setText(video.getTitle());
        viewCount.setText(String.valueOf(video.getViews()));
        date.setText(DateToStringUtil.dateToString(video.getDate()));
        channelName.setText(channel.getName());
        subscribeCount.setText(String.valueOf(channel.getSubs()));
        replyCount.setText(String.valueOf(video.getReplyCount()));
        likeCount.setText(String.valueOf(video.getLike()));
        hateCount.setText(String.valueOf(video.getHate()));
    }

    private void resetView()
    {
        if (bottomSheetSub.getChildCount() > 0)
        {
            bottomSheetSub.removeAllViews();
            replyView = null;
            descriptionView = null;
        }
    }

    private void attachReplyView()
    {
        resetView();
        replyView = new ReplyView(this, currentVideoItem.getVideoDTO().getContentsId(), onCallbackResponseListener);
        bottomSheetSub.addView(replyView.onCreateView(getLayoutInflater(), bottomSheetSub));
    }

    private void attachDescriptionView(RecyclerViewVideoItem currentVideoItem)
    {
        resetView();
        descriptionView = new DescriptionView(this, currentVideoItem);
        bottomSheetSub.addView(descriptionView.onCreateView(getLayoutInflater(), bottomSheetSub));
    }

    @Override
    public void onSlide(View bottomSheet, float slideOffset)
    {
        if (slideOffset >= 0)
        {
            bottomNavigationView.setY((int) UserDisplay.getHeight() - bottomBarHeight / 2 - navigationHeight * (1 - slideOffset));
            bottomSheet.setY(LinearInterpolation.Lerp(0, bottomNavigationView.getY() - bottomSheetPeekHeight, 1 - slideOffset));
        }
    }

    @Override
    public void onAttachViewClick(View view)
    {
        switch (view.getId())
        {
            case R.id.bottomsheetdialogfragment_reply_image_close:

            case R.id.bottomsheetdialogfragment_description_image_close:

            case R.id.bottomsheetdialogfragment_nested_reply_image_close:

                bottomSheetSubBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                break;
            default:

                // 의도하지 않은 문제

                break;
        }
    }

    //화면에 보이기 시작할때!!
    @Override
    protected void onStart()
    {
        super.onStart();
        initializeExoPlayer();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        initializeExoPlayer();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        releaseExoPlayer();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        releaseExoPlayer();
    }

    private void initializePlayer(StyledPlayerView player)
    {
        // 포커싱된 플레이어를 입력받은 플레이어로 설정한다.
        focusedPlayer = player;
        // 플레이어에 exoPlayer를 할당한다.
        focusedPlayer.setPlayer(exoPlayer);
        // 플레이어의 visible을 visible로 설정
        focusedPlayer.setVisibility(View.VISIBLE);
        // 플레이어의
        focusedPlayer.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        // 엑소 플레이어의 state를 준비상태로 설정.
        exoPlayer.prepare();
    }

    private void releasePlayer(StyledPlayerView player)
    {
        if (player != null)
        {
            // 이전에 재생되고 있던 플레이어를 멈춘다.
            exoPlayer.stop();
            // 등록된 미디어 아이템을 제거한다.
            exoPlayer.clearMediaItems();
            // 비디오 표면을 정리한다.
            exoPlayer.clearVideoSurface();
            // visible을 invisible로 설정.
            player.setVisibility(View.INVISIBLE);
            // 플레이어를 null로 설정.
            player.setPlayer(null);
        }
    }

    private void initializeExoPlayer()
    {
        RenderersFactory renderersFactory = new DefaultRenderersFactory(this)
                .setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON);

        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter.Builder(this)
                .setInitialBitrateEstimate(1200)
                .build();

        AdaptiveTrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory();

        DefaultTrackSelector trackSelector = new DefaultTrackSelector(this, trackSelectionFactory);

        exoPlayer = new ExoPlayer.Builder(this)
                .setSeekBackIncrementMs(5000)
                .setSeekForwardIncrementMs(5000)
                .setRenderersFactory(renderersFactory)
                .setTrackSelector(trackSelector)
                .setBandwidthMeter(defaultBandwidthMeter)
                .build();

        exoPlayer.prepare();
    }

    private void setMediaSource(String url, String resolution)
    {
        String userAgent = Util.getUserAgent(this, "bae");

        playerUrl = url;
        currentPlayerResolution = resolution;

        // 주소(url)을 하드코딩이 아니라 xml로 따로 저장해야한다.
        MediaSource mediaSource = new HlsMediaSource.Factory(new DefaultHttpDataSource.Factory().setUserAgent(userAgent))
                .createMediaSource(MediaItem.fromUri("http://192.168.0.4:9090/Baetube_backEnd/hls/" + url + "/" + resolution + "/" + url + ".m3u8"));
        exoPlayer.setMediaSource(mediaSource);

        exoPlayer.setPlayWhenReady(true);
    }

    private void releaseExoPlayer()
    {
        if (exoPlayer != null)
        {
            exoPlayer.clearVideoSurface();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    public void setCurrentReplyData(RecyclerViewReplyItem replyItem)
    {
        currentReplyItem = replyItem;
    }

    public void requestAddPlaylist(List<PlaylistDTO> checkedList)
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/playlist/item/insert/multi";
        List<PlaylistItemDTO> playlistItemList = new ArrayList<>();

        for (PlaylistDTO item : checkedList)
        {
            PlaylistItemDTO playlistItem = new PlaylistItemDTO(item.getPlaylistId(), managedVideoItem.getVideoDTO().getVideoId());
            playlistItemList.add(playlistItem);
        }

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(playlistItemList, url, returnableCallback);

        setManagedVideoItem(null);
    }

    public void commitModifyVideoFragment()
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main_layout, new ModifyVideoFragment(onCallbackResponseListener));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setOnDialogInteractionListener()
    {
        onDialogInteractionListener = new OnDialogInteractionListener()
        {
            @Override
            public void onAddVoteResponse(String voteItem)
            {

            }

            @Override
            public void onDeletePlaylistItem(int position)
            {

            }

            @Override
            public void onSetVideoResolution(int position)
            {
                String resolutions[] = {"1080", "720", "480"};

                exoPlayer.pause();
                long currentPosition = exoPlayer.getCurrentPosition();
                setMediaSource(playerUrl, resolutions[position]);
                exoPlayer.seekTo(currentPosition);
            }


        };
    }

    private void setSimpleOnGestureListener()
    {
        simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onDoubleTap(MotionEvent e)
            {
                //player.hideController();

                // left
                if (e.getX() < UserDisplay.getWidth() / 2)
                {
                    playerSeekBack.callOnClick();
                } else // right
                {
                    playerSeekForward.callOnClick();
                }

                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e)
            {
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e)
            {
                if (playerControllerVisibility == View.GONE)
                {
                    player.showController();
                } else
                {
                    player.hideController();
                }

                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }

        };
    }

    private void setOnCallbackResponseListener()
    {
        onCallbackResponseListener = new OnCallbackResponseListener()
        {
            @Override
            public void onResponse(Response response) throws IOException
            {
                if (!response.isSuccessful())
                {
                    System.out.println("error : " + response.message());
                    //Toast.makeText(getApplicationContext(), "error : " + response.message(), Toast.LENGTH_LONG).show();
                } else
                {
                    System.out.println("요청이 성공적으로 완료되었습니다.");
                    //Toast.makeText(getApplicationContext(), "요청이 성공적으로 완료되었습니다.", Toast.LENGTH_LONG).show();
                }

                Fragment fragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_LOGIN);

                if (fragment != null && fragment.isVisible())
                {
                    fragmentManager.popBackStack();
                    return;
                }

                fragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_ADD_STORAGE);

                if (fragment != null && fragment.isVisible())
                {
                    fragmentManager.popBackStack();
                    return;
                }

            }

            @Override
            public void onLoginUserResponse(String object)
            {
                // 로그인을 성공하면 accessToken, refreshToken을 반환하기 때문에 따로 핸들링
                // Gson의 JsonParser를 사용하여 String을 파싱.
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(object);

                // accessToken, refreshToken을 가져온다.
                String accessToken = element.getAsJsonObject().get("accessToken").getAsString();
                String refreshToken = element.getAsJsonObject().get("refreshToken").getAsString();

                // PreferenceManager를 사용하여 accessToken과 refreshToken을 저장한다.
                PreferenceManager.clear(getApplicationContext());
                PreferenceManager.setString(getApplicationContext(), PreferenceManager.PREFERENCES_ACCESSKEY, accessToken);
                PreferenceManager.setString(getApplicationContext(), PreferenceManager.PREFERENCES_REFRESHKEY, refreshToken);
            }

            @Override
            public void onVisitChannelResponse(String object)
            {
                // 채널을 방문하면 방문한 채널 정보를 출력해줘야 한다.

                // 받아온 json 배열을 List로 변환하여 핸들링한다.
                ChannelDTO channel = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create().fromJson(object, ChannelDTO.class);


                // 구독 프래그먼트를 태그를 통해 가져온다
                Fragment channelBaseFragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_CHANNEL_BASE);


                if (channelBaseFragment != null && channelBaseFragment.isVisible())
                {
                    ViewPagerFragmentData viewPagerFragmentData = ((ChannelBaseFragment) channelBaseFragment).getCurrentFragmentData();

                    if (viewPagerFragmentData.getTag().equals(FragmentTagUtil.FRAGMENT_TAG_CHANNEL_HOME))
                    {
                        ((ChannelHomeFragment) viewPagerFragmentData.getFragment()).setChannelData(channel);
                    }
                }

            }

            @Override
            public void onSubscribersChannelResponse(String object)
            {
                System.out.println("Subscribers 요청수신.");
                // 구독한 채널의 채널 id, 채널명, 프로필 이미지를 반환한다.
                // 구독한 채널 목록을 출력하는 목적.

                //ChannelDTO channel = new GsonBuilder().create().fromJson(object, ChannelDTO.class);

                // 받아온 json 배열을 List로 변환하여 핸들링한다.
                ChannelDTO[] array = new GsonBuilder().create().fromJson(object, ChannelDTO[].class);
                List<ChannelDTO> channelList = Arrays.asList(array);

                System.out.println("구독 정보 개수 : " + channelList.size());

                // 구독 프래그먼트를 태그를 통해 가져온다
                Fragment fragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_SUBSCRIBE);

                // 가져온 프래그먼트가 null이 아니거나 visible이면 현재 프래그먼트가 구독 프래그먼트인 것.
                if (fragment != null && fragment.isVisible())
                {
                    ((SubscribeFragment) fragment).setRecyclerViewScribe(channelList);
                }

            }

            @Override
            public void onVisitCommunityResponse(String object)
            {
                // 해당 채널의 게시글 목록을 반환

                // 받아온 json 배열을 List로 변환하여 핸들링한다.
                CommunityDTO[] array = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create().fromJson(object, CommunityDTO[].class);
                //builder.registerTypeAdapter(Date.class, new GsonDateFormatAdapter());
                List<CommunityDTO> communityList = Arrays.asList(array);

                Fragment channelBaseFragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_CHANNEL_BASE);

                // 가져온 프래그먼트가 null이 아니거나 visible이면 현재 프래그먼트가 채널 홈 프래그먼트인 것.
                if (channelBaseFragment != null && channelBaseFragment.isVisible())
                {
                    ViewPagerFragmentData viewPagerFragmentData = ((ChannelBaseFragment) channelBaseFragment).getCurrentFragmentData();

                    if (viewPagerFragmentData.getTag().equals(FragmentTagUtil.FRAGMENT_TAG_CHANNEL_COMMUNITY))
                    {
                        ((ChannelCommunityFragment) viewPagerFragmentData.getFragment()).setRecyclerViewCommunity(communityList);
                    }

                }
            }

            @Override
            public void onSelectReplyResponse(String object)
            {
                // 받아온 json 배열을 List로 변환하여 핸들링한다.
                ReplyDTO[] array = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create().fromJson(object, ReplyDTO[].class);
                List<ReplyDTO> replyList = Arrays.asList(array);

                replyView.setRecyclerViewReply(replyList);
            }

            @Override
            public void onSelectNestedReplyResponse(String object)
            {
                // 받아온 json 배열을 List로 변환하여 핸들링한다.
                NestedReplyDTO[] array = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create().fromJson(object, NestedReplyDTO[].class);
                List<NestedReplyDTO> nestedReplyList = Arrays.asList(array);

                System.out.println("값을 전달합니다. - " + nestedReplyList.size());

                replyView.setRecyclerViewNestedReply(nestedReplyList);
            }

            @Override
            public void onSelectPlaylistResponse(String object)
            {
                // 플레이 리스트 목록을 가져온다.

                //ChannelDTO channel = new GsonBuilder().create().fromJson(object, ChannelDTO.class);

                // 받아온 json 배열을 List로 변환하여 핸들링한다.
                PlaylistDTO[] array = new GsonBuilder().create().fromJson(object, PlaylistDTO[].class);
                List<PlaylistDTO> playlistList = Arrays.asList(array);

                if(bottomSheetVideoBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                {
                    AddPlaylistFragment addPlaylistFragment = new AddPlaylistFragment(MainActivity.this, playlistList);
                    addPlaylistFragment.show(fragmentManager, addPlaylistFragment.getTag());
                    return;
                }

                Fragment homeFragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_HOME);

                if(homeFragment != null && homeFragment.isVisible())
                {
                    AddPlaylistFragment addPlaylistFragment = new AddPlaylistFragment(MainActivity.this, playlistList);
                    addPlaylistFragment.show(fragmentManager, addPlaylistFragment.getTag());
                    return;
                }

                Fragment subscribeFragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_SUBSCRIBE);

                if(subscribeFragment != null && subscribeFragment.isVisible())
                {
                    AddPlaylistFragment addPlaylistFragment = new AddPlaylistFragment(MainActivity.this, playlistList);
                    addPlaylistFragment.show(fragmentManager, addPlaylistFragment.getTag());
                    return;
                }

                // 보관함 프래그먼트를 태그를 통해 가져온다
                Fragment storageFragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_STORAGE);
                // 채널 프래그먼트를 태그를 통해 가져온다.
                //Fragment channelfragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_CHANNEL);

                // 가져온 프래그먼트가 null이 아니거나 visible이면 현재 프래그먼트가 구독 프래그먼트인 것.
                if (storageFragment != null && storageFragment.isVisible())
                {
                    if(managedVideoItem != null)
                    {
                        AddPlaylistFragment addPlaylistFragment = new AddPlaylistFragment(MainActivity.this, playlistList);
                        addPlaylistFragment.show(fragmentManager, addPlaylistFragment.getTag());
                    }
                    else
                    {
                        ((StorageFragment) storageFragment).setRecyclerViewStorage(playlistList);
                    }
                    return;
                }

                Fragment historyDetailFragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_HISTORY_DETAIL);

                if (historyDetailFragment != null && historyDetailFragment.isVisible())
                {
                    AddPlaylistFragment addPlaylistFragment = new AddPlaylistFragment(MainActivity.this, playlistList);
                    addPlaylistFragment.show(fragmentManager, addPlaylistFragment.getTag());
                    return;
                }

                Fragment channelBaseFragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_CHANNEL_BASE);

                // 가져온 프래그먼트가 null이 아니거나 visible이면 현재 프래그먼트가 채널 홈 프래그먼트인 것.
                if (channelBaseFragment != null && channelBaseFragment.isVisible())
                {
                    ViewPagerFragmentData viewPagerFragmentData = ((ChannelBaseFragment) channelBaseFragment).getCurrentFragmentData();

                    if (viewPagerFragmentData.getTag().equals(FragmentTagUtil.FRAGMENT_TAG_CHANNEL_PLAYLIST))
                    {
                        ((ChannelPlaylistFragment) viewPagerFragmentData.getFragment()).setRecyclerViewPlaylist(playlistList);
                    }
                    else
                    {
                        AddPlaylistFragment addPlaylistFragment = new AddPlaylistFragment(MainActivity.this, playlistList);
                        addPlaylistFragment.show(fragmentManager, addPlaylistFragment.getTag());
                    }
                    return;
                }

                Fragment channelManageVideoFragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_CHANNEL_MANAGE_VIDEO);
                if (channelManageVideoFragment != null && channelManageVideoFragment.isVisible())
                {
                    AddPlaylistFragment addPlaylistFragment = new AddPlaylistFragment(MainActivity.this, playlistList);
                    addPlaylistFragment.show(fragmentManager, addPlaylistFragment.getTag());
                    return;
                }

            }

            @Override
            public void onSelectSearchHistoryResponse(String object)
            {

            }

            @Override
            public void onSelectChannelVideoResponse(String object)
            {
                // 채널 동영상의 목록을 불러온다.

                // 받아온 json 배열을 List로 변환하여 핸들링한다.
                JsonParser parser = new JsonParser();
                JsonArray elementArray = parser.parse(object).getAsJsonArray();
                ArrayList<VideoDTO> videoList = new ArrayList<>();
                ArrayList<ChannelDTO> channelList = new ArrayList<>();

                for (int i = 0; i < elementArray.size(); i++)
                {
                    JsonElement element = elementArray.get(i);

                    Integer videoId = element.getAsJsonObject().get("videoId").getAsInt();
                    Long contentsId = element.getAsJsonObject().get("contentsId").getAsLong();
                    Integer channelId = element.getAsJsonObject().get("channelId").getAsInt();
                    String url = element.getAsJsonObject().get("url").getAsString();
                    String thumbnail = element.getAsJsonObject().get("thumbnail").getAsString();
                    String title = element.getAsJsonObject().get("title").getAsString();
                    String info = element.getAsJsonObject().get("info").getAsString();
                    String location = element.getAsJsonObject().get("location").getAsString();
                    Integer age = element.getAsJsonObject().get("age").getAsInt();
                    Integer views = element.getAsJsonObject().get("views").getAsInt();
                    Integer like = element.getAsJsonObject().get("like").getAsInt();
                    Integer hate = element.getAsJsonObject().get("hate").getAsInt();
                    Integer replyCount = element.getAsJsonObject().get("replyCount").getAsInt();
                    String date = element.getAsJsonObject().get("date").getAsString();
                    Integer categoryId = element.getAsJsonObject().get("categoryId").getAsInt();
                    String profile = element.getAsJsonObject().get("profile").getAsString();
                    String name = element.getAsJsonObject().get("name").getAsString();

                    VideoDTO video = new VideoDTO(videoId, contentsId, url, thumbnail, title, info, location, age, views, like, hate, replyCount,
                            TimestampUtil.StringToTimestamp(date), categoryId);
                    ChannelDTO channel = new ChannelDTO();
                    channel.setChannelId(channelId);
                    channel.setProfile(profile);
                    channel.setName(name);

                    videoList.add(video);
                    channelList.add(channel);
                }

                // 채널 홈 프래그먼트를 태그를 통해 가져온다
                Fragment channelBaseFragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_CHANNEL_BASE);

                // 가져온 프래그먼트가 null이 아니거나 visible이면 현재 프래그먼트가 채널 홈 프래그먼트인 것.
                if (channelBaseFragment != null && channelBaseFragment.isVisible())
                {
                    Fragment channelManageVideoFragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_CHANNEL_MANAGE_VIDEO);

                    if (channelManageVideoFragment != null && channelManageVideoFragment.isVisible())
                    {
                        ((ChannelManageVideoFragment) channelManageVideoFragment).setRecyclerViewVideo(videoList, channelList);

                        return;
                    }

                    ViewPagerFragmentData viewPagerFragmentData = ((ChannelBaseFragment) channelBaseFragment).getCurrentFragmentData();

                    if (viewPagerFragmentData.getTag().equals(FragmentTagUtil.FRAGMENT_TAG_CHANNEL_HOME))
                    {
                        ((ChannelHomeFragment) viewPagerFragmentData.getFragment()).setRecyclerViewVideo(videoList, channelList);
                    } else if (viewPagerFragmentData.getTag().equals(FragmentTagUtil.FRAGMENT_TAG_CHANNEL_VIDEO))
                    {
                        ((ChannelVideoFragment) viewPagerFragmentData.getFragment()).setRecyclerViewVideo(videoList, channelList);
                    }

                }

            }

            @Override
            public void onSelectHistoryVideoResponse(String object)
            {
                // 시청한 영상의 목록을 불러온다.
                System.out.println("HistoryVideo 요청수신.");
                // 받아온 json 배열을 List로 변환하여 핸들링한다.
                JsonParser parser = new JsonParser();
                JsonArray elementArray = parser.parse(object).getAsJsonArray();
                ArrayList<VideoDTO> videoList = new ArrayList<>();
                ArrayList<ChannelDTO> channelList = new ArrayList<>();

                for (int i = 0; i < elementArray.size(); i++)
                {
                    JsonElement element = elementArray.get(i);

                    Integer videoId = element.getAsJsonObject().get("videoId").getAsInt();
                    Long contentsId = element.getAsJsonObject().get("contentsId").getAsLong();
                    Integer channelId = element.getAsJsonObject().get("channelId").getAsInt();
                    String url = element.getAsJsonObject().get("url").getAsString();
                    String thumbnail = element.getAsJsonObject().get("thumbnail").getAsString();
                    String title = element.getAsJsonObject().get("title").getAsString();
                    String info = element.getAsJsonObject().get("info").getAsString();
                    String location = element.getAsJsonObject().get("location").getAsString();
                    Integer age = element.getAsJsonObject().get("age").getAsInt();
                    Integer views = element.getAsJsonObject().get("views").getAsInt();
                    Integer like = element.getAsJsonObject().get("like").getAsInt();
                    Integer hate = element.getAsJsonObject().get("hate").getAsInt();
                    Integer replyCount = element.getAsJsonObject().get("replyCount").getAsInt();
                    String date = element.getAsJsonObject().get("date").getAsString();
                    Integer categoryId = element.getAsJsonObject().get("categoryId").getAsInt();
                    String profile = element.getAsJsonObject().get("profile").getAsString();
                    String name = element.getAsJsonObject().get("name").getAsString();

                    VideoDTO video = new VideoDTO(videoId, contentsId, url, thumbnail, title, info, location, age, views, like, hate, replyCount,
                            TimestampUtil.StringToTimestamp(date), categoryId);
                    ChannelDTO channel = new ChannelDTO();
                    channel.setChannelId(channelId);
                    channel.setProfile(profile);
                    channel.setName(name);

                    videoList.add(video);
                    channelList.add(channel);
                }

                // 구독 프래그먼트를 태그를 통해 가져온다
                Fragment fragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_STORAGE);

                // 가져온 프래그먼트가 null이 아니거나 visible이면 현재 프래그먼트가 홈 프래그먼트인 것.
                if (fragment != null && fragment.isVisible())
                {
                    ((StorageFragment) fragment).setRecyclerViewVideoHistory(videoList, channelList);
                }
            }

            @Override
            public void onSelectMainVideoResponse(String object)
            {
                // 받아온 json 배열을 List로 변환하여 핸들링한다.
                JsonParser parser = new JsonParser();
                JsonArray elementArray = parser.parse(object).getAsJsonArray();
                ArrayList<VideoDTO> videoList = new ArrayList<>();
                ArrayList<ChannelDTO> channelList = new ArrayList<>();

                for (int i = 0; i < elementArray.size(); i++)
                {
                    JsonElement element = elementArray.get(i);

                    Integer videoId = element.getAsJsonObject().get("videoId").getAsInt();
                    Long contentsId = element.getAsJsonObject().get("contentsId").getAsLong();
                    Integer channelId = element.getAsJsonObject().get("channelId").getAsInt();
                    String url = element.getAsJsonObject().get("url").getAsString();
                    String thumbnail = element.getAsJsonObject().get("thumbnail").getAsString();
                    String title = element.getAsJsonObject().get("title").getAsString();
                    String info = element.getAsJsonObject().get("info").getAsString();
                    String location = element.getAsJsonObject().get("location").getAsString();
                    Integer age = element.getAsJsonObject().get("age").getAsInt();
                    Integer views = element.getAsJsonObject().get("views").getAsInt();
                    Integer like = element.getAsJsonObject().get("like").getAsInt();
                    Integer hate = element.getAsJsonObject().get("hate").getAsInt();
                    Integer replyCount = element.getAsJsonObject().get("replyCount").getAsInt();
                    String date = element.getAsJsonObject().get("date").getAsString();
                    Integer categoryId = element.getAsJsonObject().get("categoryId").getAsInt();
                    String profile = element.getAsJsonObject().get("profile").getAsString();
                    String name = element.getAsJsonObject().get("name").getAsString();
                    Integer subs = element.getAsJsonObject().get("subs").getAsInt();

                    VideoDTO video = new VideoDTO(videoId, contentsId, url, thumbnail, title, info, location, age, views, like, hate, replyCount,
                            TimestampUtil.StringToTimestamp(date), categoryId);
                    ChannelDTO channel = new ChannelDTO();
                    channel.setChannelId(channelId);
                    channel.setProfile(profile);
                    channel.setName(name);
                    channel.setSubs(subs);

                    videoList.add(video);
                    channelList.add(channel);
                }

                // 구독 프래그먼트를 태그를 통해 가져온다
                Fragment fragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_HOME);

                // 가져온 프래그먼트가 null이 아니거나 visible이면 현재 프래그먼트가 홈 프래그먼트인 것.
                if (fragment != null)
                {
                    ((HomeFragment) fragment).setRecyclerViewVideo(videoList, channelList);
                }

            }

            @Override
            public void onSelectPlaylistVideoResponse(String object)
            {
                // 받아온 json 배열을 List로 변환하여 핸들링한다.
                JsonParser parser = new JsonParser();
                JsonArray elementArray = parser.parse(object).getAsJsonArray();
                ArrayList<VideoDTO> videoList = new ArrayList<>();
                ArrayList<ChannelDTO> channelList = new ArrayList<>();

                for (int i = 0; i < elementArray.size(); i++)
                {
                    JsonElement element = elementArray.get(i);

                    Integer videoId = element.getAsJsonObject().get("videoId").getAsInt();
                    Long contentsId = element.getAsJsonObject().get("contentsId").getAsLong();
                    Integer channelId = element.getAsJsonObject().get("channelId").getAsInt();
                    String url = element.getAsJsonObject().get("url").getAsString();
                    String thumbnail = element.getAsJsonObject().get("thumbnail").getAsString();
                    String title = element.getAsJsonObject().get("title").getAsString();
                    String info = element.getAsJsonObject().get("info").getAsString();
                    String location = element.getAsJsonObject().get("location").getAsString();
                    Integer age = element.getAsJsonObject().get("age").getAsInt();
                    Integer views = element.getAsJsonObject().get("views").getAsInt();
                    Integer like = element.getAsJsonObject().get("like").getAsInt();
                    Integer hate = element.getAsJsonObject().get("hate").getAsInt();
                    Integer replyCount = element.getAsJsonObject().get("replyCount").getAsInt();
                    String date = element.getAsJsonObject().get("date").getAsString();
                    Integer categoryId = element.getAsJsonObject().get("categoryId").getAsInt();
                    String profile = element.getAsJsonObject().get("profile").getAsString();
                    String name = element.getAsJsonObject().get("name").getAsString();
                    Integer playlistId = element.getAsJsonObject().get("playlistId").getAsInt();

                    VideoDTO video = new VideoDTO(videoId, contentsId, url, thumbnail, title, info, location, age, views, like, hate, replyCount,
                            TimestampUtil.StringToTimestamp(date), categoryId);
                    video.setPlaylistId(playlistId);
                    ChannelDTO channel = new ChannelDTO();
                    channel.setChannelId(channelId);
                    channel.setProfile(profile);
                    channel.setName(name);

                    videoList.add(video);
                    channelList.add(channel);
                }

                // 구독 프래그먼트를 태그를 통해 가져온다
                Fragment fragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_PLAYLIST_DETAIL);

                // 가져온 프래그먼트가 null이 아니거나 visible이면 현재 프래그먼트가 홈 프래그먼트인 것.
                if (fragment != null && fragment.isVisible())
                {
                    ((PlaylistDetailFragment) fragment).setRecyclerViewVideo(videoList, channelList);
                }
            }

            @Override
            public void onSelectSubscribeVideoResponse(String object)
            {
                // 구독한 채널들의 동영상 정보 리스트를 반환한다.
                // 구독한 채널의 동영상들을 출력하는 목적

                //ChannelDTO channel = new GsonBuilder().create().fromJson(object, ChannelDTO.class);

                // 받아온 json 배열을 List로 변환하여 핸들링한다.
                JsonParser parser = new JsonParser();
                JsonArray elementArray = parser.parse(object).getAsJsonArray();
                ArrayList<VideoDTO> videoList = new ArrayList<>();
                ArrayList<ChannelDTO> channelList = new ArrayList<>();

                for (int i = 0; i < elementArray.size(); i++)
                {
                    JsonElement element = elementArray.get(i);

                    Integer videoId = element.getAsJsonObject().get("videoId").getAsInt();
                    Long contentsId = element.getAsJsonObject().get("contentsId").getAsLong();
                    Integer channelId = element.getAsJsonObject().get("channelId").getAsInt();
                    String url = element.getAsJsonObject().get("url").getAsString();
                    String thumbnail = element.getAsJsonObject().get("thumbnail").getAsString();
                    String title = element.getAsJsonObject().get("title").getAsString();
                    String info = element.getAsJsonObject().get("info").getAsString();
                    String location = element.getAsJsonObject().get("location").getAsString();
                    Integer age = element.getAsJsonObject().get("age").getAsInt();
                    Integer views = element.getAsJsonObject().get("views").getAsInt();
                    Integer like = element.getAsJsonObject().get("like").getAsInt();
                    Integer hate = element.getAsJsonObject().get("hate").getAsInt();
                    Integer replyCount = element.getAsJsonObject().get("replyCount").getAsInt();
                    String date = element.getAsJsonObject().get("date").getAsString();
                    Integer categoryId = element.getAsJsonObject().get("categoryId").getAsInt();
                    String profile = element.getAsJsonObject().get("profile").getAsString();
                    String name = element.getAsJsonObject().get("name").getAsString();

                    VideoDTO video = new VideoDTO(videoId, contentsId, url, thumbnail, title, info, location, age, views, like, hate, replyCount,
                            TimestampUtil.StringToTimestamp(date), categoryId);
                    ChannelDTO channel = new ChannelDTO();
                    channel.setChannelId(channelId);
                    channel.setProfile(profile);
                    channel.setName(name);

                    videoList.add(video);
                    channelList.add(channel);
                }

                // 구독 프래그먼트를 태그를 통해 가져온다
                Fragment fragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_SUBSCRIBE);

                // 가져온 프래그먼트가 null이 아니거나 visible이면 현재 프래그먼트가 구독 프래그먼트인 것.
                if (fragment != null && fragment.isVisible())
                {
                    ((SubscribeFragment) fragment).setRecyclerViewVideo(videoList, channelList);
                }
            }

            @Override
            public void onSelectViewVideoResponse(String object)
            {
                // Gson의 JsonParser를 사용하여 String을 파싱.
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(object);

                Integer videoId = element.getAsJsonObject().get("videoId").getAsInt();
                Long contentsId = element.getAsJsonObject().get("contentsId").getAsLong();
                Integer channelId = element.getAsJsonObject().get("channelId").getAsInt();
                String url = element.getAsJsonObject().get("url").getAsString();
                String thumbnail = element.getAsJsonObject().get("thumbnail").getAsString();
                String title = element.getAsJsonObject().get("title").getAsString();
                String info = element.getAsJsonObject().get("info").getAsString();
                String location = element.getAsJsonObject().get("location").getAsString();
                Integer age = element.getAsJsonObject().get("age").getAsInt();
                Integer views = element.getAsJsonObject().get("views").getAsInt();
                Integer like = element.getAsJsonObject().get("like").getAsInt();
                Integer hate = element.getAsJsonObject().get("hate").getAsInt();
                Integer replyCount = element.getAsJsonObject().get("replyCount").getAsInt();
                String date = element.getAsJsonObject().get("date").getAsString();
                Integer categoryId = element.getAsJsonObject().get("categoryId").getAsInt();
                String profile = element.getAsJsonObject().get("profile").getAsString();
                String name = element.getAsJsonObject().get("name").getAsString();
                Integer subs = element.getAsJsonObject().get("subs").getAsInt();

                VideoDTO video = new VideoDTO(videoId, contentsId, url, thumbnail, title, info, location, age, views, like, hate, replyCount,
                        TimestampUtil.StringToTimestamp(date), categoryId);
                ChannelDTO channel = new ChannelDTO();
                channel.setSubs(subs);
                channel.setChannelId(channelId);
                channel.setProfile(profile);
                channel.setName(name);

                runOnUiThread(new Runnable(){
                    @Override
                    public void run()
                    {
                        setBottomSheetVideoInfo(video, channel);
                    }
                });

            }

            @Override
            public void onSelectVoteOptionResponse(String object)
            {

            }

            @Override
            public void onSelectSubscribeResponse(String object)
            {
                if(object == null || object.isEmpty())
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            buttonSubscribe.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                            buttonSubscribe.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                        }
                    });

                    return;
                }

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                            buttonSubscribe.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                            buttonSubscribe.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_subscribe_border));
                    }
                });
            }

            @Override
            public void onInsertResponse(String object)
            {
                Fragment fragment = fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_ADD_STORAGE);

                if (fragment != null && fragment.isVisible())
                {
                    ((AddStorageFragment) fragment).requestInsertPlaylistItems(Integer.parseInt(object));
                }
            }

            @Override
            public void onRateResponse(String object)
            {
                // 바텀시트비디오가 expanded 인 경우.
                if (bottomSheetVideoBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                {
                    // 로그인을 성공하면 accessToken, refreshToken을 반환하기 때문에 따로 핸들링
                    // Gson의 JsonParser를 사용하여 String을 파싱.
                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(object);

                    // like, hate을 가져온다.
                    String like = element.getAsJsonObject().get("like").getAsString();
                    String hate = element.getAsJsonObject().get("hate").getAsString();

                    System.out.println("like : " + like);
                    System.out.println("hate : " + hate);

                    likeCount.setText(like);
                    hateCount.setText(hate);
                }
            }

            @Override
            public void onSubscribeResponse(String object)
            {
                System.out.println("구독했습니다.");
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        buttonSubscribe.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                        buttonSubscribe.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_subscribe_border));
                    }
                });
            }

            @Override
            public void onUnSubscribeResponse(String object)
            {
                System.out.println("구독을 취소 했습니다.");
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        buttonSubscribe.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        buttonSubscribe.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                    }
                });
            }

            @Override
            public void onReplyResponse(String object)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        replyCount.setText(String.valueOf(Integer.parseInt(replyCount.getText().toString()) + 1));
                    }
                });

                replyView.clearReplyList();
                replyView.requestReply();
            }

            @Override
            public void onNestedReplyResponse(String object)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        replyCount.setText(String.valueOf(Integer.parseInt(replyCount.getText().toString()) + 1));
                    }
                });

                replyView.clearNestedReplyList();
                replyView.requestNestedReply(currentReplyItem.getReplyDTO().getReplyId());
            }

            @Override
            public void onSignInResponse(String object)
            {
                fragmentManager.popBackStack();
            }

            @Override
            public void onSelectRelatedVideoResponse(String object)
            {
                // 받아온 json 배열을 List로 변환하여 핸들링한다.
                JsonParser parser = new JsonParser();
                JsonArray elementArray = parser.parse(object).getAsJsonArray();
                ArrayList<VideoDTO> videoList = new ArrayList<>();
                ArrayList<ChannelDTO> channelList = new ArrayList<>();

                for (int i = 0; i < elementArray.size(); i++)
                {
                    JsonElement element = elementArray.get(i);

                    Integer videoId = element.getAsJsonObject().get("videoId").getAsInt();
                    Long contentsId = element.getAsJsonObject().get("contentsId").getAsLong();
                    Integer channelId = element.getAsJsonObject().get("channelId").getAsInt();
                    String url = element.getAsJsonObject().get("url").getAsString();
                    String thumbnail = element.getAsJsonObject().get("thumbnail").getAsString();
                    String title = element.getAsJsonObject().get("title").getAsString();
                    String info = element.getAsJsonObject().get("info").getAsString();
                    String location = element.getAsJsonObject().get("location").getAsString();
                    Integer age = element.getAsJsonObject().get("age").getAsInt();
                    Integer views = element.getAsJsonObject().get("views").getAsInt();
                    Integer like = element.getAsJsonObject().get("like").getAsInt();
                    Integer hate = element.getAsJsonObject().get("hate").getAsInt();
                    Integer replyCount = element.getAsJsonObject().get("replyCount").getAsInt();
                    String date = element.getAsJsonObject().get("date").getAsString();
                    Integer categoryId = element.getAsJsonObject().get("categoryId").getAsInt();
                    String profile = element.getAsJsonObject().get("profile").getAsString();
                    String name = element.getAsJsonObject().get("name").getAsString();
                    Integer subs = element.getAsJsonObject().get("subs").getAsInt();

                    VideoDTO video = new VideoDTO(videoId, contentsId, url, thumbnail, title, info, location, age, views, like, hate, replyCount,
                            TimestampUtil.StringToTimestamp(date), categoryId);
                    ChannelDTO channel = new ChannelDTO();
                    channel.setChannelId(channelId);
                    channel.setProfile(profile);
                    channel.setName(name);
                    channel.setSubs(subs);

                    videoList.add(video);
                    channelList.add(channel);
                }

                relatedVideoRecyclerView(videoList, channelList);
            }
        };
    }

    public void relatedVideoRecyclerView(List<VideoDTO> videoList, ArrayList<ChannelDTO> channelList)
    {

        for(int i = 0; i < videoList.size(); i++)
        {
            RecyclerViewVideoItem item = new RecyclerViewVideoItem();
            item.setViewType(ViewType.VIDEO_LARGE);
            item.setVideoDTO(videoList.get(i));
            item.setChannelDTO(channelList.get(i));

            relatedVideoList.add(item);
        }

        runOnUiThread(new Runnable(){
            @Override
            public void run()
            {
                relatedVideoAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onFocusChange(View view, boolean b)
    {
        if(!b)
        {
            return;
        }

        switch (view.getId())
        {
            case R.id.bottomsheetdialogfragment_nested_reply_edit_reply :

                isReplyFocused = false;

                break;
            case R.id.bottomsheetdialogfragment_video_edit_reply :



            case R.id.bottomsheetdialogfragment_reply_edit_reply :

                isReplyFocused = true;

                break;
        }
    }

    public void setBottomNavigationViewVisible(int visibility)
    {
        bottomNavigationView.setVisibility(visibility);
    }
}