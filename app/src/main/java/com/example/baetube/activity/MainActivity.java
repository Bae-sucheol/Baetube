package com.example.baetube.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.UserDisplay;
import com.example.baetube.ViewType;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.ReplyDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.fragment.HomeFragment;
import com.example.baetube.R;
import com.example.baetube.fragment.StorageFragment;
import com.example.baetube.fragment.SubscribeFragment;
import com.example.baetube.recyclerview.adapter.RecyclerViewReplyAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewReplyItem;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnRecyclerViewClickListener
{
    // 바텀 네비게이션
    private BottomNavigationView bottomNavigationView;
    // 프래그먼트 매니저
    private FragmentManager fragmentManager;
    // 연관 비디오 리사이클러 뷰
    private RecyclerView videoRecyclerView;
    // 연관 비디오 아이템 리스트
    private ArrayList<RecyclerViewVideoItem> videoList = new ArrayList<>();
    // 연관 비디오 리사이클러 뷰 어댑터
    private RecyclerViewVideoAdapter videoAdapter;
    // 댓글 리사이클러 뷰
    private RecyclerView replyRecyclerView;
    // 댓글 아이템 리스트
    private ArrayList<RecyclerViewReplyItem> replyList = new ArrayList<>();
    // 댓글 리사이클러 뷰 어댑터
    private RecyclerViewReplyAdapter replyAdapter;


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

        test();
        test2();
        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */
        videoRecyclerView = findViewById(R.id.bottomsheetdialogfragment_video_recyclerview);
        videoAdapter = new RecyclerViewVideoAdapter(videoList);
        videoRecyclerView.setAdapter(videoAdapter);
        videoAdapter.setOnRecyclerViewClickListener(this);
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        replyRecyclerView = findViewById(R.id.bottomsheetdialogfragment_reply_recyclerview);
        replyAdapter = new RecyclerViewReplyAdapter(replyList);
        replyRecyclerView.setAdapter(replyAdapter);
        replyRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // 프래그먼트 매니저를 지정
        fragmentManager = getSupportFragmentManager();

        // 프래그먼트 매니저에 HomeFragment를 추가하고 커밋한다. ( 첫 화면 지정 )
        fragmentManager.beginTransaction().add(R.id.activity_main_frame, new HomeFragment()).commit();
        //fragmentManager.beginTransaction().add(R.id.activity_main_frame, new LoginFragment()).commit();
        //fragmentManager.beginTransaction().add(R.id.activity_main_frame, new SignInFragment()).commit();
        //fragmentManager.beginTransaction().add(R.id.activity_main_frame, new SubscribeDetailFragment()).commit();
        //fragmentManager.beginTransaction().add(R.id.activity_main_frame, new PlaylistDetailFragment()).commit();
        //fragmentManager.beginTransaction().add(R.id.activity_main_frame, new PlaylistModifyFragment()).commit();
        //fragmentManager.beginTransaction().add(R.id.activity_main_frame, new ChannelBaseFragment()).commit();
        //fragmentManager.beginTransaction().add(R.id.activity_main_frame, new ChannelManageVideoFragment()).commit();
        //fragmentManager.beginTransaction().add(R.id.activity_main_frame, new ChannelAnalysisFragment()).commit();
        //fragmentManager.beginTransaction().add(R.id.activity_main_frame, new NotificationFragment()).commit();
        //fragmentManager.beginTransaction().add(R.id.activity_main_frame, new UploadVideoListFragment()).commit();

        // 바텀 네비게이션 요소를 findViewById를 사용하여 찾는다.
        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation);

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
                        fragmentManager.beginTransaction().replace(R.id.activity_main_frame, new HomeFragment()).commit();
                        break;
                    // 업로드 아이콘 클릭 시.
                    case R.id.menu_bottom_navigation_upload :
                        // 업로드 액티비티로 전환해야 하므로 추후에 추가.
                        break;
                    // 구독 아이콘 클릭 시 해당 프래그먼트로 리플레이스
                    case R.id.menu_bottom_navigation_subscribe :
                        fragmentManager.beginTransaction().replace(R.id.activity_main_frame, new SubscribeFragment()).commit();
                        break;
                    // 보관함 아이콘 클릭 시 해당 프래그머늩로 리플레이스
                    case R.id.menu_bottom_navigation_storage :
                        fragmentManager.beginTransaction().replace(R.id.activity_main_frame, new StorageFragment()).commit();
                        break;
                }

                return true;
            }
        });

        BottomSheetBehavior videoBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.activity_main_video_frame));
        videoBottomSheetBehavior.setPeekHeight(500);
        videoBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        BottomSheetBehavior replyBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.activity_main_reply_frame));
        replyBottomSheetBehavior.setPeekHeight(400);
        replyBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


    }

    @Override
    public void onItemClick(View view, int position)
    {

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
            item.setViewType(ViewType.VIEWTYPE_VIDEO_LARGE);

            channelDTO.setName(channel_names[i]);
            videoDTO.setDate("1시간 전");
            videoDTO.setTitle(titles[i]);
            videoDTO.setViews(500);

            videoList.add(item);
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
}