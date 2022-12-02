package com.example.baetube.fragment.channel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.baetube.AnalysisPagerAdapter;
import com.example.baetube.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ChannelAnalysisFragment extends Fragment
{
    private View view;

    private ViewPager2 viewPager;
    private AnalysisPagerAdapter analysisPagerAdapter;
    private TabLayout tabLayout;
    private TabLayoutMediator tabLayoutMediator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_channel_analysis, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        //toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 뷰페이저 요소를 찾는다.
        viewPager = view.findViewById(R.id.fragment_channel_analysis_viewpager);
        // 뷰페이저의 인디케이터 역할인 탭 레이아웃 요소를 찾는다.
        tabLayout = view.findViewById(R.id.fragment_channel_analysis_tab_layout);

        // 커스텀 뷰페이저 어댑터 객체를 만든다.
        analysisPagerAdapter = new AnalysisPagerAdapter(this, 3);
        // 어댑터를 뷰페이저에 할당한다.
        viewPager.setAdapter(analysisPagerAdapter);
        // 뷰페이저의 방향을 지정.(수평)
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        // 탭 레이아웃 중계자 객체 생성
        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy()
        {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position)
            {

            }
        });


        tabLayoutMediator.attach();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_none, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home :
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}