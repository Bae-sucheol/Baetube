package com.example.baetube.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.Callback.ReturnableCallback;
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
import com.example.baetube.fragment.channel.ChannelBaseFragment;
import com.example.baetube.recyclerview.adapter.RecyclerViewSubscribeAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewSubscribeItem;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.util.ArrayList;
import java.util.List;

public class SubscribeFragment extends Fragment implements OnRecyclerViewClickListener, View.OnClickListener, OnFragmentInteractionListener
{

    private View view;
    private TextView buttonDetail;

    private RecyclerView recyclerViewScribe;
    private RecyclerViewSubscribeAdapter recyclerViewSubscribeAdapter;
    private ArrayList<RecyclerViewSubscribeItem> recyclerViewSubscribeList;

    private RecyclerView recyclerViewVideo;
    private RecyclerViewVideoAdapter recyclerViewVideoAdapter;
    private ArrayList<RecyclerViewVideoItem> recyclerViewVideoList;

    private OkHttpUtil okHttpUtil;

    private OnCallbackResponseListener onCallbackResponseListener;
    private OnFragmentInteractionListener onFragmentInteractionListener;

    private LinearLayoutManager subscribeLinearLayoutManager;
    private LinearLayoutManager linearLayoutManager;

    private boolean isCalled;
    private boolean isFirst;

    public SubscribeFragment(OnCallbackResponseListener onCallbackResponseListener)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

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

        //test();
        //test2();

        buttonDetail = view.findViewById(R.id.fragment_subscribe_text_button_detail);

        buttonDetail.setOnClickListener(this);

        /*
         * 구독 리스트 출력
         */
        // 리스트 초기화.
        recyclerViewSubscribeList = new ArrayList<>();

        // 리사이클러뷰 요소 찾기
        recyclerViewScribe = view.findViewById(R.id.fragment_subscribe_list_recyclerview);

        // 리사이클러뷰 어댑터 객체 생성
        recyclerViewSubscribeAdapter = new RecyclerViewSubscribeAdapter(recyclerViewSubscribeList);

        // 리사이클러뷰에 어댑터 설정
        recyclerViewScribe.setAdapter(recyclerViewSubscribeAdapter);

        // 어댑터에 클릭 리스너 등록
        recyclerViewSubscribeAdapter.setOnRecyclerViewClickListener(this);

