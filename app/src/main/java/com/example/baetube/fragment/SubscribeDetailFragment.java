package com.example.baetube.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.example.baetube.ViewType;
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
    private ArrayList<RecyclerViewSubscribeItem> selectedList = new ArrayList<>();

    private int slideDistance = 0;
    private int animation_duration = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_subscribe_detail, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        toolbar.setTitle(getString(R.string.fragment_subscribe_detail_toolbar_title));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        animation_duration = getContext().getResources().getInteger(R.integer.animation_duration);
        slideDistance = (int) getContext().getResources().getDimension(R.dimen.width_subscribe_slide);

        test();

        // Inflate the layout for this fragment
        return view;
    }

    public void test()
    {
        String channel_names[] = {"홍길동", "이순신", "장영실", "김유신", "허준", "김시민", "이성계","홍길동", "이순신", "장영실", "김유신", "허준", "김시민", "이성계"};

        for(int i = 0; i < 14; i++)
        {
            RecyclerViewSubscribeItem item = new RecyclerViewSubscribeItem();

            ChannelDTO channelDTO = new ChannelDTO();

            item.setChannelDTO(channelDTO);
            item.setViewType(ViewType.VIEWTYPE_SUBSCRIBE_HORIZONTAL);

            channelDTO.setName(channel_names[i]);

            subscribeList.add(item);
        }

    }

    @Override
    public void onItemClick(View view, int position)
    {



    }

    @Override
    public void onItemLongClick(View view, int position)
    {
        // 클릭한 아이템이 선택 리스트에 존재하는지 판별한다 (없으면 -1, 있으면 해당 인덱스 반환)
        int index = selectedList.indexOf(subscribeList.get(position));
        // 리스트에 없다면 애니메이션을 실행하고 해당 아이템을 선택 리스트에 추가
        if(index == -1)
        {
            TranslateAnimation translateAnimation = new TranslateAnimation(0, -slideDistance, 0, 0);

            translateAnimation.setDuration(animation_duration);
            translateAnimation.setFillAfter(true);

            view.startAnimation(translateAnimation);

            selectedList.add(subscribeList.get(position));
        }
        else // 리스트에 이미 존재한다면 애니메이션을 실행하고 해당 아이템을 선택 리스트에서 제거
        {
            TranslateAnimation translateAnimation = new TranslateAnimation(-slideDistance, 0, 0, 0);

            translateAnimation.setDuration(animation_duration);
            translateAnimation.setFillAfter(true);

            view.startAnimation(translateAnimation);

            selectedList.remove(subscribeList.get(position));
        }
    }

}