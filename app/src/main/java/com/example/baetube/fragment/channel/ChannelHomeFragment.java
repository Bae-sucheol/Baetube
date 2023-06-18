package com.example.baetube.fragment.channel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.FragmentTagUtil;
import com.example.baetube.OkHttpUtil;
import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.OnFragmentInteractionListener;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.ViewType;
import com.example.baetube.activity.MainActivity;
import com.example.baetube.bottomsheetdialog.VideoOptionFragment;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.modify.ModifyChannelInformationFragment;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.util.ArrayList;
import java.util.List;

public class ChannelHomeFragment extends Fragment implements OnRecyclerViewClickListener, View.OnClickListener
{
    private View view;

    private RecyclerView recyclerView;
    private RecyclerViewVideoAdapter recyclerViewVideoAdapter;
    private ArrayList<RecyclerViewVideoItem> list;

    private OnFragmentInteractionListener onFragmentInteractionListener;

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

    private OnCallbackResponseListener onCallbackResponseListener;

    private OkHttpUtil okHttpUtil;

    public ChannelHomeFragment(OnCallbackResponseListener onCallbackResponseListener)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_channel_home, container, false);

        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */
        list = new ArrayList<>();
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

        onFragmentInteractionListener = (OnFragmentInteractionListener)getContext();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (view.getId())
        {
            case R.id.recyclerview_video_image_thumbnail :

                RecyclerViewVideoItem item = list.get(position);
                onFragmentInteractionListener.onVideoItemClick(item);

                break;
            case R.id.recyclerview_video_image_option :

                VideoOptionFragment videoOptionFragment = new VideoOptionFragment(getContext());
                videoOptionFragment.show(getParentFragmentManager(), videoOptionFragment.getTag());
                ((MainActivity)getContext()).setManagedVideoItem(list.get(position));

                break;
            case R.id.recyclerview_video_layout_information :

                item = list.get(position);
                onFragmentInteractionListener.onVideoItemClick(item);

                break;

            default :
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }

    @Override
    public void onCastVoteOption(VoteDTO voteData, boolean isCancel)
    {

    }

    /*
    @Override
    public void onViewCreated( View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        okHttpUtil = new OkHttpUtil();

        // 채널 Id를 기입해야 한다. 지금은 테스트용으로 임의의 값을 넣는다.
        String channelUrl = "http://192.168.0.4:9090/Baetube_backEnd/api/channel/visit/4";

        ReturnableCallback channelCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_VISIT_CHANNEL);

        okHttpUtil.sendGetRequest(channelUrl, channelCallback);

        // 채널 동영상 정보를 가져온다.

        String videoUrl = "http://192.168.0.4:9090/Baetube_backEnd/api/video/channel_video/4";

        ReturnableCallback videoCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_CHANNEL_VIDEO);

        okHttpUtil.sendGetRequest(videoUrl, videoCallback);

    }

     */

    public void setChannelData(ChannelDTO channel)
    {

        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run()
            {
                ChannelBaseFragment channelBaseFragment = (ChannelBaseFragment)getParentFragment();

                channelBaseFragment.setChannel(channel);

                channelName.setText(channel.getName());
                subscribeCount.setText(channel.getSubs().toString());
                videoCount.setText(channel.getVideoCount().toString());
                channelDescription.setText(channel.getDescription());
                // 기타 이미지 설정은 추후에.

                if(channel.getChannelId() != channelBaseFragment.getMyChannel().getChannelId())
                {
                    layoutManage.setVisibility(View.GONE);
                }
            }
        });

    }

    public void setRecyclerViewVideo(List<VideoDTO> videoList, List<ChannelDTO> channelList)
    {

        for(int i = 0; i < videoList.size(); i++)
        {
            RecyclerViewVideoItem item = new RecyclerViewVideoItem();
            item.setViewType(ViewType.VIDEO_MEDIUM);
            item.setVideoDTO(videoList.get(i));
            item.setChannelDTO(channelList.get(i));

            list.add(item);
        }

        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run()
            {
                recyclerViewVideoAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fragment_channel_home_image_expand :

                FragmentManager fragmentManager = getParentFragment().getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, ChannelInfomationFragment.newInstance(true, onCallbackResponseListener));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.fragment_channel_home_text_subscribe :

                // 구독 기능

                break;
            case R.id.fragment_channel_home_image_analysis :

                
                break;
            case R.id.fragment_channel_home_image_modify_channel :

                fragmentManager = getParentFragment().getParentFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ModifyChannelInformationFragment(onCallbackResponseListener),
                        FragmentTagUtil.FRAGMENT_TAG_MODIFY_CHANNEL_INFORMATION);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.fragment_channel_home_text_button_manage_video :

                ChannelBaseFragment channelBaseFragment = (ChannelBaseFragment)getParentFragment();
                Integer channelId = channelBaseFragment.getChannel().getChannelId();

                fragmentManager = getParentFragment().getParentFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.activity_main_layout, new ChannelManageVideoFragment(onCallbackResponseListener, channelId), FragmentTagUtil.FRAGMENT_TAG_CHANNEL_MANAGE_VIDEO);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            default :

                break;
        }
    }

}