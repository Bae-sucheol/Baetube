package com.example.baetube.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.baetube.UserDisplay;
import com.example.baetube.fragment.HomeFragment;
import com.example.baetube.R;
import com.example.baetube.fragment.LoginFragment;
import com.example.baetube.fragment.SignInFragment;
import com.example.baetube.fragment.StorageFragment;
import com.example.baetube.fragment.SubscribeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity
{
    // 바텀 네비게이션
    private BottomNavigationView bottomNavigationView;
    // 프래그먼트 매니저
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 디스플레이 사이즈를 구해서 UserDisplay 클래스에 저장한다.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);
        UserDisplay.setWidth(size.x);
        UserDisplay.setHeight(size.y);

        // 프래그먼트 매니저를 지정
        fragmentManager = getSupportFragmentManager();

        // 프래그먼트 매니저에 HomeFragment를 추가하고 커밋한다. ( 첫 화면 지정 )
        //fragmentManager.beginTransaction().add(R.id.activity_main_frame, new HomeFragment()).commit();
        //fragmentManager.beginTransaction().add(R.id.activity_main_frame, new LoginFragment()).commit();
        fragmentManager.beginTransaction().add(R.id.activity_main_frame, new SignInFragment()).commit();

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

    }

}