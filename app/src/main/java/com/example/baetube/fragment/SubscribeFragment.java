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
import android.view.View;
import android.view.ViewGroup;

import com.example.baetube.R;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewSubscribeAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewSubscribeItem;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.util.ArrayList;

public class SubscribeFragment extends Fragment
{

    private View view;

    private RecyclerView recyclerViewScribe;
    private RecyclerViewSubscribeAdapter recyclerViewSubscribeAdapter;
    private ArrayList<RecyclerViewSubscribeItem> subscribeList = new ArrayList<>();

    private RecyclerView recyclerViewVideo;
    private RecyclerViewVideoAdapter recyclerViewVideoAdapter;
    private ArrayList<RecyclerViewVideoItem> videoList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        // Inflate할 뷰 설정
        view = inflater.inflate(R.layout.fragment_subscribe, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);

        test();
        test2();

        /*
         * 구독 리스트 출력
         */
        // 리사이클러뷰 요소 찾기
        recyclerViewScribe = view.findViewById(R.id.fragment_subscribe_list_recyclerview);

        // 리사이클러뷰 어댑터 객체 생성
        recyclerViewSubscribeAdapter = new RecyclerViewSubscribeAdapter(subscribeList);

        // 리사이클러뷰에 어댑터 설정
        recyclerViewScribe.setAdapter(recyclerViewSubscribeAdapter);

        // 리사이클러뷰에 레이아웃 매니저 설정 (가로로 출력)
        recyclerViewScribe.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        /*
         * 동영상 리스트 출력
         */
        // 리사이클러뷰 요소 찾기
        recyclerViewVideo = view.findViewById(R.id.fragment_subscribe_video_recyclerview);

        // 리사이클러뷰 어댑터 객체 생성
        recyclerViewVideoAdapter = new RecyclerViewVideoAdapter(videoList);

        // 리사이클러뷰에 어댑터 설정
        recyclerViewVideo.setAdapter(recyclerViewVideoAdapter);

        // 리사이클러뷰에 레이아웃 매니저 설정
        recyclerViewVideo.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar, menu);
    }

    public void test()
    {
        String channel_names[] = {"홍길동", "이순신", "장영실", "김유신", "허준", "김시민", "이성계"};

        for(int i = 0; i < 7; i++)
        {
            RecyclerViewSubscribeItem item = new RecyclerViewSubscribeItem();

            ChannelDTO channelDTO = new ChannelDTO();

            item.setChannelDTO(channelDTO);
            item.setViewType(view.getResources().getInteger(R.integer.view_type_subscribe_vertical));

            channelDTO.setName(channel_names[i]);

            subscribeList.add(item);
        }

    }

    public void test2()
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

            videoList.add(item);
        }

    }

}