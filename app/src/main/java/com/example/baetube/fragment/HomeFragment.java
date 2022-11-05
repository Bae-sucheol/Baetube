package com.example.baetube.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.baetube.R;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private View view;

    private RecyclerView recyclerView;
    private RecyclerViewVideoAdapter recyclerViewVideoAdapter;
    private ArrayList<RecyclerViewVideoItem> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        // Inflate할 뷰 설정
        view = inflater.inflate(R.layout.fragment_home, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);

        test();

        // 리사이클러뷰 요소 찾기
        recyclerView = view.findViewById(R.id.fragment_home_recyclerview);

        // 리사이클러뷰 어댑터 객체 생성
        recyclerViewVideoAdapter = new RecyclerViewVideoAdapter(list);

        // 리사이클러뷰에 어댑터 설정
        recyclerView.setAdapter(recyclerViewVideoAdapter);

        // 리사이클러뷰에 레이아웃 매니저 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_toolbar_notification:
                //
                break;

            case R.id.menu_toolbar_search:
                //
                break;

            case R.id.menu_toolbar_profile:
                //
                break;
        }

        return super.onOptionsItemSelected(item);
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

            channelDTO.setName(channel_names[i]);
            videoDTO.setDate("1시간 전");
            videoDTO.setTitle(titles[i]);
            videoDTO.setViews(500);

            list.add(item);
        }

    }
}