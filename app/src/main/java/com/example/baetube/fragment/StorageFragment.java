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
import com.example.baetube.dto.PlaylistDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewStorageAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewPlaylistItem;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.util.ArrayList;

public class StorageFragment extends Fragment
{
    private View view;

    private RecyclerView recyclerViewVideoHistory;
    private RecyclerViewVideoAdapter recyclerViewVideoHistoryAdapter;
    private ArrayList<RecyclerViewVideoItem> videoHistoryList = new ArrayList<>();

    private RecyclerView recyclerViewVideoStorage;
    private RecyclerViewStorageAdapter recyclerViewStorageAdapter;
    private ArrayList<RecyclerViewPlaylistItem> storageList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_storage, container, false);

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
         * 동영상 시청 기록 리스트 출력
         */
        // 리사이클러뷰 요소 찾기
        recyclerViewVideoHistory = view.findViewById(R.id.fragment_storage_history_recyclerview);

        // 리사이클러뷰 어댑터 객체 생성
        recyclerViewVideoHistoryAdapter = new RecyclerViewVideoAdapter(videoHistoryList);

        // 리사이클러뷰에 어댑터 설정
        recyclerViewVideoHistory.setAdapter(recyclerViewVideoHistoryAdapter);

        // 리사이클러뷰에 레이아웃 매니저 설정 (가로로 출력)
        recyclerViewVideoHistory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        /*
         * 보관함 리스트 출력
         */
        // 리사이클러뷰 요소 찾기
        recyclerViewVideoStorage = view.findViewById(R.id.fragment_storage_playlist_recyclerview);

        // 리사이클러뷰 어댑터 객체 생성
        recyclerViewStorageAdapter = new RecyclerViewStorageAdapter(storageList);

        // 리사이클러뷰에 어댑터 설정
        recyclerViewVideoStorage.setAdapter(recyclerViewStorageAdapter);

        // 리사이클러뷰에 레이아웃 매니저 설정 (가로로 출력)
        recyclerViewVideoStorage.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
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
            item.setViewType(ViewType.VIEWTYPE_VIDEO_SMALL);

            channelDTO.setName(channel_names[i]);
            videoDTO.setDate("1시간 전");
            videoDTO.setTitle(titles[i]);
            videoDTO.setViews(500);

            videoHistoryList.add(item);
        }

    }

    public void test2()
    {
        String names[] = {"음악", "게임", "영화", "기술", "생물"};
        int counts[] = {5, 10, 15, 20, 25};

        for(int i = 0; i < 5; i++)
        {
            RecyclerViewPlaylistItem item = new RecyclerViewPlaylistItem();

            PlaylistDTO playlistDTO = new PlaylistDTO();
            playlistDTO.setName(names[i]);
            playlistDTO.setVideo_count(counts[i]);

            item.setPlaylistDTO(playlistDTO);

            storageList.add(item);
        }

    }
}