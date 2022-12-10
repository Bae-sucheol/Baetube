package com.example.baetube.activity;

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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.UserDisplay;
import com.example.baetube.ViewType;
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

public class MainActivity extends AppCompatActivity
{
    // 바텀 네비게이션
    private BottomNavigationView bottomNavigationView;
    // 프래그먼트 매니저
    private FragmentManager fragmentManager;

    private ConstraintLayout layoutFront;
    private LinearLayout layout;
    private MotionLayout layoutParent;
    private VideoView videoView;
    private int video_height;
    private int swipe_height;
    private float start;

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

        //layoutFront = findViewById(R.id.activity_main_layout_front);

        //videoView = layoutFront.findViewById(R.id.videoView);
        //video_height = videoView.getHeight();
        //swipe_height = (int)UserDisplay.getHeight() - video_height;
        //layout = findViewById(R.id.activity_main_layout);
        //layout.setEnabled(false);


        // 프래그먼트 매니저를 지정
        fragmentManager = getSupportFragmentManager();

        // 프래그먼트 매니저에 HomeFragment를 추가하고 커밋한다. ( 첫 화면 지정 )
        fragmentManager.beginTransaction().add(R.id.activity_main_layout, new HomeFragment()).commit();
        //fragmentManager.beginTransaction().add(R.id.activity_main_layout, new ChannelBaseFragment()).commit();


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

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

}