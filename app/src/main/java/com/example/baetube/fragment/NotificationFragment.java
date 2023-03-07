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
import com.example.baetube.ViewType;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewNotificationAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewNotificationItem;

import java.sql.Timestamp;
import java.util.ArrayList;

public class NotificationFragment extends Fragment
{
    private View view;

    private RecyclerView recyclerView;
    private RecyclerViewNotificationAdapter recyclerViewVideoAdapter;
    private ArrayList<RecyclerViewNotificationItem> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_notification, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        //toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        test();

        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */

        recyclerView = view.findViewById(R.id.fragment_notification_recyclerview);
        recyclerViewVideoAdapter = new RecyclerViewNotificationAdapter(list);
        recyclerView.setAdapter(recyclerViewVideoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_sub, menu);
    }

    public void test()
    {
        String channel_names[] = {"홍길동", "이순신", "장영실", "김유신", "허준"};
        String titles[] = {"쉽게 배우는 자바", "쉽게 배우는 학익진", "쉽게 배우는 거중기",
                "쉽게 배우는 전투법", "쉽게 배우는 침술"};

        RecyclerViewNotificationItem dividerItem = new RecyclerViewNotificationItem();

        dividerItem.setDivideString(getContext().getString(R.string.notification_date_today));
        dividerItem.setViewType(ViewType.NOTIFICATION_DIVIDER);
        list.add(dividerItem);

        for(int i = 0; i < 5; i++)
        {
            RecyclerViewNotificationItem item = new RecyclerViewNotificationItem();

            ChannelDTO channelDTO = new ChannelDTO();
            VideoDTO videoDTO = new VideoDTO();

            item.setChannelDTO(channelDTO);
            item.setVideoDTO(videoDTO);
            item.setViewType(ViewType.NOTIFICATION_VIDEO);

            channelDTO.setName(channel_names[i]);
            videoDTO.setDate(new Timestamp(System.currentTimeMillis()));
            videoDTO.setTitle(titles[i]);

            list.add(item);
        }

        dividerItem = new RecyclerViewNotificationItem();

        dividerItem.setDivideString(getContext().getString(R.string.notification_date_week));
        dividerItem.setViewType(ViewType.NOTIFICATION_DIVIDER);
        list.add(dividerItem);

        for(int i = 0; i < 5; i++)
        {
            RecyclerViewNotificationItem item = new RecyclerViewNotificationItem();

            ChannelDTO channelDTO = new ChannelDTO();
            VideoDTO videoDTO = new VideoDTO();

            item.setChannelDTO(channelDTO);
            item.setVideoDTO(videoDTO);
            item.setViewType(ViewType.NOTIFICATION_VIDEO);

            channelDTO.setName(channel_names[i]);
            videoDTO.setDate(new Timestamp(System.currentTimeMillis()));
            videoDTO.setTitle(titles[i]);

            list.add(item);
        }

        dividerItem = new RecyclerViewNotificationItem();

        dividerItem.setDivideString(getContext().getString(R.string.notification_date_other));
        dividerItem.setViewType(ViewType.NOTIFICATION_DIVIDER);
        list.add(dividerItem);

        for(int i = 0; i < 5; i++)
        {
            RecyclerViewNotificationItem item = new RecyclerViewNotificationItem();

            ChannelDTO channelDTO = new ChannelDTO();
            VideoDTO videoDTO = new VideoDTO();

            item.setChannelDTO(channelDTO);
            item.setVideoDTO(videoDTO);
            item.setViewType(ViewType.NOTIFICATION_VIDEO);

            channelDTO.setName(channel_names[i]);
            videoDTO.setDate(new Timestamp(System.currentTimeMillis()));
            videoDTO.setTitle(titles[i]);

            list.add(item);
        }

    }

}