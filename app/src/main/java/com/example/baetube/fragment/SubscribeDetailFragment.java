package com.example.baetube.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.UserDisplay;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewSubscribeAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewSubscribeItem;

import java.util.ArrayList;

public class SubscribeDetailFragment extends Fragment implements OnRecyclerViewClickListener
{
    private View view;

    private RecyclerView recyclerViewScribe;
    private RecyclerViewSubscribeAdapter recyclerViewSubscribeAdapter;
    private ArrayList<RecyclerViewSubscribeItem> subscribeList = new ArrayList<>();

    private int slideDistance = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_subscribe_detail, container, false);

        /*
        * 1. 리사이클러뷰 요소 찾기
        * 2. 리사이클러뷰 어댑터 객체 생성
        * 3. 리사이클러뷰 어댑터 설정
        * 4. 리사이클러뷰 레이아웃 매니저 설정
         */
        recyclerViewScribe = view.findViewById(R.id.fragment_subscribe_detail_recyclerview);
        recyclerViewSubscribeAdapter = new RecyclerViewSubscribeAdapter(subscribeList);
        recyclerViewSubscribeAdapter.setOnRecyclerViewClickListener(this);
        recyclerViewScribe.setAdapter(recyclerViewSubscribeAdapter);
        recyclerViewScribe.setLayoutManager(new LinearLayoutManager(getContext()));

        test();

        // Inflate the layout for this fragment
        return view;
    }

    public void test()
    {
        String channel_names[] = {"홍길동", "이순신", "장영실", "김유신", "허준", "김시민", "이성계"};

        for(int i = 0; i < 7; i++)
        {
            RecyclerViewSubscribeItem item = new RecyclerViewSubscribeItem();

            ChannelDTO channelDTO = new ChannelDTO();

            item.setChannelDTO(channelDTO);
            item.setViewType(view.getResources().getInteger(R.integer.view_type_subscribe_horizontal));

            channelDTO.setName(channel_names[i]);

            subscribeList.add(item);
        }

    }

    @Override
    public void onItemClick(View view, int position)
    {
        if(slideDistance == 0)
        {
            slideDistance = (int)getContext().getResources().getDimension(R.dimen.subscribe_slide_distance);
        }

        TranslateAnimation translateAnimation = new TranslateAnimation(0, -slideDistance, 0 ,0);

        translateAnimation.setDuration(300);
        translateAnimation.setFillAfter(true);

        view.startAnimation(translateAnimation);
    }

}