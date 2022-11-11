package com.example.baetube.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.ViewType;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.PlaylistDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewPlaylistAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewPlaylistItem;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.util.ArrayList;

public class ChannelPlaylistFragment extends Fragment implements OnRecyclerViewClickListener
{

    private View view;

    private RecyclerView recyclerView;
    private RecyclerViewPlaylistAdapter recyclerViewPlaylistAdapter;
    private ArrayList<RecyclerViewPlaylistItem> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_channel_playlist, container, false);

        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */
        test();

        recyclerView = view.findViewById(R.id.fragment_channel_playlist_recyclerview);
        recyclerViewPlaylistAdapter = new RecyclerViewPlaylistAdapter(list);
        recyclerViewPlaylistAdapter.setOnRecyclerViewClickListener(this);
        recyclerView.setAdapter(recyclerViewPlaylistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return view;
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
        String titles[] = {"자바 강의", "c언어 강의", "c++ 강의", "파이썬 강의", "오버워치2 강의"};

        for(int i = 0; i < 5; i++)
        {
            RecyclerViewPlaylistItem item = new RecyclerViewPlaylistItem();

            PlaylistDTO playlistDTO = new PlaylistDTO();
            ChannelDTO channelDTO = new ChannelDTO();

            item.setChannelDTO(channelDTO);
            item.setPlaylistDTO(playlistDTO);

            channelDTO.setName("홍길동");
            playlistDTO.setName(titles[i]);
            playlistDTO.setVideoCount(5);

            list.add(item);
        }

    }
}