        // 레이아웃 매니저 설정.
        subscribeLinearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false)
        {
            @Override
            public void onLayoutCompleted(RecyclerView.State state)
            {
                return;
            }
        };

        // 리사이클러뷰에 레이아웃 매니저 설정 (가로로 출력)
        recyclerViewScribe.setLayoutManager(subscribeLinearLayoutManager);

        /*
         * 동영상 리스트 출력
         */
        // 리스트 초기화.
        recyclerViewVideoList = new ArrayList<>();

        // 리사이클러뷰 요소 찾기
        recyclerViewVideo = view.findViewById(R.id.fragment_subscribe_video_recyclerview);

        // 리사이클러뷰 어댑터 객체 생성
        recyclerViewVideoAdapter = new RecyclerViewVideoAdapter(recyclerViewVideoList);

        // 리사이클러뷰에 어댑터 설정
        recyclerViewVideo.setAdapter(recyclerViewVideoAdapter);

        // 어댑터에 클릭 리스너 등록
        recyclerViewVideoAdapter.setOnRecyclerViewClickListener(this);

        isCalled = false;

        // 리사이클러뷰에 레이아웃 매니저 설정
        linearLayoutManager = new LinearLayoutManager(getContext()) {
            // 레이아웃 매니저가 컴플리트 되는 시점.
            // 여기서 메소드를 호출하는 것이 좋을 것 같다.
            @Override
            public void onLayoutCompleted(RecyclerView.State state)
            {
                super.onLayoutCompleted(state);

                if (okHttpUtil == null)
                {
                    okHttpUtil = new OkHttpUtil();
                }

                if (!isCalled)
                {
                    // 본인의 채널 Id를 기입해야 한다. 지금은 테스트용으로 임의의 값을 넣는다.
                    String subscribersUrl = "http://192.168.0.4:9090/Baetube_backEnd/api/channel/subscribers/5";

                    ReturnableCallback subscribersCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SUBSCRIBERS_CHANNEL);

                    okHttpUtil.sendGetRequest(subscribersUrl, subscribersCallback);

                    String subscribersVideoUrl = "http://192.168.0.4:9090/Baetube_backEnd/api/video/subscribe_video/5";

                    ReturnableCallback subscribersVideoCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_SUBSCRIBE_VIDEO);

                    okHttpUtil.sendGetRequest(subscribersVideoUrl, subscribersVideoCallback);

                    isCalled = true;
                }

                if(!recyclerViewVideoList.isEmpty() && recyclerViewVideo.getChildCount() > 0 && !isFirst)
                {
                    // 현재 화면에 전부 보이는 첫 번째 뷰의 어댑터 위치를 반환한다.
                    int position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    System.out.println("리사이클러뷰 비디오 리스트 포지션 : " + position);

                    if(position != 0)
                    {
                        return;
                    }

                    RecyclerViewVideoItem item = recyclerViewVideoList.get(position);
                    View view = linearLayoutManager.findViewByPosition(position);
                    FrameLayout layout = view.findViewById(R.id.recyclerview_video_layout_player);

                    onFragmentInteractionListener.onCompletelyVisible(layout, item.getVideoDTO().getUrl());

                    isFirst = true;
                }



            }
        };
        recyclerViewVideo.setLayoutManager(linearLayoutManager);

        onFragmentInteractionListener = (OnFragmentInteractionListener) getContext();

        recyclerViewVideo.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);

                // 스크롤을 하지 않는 상태일 때.
                if(newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    // 현재 화면에 전부 보이는 첫 번째 뷰의 어댑터 위치를 반환한다.
                    int position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();

                    if(position == -1)
                    {
                        return;
                    }

                    RecyclerViewVideoItem item = recyclerViewVideoList.get(position);
                    View view = linearLayoutManager.findViewByPosition(position);
                    FrameLayout layout = view.findViewById(R.id.recyclerview_video_layout_player);

                    onFragmentInteractionListener.onCompletelyVisible(layout, item.getVideoDTO().getUrl());
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_main, menu);
    }

    public void setRecyclerViewScribe(List<ChannelDTO> channelList)
    {
        if(recyclerViewVideoList == null)
        {
            return;
        }

        for(ChannelDTO channel : channelList)
        {
            RecyclerViewSubscribeItem item = new RecyclerViewSubscribeItem();
            item.setViewType(ViewType.SUBSCRIBE_VERTICAL);
            item.setChannelDTO(channel);

            recyclerViewSubscribeList.add(item);
        }

        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run()
            {
                recyclerViewSubscribeAdapter.notifyDataSetChanged();
            }
        });

    }

    public void setRecyclerViewVideo(List<VideoDTO> videoList, ArrayList<ChannelDTO> channelList)
    {
        if(recyclerViewVideoList == null)
        {
            return;
        }

        for(int i = 0; i < videoList.size(); i++)
        {
            RecyclerViewVideoItem item = new RecyclerViewVideoItem();
            item.setViewType(ViewType.VIDEO_LARGE);
            item.setVideoDTO(videoList.get(i));
            item.setChannelDTO(channelList.get(i));

            recyclerViewVideoList.add(item);
        }

        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run()
            {
                System.out.println("리사이클러뷰 업데이트 요청");
                recyclerViewVideoAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (view.getId())
        {
            case R.id.recyclerview_video_layout_player :

                RecyclerViewVideoItem item = recyclerViewVideoList.get(position);
                //this.onVideoItemClick(item.getVideoDTO().getUrl());
                onFragmentInteractionListener.onVideoItemClick(item);

            case R.id.recyclerview_video_image_thumbnail :

                item = recyclerViewVideoList.get(position);
                //this.onVideoItemClick(item.getVideoDTO().getUrl());
                onFragmentInteractionListener.onVideoItemClick(item);

                break;
            case R.id.exo_artwork :

                item = recyclerViewVideoList.get(position);
                this.onVideoItemClick(item);

                break;
            case R.id.recyclerview_video_image_profile :

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ChannelBaseFragment(onCallbackResponseListener));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.recyclerview_video_image_option :

                VideoOptionFragment videoOptionFragment = new VideoOptionFragment(getContext());
                videoOptionFragment.show(getParentFragmentManager(), videoOptionFragment.getTag());
                ((MainActivity)getContext()).setManagedVideoItem(recyclerViewVideoList.get(position));

                break;
            case R.id.recyclerview_video_layout_information :

                //videoFragment = new VideoFragment(onCallbackResponseListener);
                //videoFragment.show(getParentFragmentManager(), videoFragment.getTag());

                break;

            case R.id.recyclerview_subscribe_layout :

                fragmentManager = getParentFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ChannelBaseFragment(onCallbackResponseListener));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            default :
                // 비디오 서페이스
                item = recyclerViewVideoList.get(position);
                onFragmentInteractionListener.onVideoItemClick(item);
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

    @Override
    public void onClick(View view)
    {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main_layout,
                new SubscribeDetailFragment((ArrayList<RecyclerViewSubscribeItem>)recyclerViewSubscribeList.clone(), onCallbackResponseListener));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onVideoItemClick(RecyclerViewVideoItem videoItem)
    {
        //onFragmentInteractionListener.onVideoItemClick(uuid);
    }

    @Override
    public void onCompletelyVisible(FrameLayout layout, String uuid)
    {

    }
}