package com.example.baetube.fragment.channel;

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
import com.example.baetube.bottomsheetdialog.VideoFragment;
import com.example.baetube.bottomsheetdialog.VideoOptionFragment;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.util.ArrayList;

public class ChannelVideoFragment extends Fragment implements OnRecyclerViewClickListener
{
    private View view;

    private RecyclerView recyclerView;
    private RecyclerViewVideoAdapter recyclerViewVideoAdapter;
    private ArrayList<RecyclerViewVideoItem> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_channel_video, container, false);

        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */

        test();

        recyclerView = view.findViewById(R.id.fragment_channel_video_recyclerview);
        recyclerViewVideoAdapter = new RecyclerViewVideoAdapter(list);
        recyclerViewVideoAdapter.setOnRecyclerViewClickListener(this);
        recyclerView.setAdapter(recyclerViewVideoAdapter);
        recyclerViewVideoAdapter.setOnRecyclerViewClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (view.getId())
        {
            case R.id.recyclerview_video_image_thumbnail :

                VideoFragment videoFragment = new VideoFragment();
                videoFragment.show(getParentFragmentManager(), videoFragment.getTag());

                break;
            case R.id.recyclerview_video_image_option :

                VideoOptionFragment videoOptionFragment = new VideoOptionFragment(getContext());
                videoOptionFragment.show(getParentFragmentManager(), videoOptionFragment.getTag());

                break;
            case R.id.recyclerview_video_layout_information :

                videoFragment = new VideoFragment();
                videoFragment.show(getParentFragmentManager(), videoFragment.getTag());

                break;

            default :
                break;
        }
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
            item.setViewType(ViewType.VIDEO_MEDIUM);

            channelDTO.setName(channel_names[i]);
            videoDTO.setDate("1시간 전");
            videoDTO.setTitle(titles[i]);
            videoDTO.setViews(500);

            list.add(item);
        }

    }
}