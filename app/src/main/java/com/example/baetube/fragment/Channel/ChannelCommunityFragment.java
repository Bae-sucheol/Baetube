package com.example.baetube.fragment.Channel;

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
import com.example.baetube.dto.CommunityDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewCommunityAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewPlaylistAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewCommunityItem;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.util.ArrayList;

public class ChannelCommunityFragment extends Fragment implements OnRecyclerViewClickListener
{
    private View view;

    private RecyclerView recyclerView;
    private RecyclerViewCommunityAdapter recyclerViewCommunityAdapter;
    private ArrayList<RecyclerViewCommunityItem> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_channel_community, container, false);

        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */
        test();

        recyclerView = view.findViewById(R.id.fragment_channel_community_recyclerview);
        recyclerViewCommunityAdapter = new RecyclerViewCommunityAdapter(list);
        recyclerViewCommunityAdapter.setOnRecyclerViewClickListener(this);
        recyclerView.setAdapter(recyclerViewCommunityAdapter);
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
        String channel_names[] = {"홍길동", "이순신", "장영실", "김유신", "허준"};
        String comments[] = {"테스트1", "테스트2", "테스트3", "테스트4", "테스트5"};

        for(int i = 0; i < 5; i++)
        {
            RecyclerViewCommunityItem item = new RecyclerViewCommunityItem();

            CommunityDTO communityDTO = new CommunityDTO();
            ChannelDTO channelDTO = new ChannelDTO();

            communityDTO.setComment(comments[i]);
            communityDTO.setDate("1시간 전");
            communityDTO.setLikeCount(100);
            communityDTO.setHateCount(100);
            communityDTO.setReplyCount(100);
            channelDTO.setName(channel_names[i]);

            item.setCommunityDTO(communityDTO);
            item.setChannelDTO(channelDTO);

            list.add(item);
        }

    }
}