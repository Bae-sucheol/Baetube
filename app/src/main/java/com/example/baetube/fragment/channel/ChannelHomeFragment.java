package com.example.baetube.fragment.channel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.PublicState;
import com.example.baetube.R;
import com.example.baetube.ViewType;
import com.example.baetube.bottomsheetdialog.ChannelReportFragment;
import com.example.baetube.bottomsheetdialog.VideoFragment;
import com.example.baetube.bottomsheetdialog.VideoOptionFragment;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.fragment.modify.ModifyChannelInformationFragment;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.util.ArrayList;

public class ChannelHomeFragment extends Fragment implements OnRecyclerViewClickListener, View.OnClickListener
{
    private View view;

    private RecyclerView recyclerView;
    private RecyclerViewVideoAdapter recyclerViewVideoAdapter;
    private ArrayList<RecyclerViewVideoItem> list = new ArrayList<>();

    private ImageView profile;
    private ImageView expand;
    private ImageView channelAnalysis;
    private ImageView modifyChannel;
    private TextView channelName;
    private TextView buttonSubscribe;
    private TextView subscribeCount;
    private TextView videoCount;
    private TextView channelDescription;
    private TextView buttonManageVideo;
    private LinearLayout layoutManage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_channel_home, container, false);


        test();
        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */
        recyclerView = view.findViewById(R.id.fragment_channel_home_recyclerview);
        recyclerViewVideoAdapter = new RecyclerViewVideoAdapter(list);
        recyclerViewVideoAdapter.setOnRecyclerViewClickListener(this);
        recyclerView.setAdapter(recyclerViewVideoAdapter);
        recyclerViewVideoAdapter.setOnRecyclerViewClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        profile = view.findViewById(R.id.fragment_channel_home_image_profile);
        expand = view.findViewById(R.id.fragment_channel_home_image_expand);
        channelAnalysis = view.findViewById(R.id.fragment_channel_home_image_analysis);
        modifyChannel = view.findViewById(R.id.fragment_channel_home_image_modify_channel);
        channelName = view.findViewById(R.id.fragment_channel_home_text_channel_name);
        buttonSubscribe = view.findViewById(R.id.fragment_channel_home_text_subscribe);
        subscribeCount = view.findViewById(R.id.fragment_channel_home_text_subscribe_count);
        videoCount = view.findViewById(R.id.fragment_channel_home_text_video_count);
        channelDescription = view.findViewById(R.id.fragment_channel_home_text_channel_description);
        buttonManageVideo = view.findViewById(R.id.fragment_channel_home_text_button_manage_video);
        layoutManage = view.findViewById(R.id.fragment_channel_home_layout_manage);

        expand.setOnClickListener(this);
        channelAnalysis.setOnClickListener(this);
        modifyChannel.setOnClickListener(this);
        buttonSubscribe.setOnClickListener(this);
        buttonManageVideo.setOnClickListener(this);

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

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fragment_channel_home_image_expand :

                FragmentManager fragmentManager = getParentFragment().getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, ChannelInfomationFragment.newInstance(true));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.fragment_channel_home_text_subscribe :

                // 구독 기능

                break;
            case R.id.fragment_channel_home_image_analysis :

                fragmentManager = getParentFragment().getParentFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ChannelAnalysisFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.fragment_channel_home_image_modify_channel :

                fragmentManager = getParentFragment().getParentFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ModifyChannelInformationFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.fragment_channel_home_text_button_manage_video :

                fragmentManager = getParentFragment().getParentFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ChannelManageVideoFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            default :

                break;
        }
    }
}