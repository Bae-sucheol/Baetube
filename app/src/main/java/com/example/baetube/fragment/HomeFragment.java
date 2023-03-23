package com.example.baetube.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.Callback.ReturnableCallback;
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
import com.example.baetube.fragment.channel.ChannelBaseFragment;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener, OnRecyclerViewClickListener, OnFragmentInteractionListener
{
    private View view;

    private DrawerLayout drawerLayout;

    private LinearLayout layoutProfile;
    private LinearLayout layoutAccountManage;
    private LinearLayout layoutMyChannel;
    private LinearLayout layoutLogout;
    private LinearLayout layoutBack;

    private RecyclerView recyclerView;
    private RecyclerViewVideoAdapter recyclerViewVideoAdapter;
    private ArrayList<RecyclerViewVideoItem> list;

    private OnCallbackResponseListener onCallbackResponseListener;
    private OnFragmentInteractionListener onFragmentInteractionListener;
    private OkHttpUtil okHttpUtil;

    private LinearLayoutManager linearLayoutManager;

    private boolean isCalled;
    private boolean isFirst;

    public HomeFragment(OnCallbackResponseListener onCallbackResponseListener)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        // Inflate할 뷰 설정
        view = inflater.inflate(R.layout.fragment_home, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);

        // 드로워 레이아웃 요소 찾기
        drawerLayout = view.findViewById(R.id.fragment_home_drawer);

        // 드로워 메뉴 레이아웃 요소 찾기
        layoutProfile = view.findViewById(R.id.fragment_home_drawer_image_profile_layout);
        layoutAccountManage = view.findViewById(R.id.fragment_home_drawer_account_manage_layout);
        layoutMyChannel = view.findViewById(R.id.fragment_home_drawer_my_channel_layout);
        layoutLogout = view.findViewById(R.id.fragment_home_drawer_logout_layout);
        layoutBack = view.findViewById(R.id.fragment_home_drawer_back_layout);

        // 클릭 리스너 등록
        layoutProfile.setOnClickListener(this);
        layoutAccountManage.setOnClickListener(this);
        layoutMyChannel.setOnClickListener(this);
        layoutLogout.setOnClickListener(this);
        layoutBack.setOnClickListener(this);

        onFragmentInteractionListener = (OnFragmentInteractionListener) getContext();


        // 리스트 초기화.
        list = new ArrayList<>();

        // 리사이클러뷰 요소 찾기
        recyclerView = view.findViewById(R.id.fragment_home_recyclerview);

        // 리사이클러뷰 어댑터 객체 생성
        recyclerViewVideoAdapter = new RecyclerViewVideoAdapter(list);

        // 리사이클러뷰에 어댑터 설정
        recyclerView.setAdapter(recyclerViewVideoAdapter);

        // 리사이클러뷰 어댑터에 클릭리스너 등록
        recyclerViewVideoAdapter.setOnRecyclerViewClickListener(this);

        isCalled = false;

        // 리사이클러뷰에 레이아웃 매니저 설정
        linearLayoutManager = new LinearLayoutManager(getContext())
        {
            @Override
            public void onLayoutCompleted(RecyclerView.State state)
            {
                super.onLayoutCompleted(state);

                if(okHttpUtil == null)
                {
                    okHttpUtil = new OkHttpUtil();
                }

                if(!isCalled)
                {
                    // 지금은 테스트용으로 임의의 값을 넣는다.
                    String url = "http://192.168.0.4:9090/Baetube_backEnd/api/video/main_video/20";

                    ReturnableCallback mainVideoCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_MAIN_VIDEO);

                    okHttpUtil.sendGetRequest(url, mainVideoCallback);

                    isCalled = true;
                }

                if(!list.isEmpty() && recyclerView.getChildCount() > 0 && !isFirst)
                {
                    // 현재 화면에 전부 보이는 첫 번째 뷰의 어댑터 위치를 반환한다.
                    int position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();

                    if(position != 0)
                    {
                        return;
                    }

                    RecyclerViewVideoItem item = list.get(position);
                    View view = linearLayoutManager.findViewByPosition(position);
                    FrameLayout layout = view.findViewById(R.id.recyclerview_video_layout_player);

                    onFragmentInteractionListener.onCompletelyVisible(layout, item.getVideoDTO().getUrl());

                    isFirst = true;
                }

            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
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

                    RecyclerViewVideoItem item = list.get(position);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_toolbar_notification:
                //
                break;

            case R.id.menu_toolbar_search:
                //
                break;

            case R.id.menu_toolbar_profile:

                if (!drawerLayout.isDrawerOpen(Gravity.RIGHT))
                {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.fragment_home_drawer_image_profile_layout :

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.activity_main_layout, new LoginFragment(onCallbackResponseListener), FragmentTagUtil.FRAGMENT_TAG_LOGIN);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.fragment_home_drawer_account_manage_layout :

                break;
            case R.id.fragment_home_drawer_my_channel_layout :

                break;
            case R.id.fragment_home_drawer_logout_layout :

                break;
            case R.id.fragment_home_drawer_back_layout :

                if (drawerLayout.isDrawerOpen(Gravity.RIGHT))
                {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }

                break;

            default :
                break;
        }
    }

    public void setRecyclerViewVideo(List<VideoDTO> videoList, ArrayList<ChannelDTO> channelList)
    {
        if(list == null)
        {
            return;
        }

        for(int i = 0; i < videoList.size(); i++)
        {
            RecyclerViewVideoItem item = new RecyclerViewVideoItem();
            item.setViewType(ViewType.VIDEO_LARGE);
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
    public void onItemClick(View view, int position)
    {

        switch (view.getId())
        {
            case R.id.recyclerview_video_layout_player :

                RecyclerViewVideoItem item = list.get(position);
                //this.onVideoItemClick(item.getVideoDTO().getUrl());
                onFragmentInteractionListener.onVideoItemClick(item);

            case R.id.recyclerview_video_image_thumbnail :

                item = list.get(position);
                //this.onVideoItemClick(item.getVideoDTO().getUrl());
                onFragmentInteractionListener.onVideoItemClick(item);
                System.out.println("여기서 클릭");

                break;
            case R.id.recyclerview_video_image_profile :

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ChannelBaseFragment(onCallbackResponseListener), FragmentTagUtil.FRAGMENT_TAG_CHANNEL_BASE);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.recyclerview_video_image_option :

                VideoOptionFragment videoOptionFragment = new VideoOptionFragment(getContext());
                videoOptionFragment.show(getParentFragmentManager(), videoOptionFragment.getTag());
                ((MainActivity)getContext()).setManagedVideoItem(list.get(position));

                break;
            case R.id.recyclerview_video_layout_information :

                //videoFragment = new VideoFragment();
                //videoFragment.show(getParentFragmentManager(), videoFragment.getTag());

                break;

            default :

                // 비디오 서페이스
                item = list.get(position);
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
    public void onVideoItemClick(RecyclerViewVideoItem videoItem)
    {

    }

    @Override
    public void onCompletelyVisible(FrameLayout layout, String uuid)
    {
        //onFragmentInteractionListener.onCompletelyVisible(layout, uuid);
    }

}