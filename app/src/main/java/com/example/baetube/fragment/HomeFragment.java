package com.example.baetube.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.FragmentTagUtil;
import com.example.baetube.OkHttpUtil;
import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.OnDialogInteractionListener;
import com.example.baetube.OnFragmentInteractionListener;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.PreferenceManager;
import com.example.baetube.R;
import com.example.baetube.ViewType;
import com.example.baetube.activity.MainActivity;
import com.example.baetube.bottomsheetdialog.ChannelSelectOptionFragment;
import com.example.baetube.bottomsheetdialog.VideoOptionFragment;
import com.example.baetube.dto.CategoryDTO;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.channel.ChannelBaseFragment;
import com.example.baetube.fragment.modify.ModifyChannelInformationFragment;
import com.example.baetube.recyclerview.adapter.RecyclerViewCategoryAdapter;
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

    private TextView textName;
    private ImageView profile;

    private RecyclerView recyclerView;
    private RecyclerViewVideoAdapter recyclerViewVideoAdapter;
    private ArrayList<RecyclerViewVideoItem> list;
    private ArrayList<RecyclerViewVideoItem> filteredList;

    private RecyclerView recyclerViewCategory;
    private RecyclerViewCategoryAdapter recyclerViewCategoryAdapter;
    private ArrayList<CategoryDTO> listCategory;

    private OnCallbackResponseListener onCallbackResponseListener;
    private OnFragmentInteractionListener onFragmentInteractionListener;
    private OnDialogInteractionListener onDialogInteractionListener;
    private OkHttpUtil okHttpUtil;

    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManagerCategory;

    private Integer selectedPosition;

    private boolean isCalled;
    private boolean isFirst;

    private ChannelDTO channel;

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

        textName = view.findViewById(R.id.fragment_home_drawer_text_name);
        profile = view.findViewById(R.id.fragment_home_drawer_image_profile);

        // 클릭 리스너 등록
        layoutProfile.setOnClickListener(this);
        layoutAccountManage.setOnClickListener(this);
        layoutMyChannel.setOnClickListener(this);
        layoutLogout.setOnClickListener(this);
        layoutBack.setOnClickListener(this);

        onFragmentInteractionListener = (OnFragmentInteractionListener) getContext();

        selectedPosition = 0;

        // 리스트 초기화.
        list = new ArrayList<>();
        filteredList = new ArrayList<>();

        // 리사이클러뷰 요소 찾기
        recyclerView = view.findViewById(R.id.fragment_home_recyclerview);

        // 리사이클러뷰 어댑터 객체 생성
        recyclerViewVideoAdapter = new RecyclerViewVideoAdapter(list);

        // 리사이클러뷰에 어댑터 설정
        recyclerView.setAdapter(recyclerViewVideoAdapter);

        // 리사이클러뷰 어댑터에 클릭리스너 등록
        recyclerViewVideoAdapter.setOnRecyclerViewClickListener(this);

        // 리스트 초기화
        listCategory = new ArrayList<>();

        // 리사이클러뷰 요소 찾기
        recyclerViewCategory = view.findViewById(R.id.fragment_home_recyclerview_category);

        // 리사이클러뷰 어댑터 객체 생성
        recyclerViewCategoryAdapter = new RecyclerViewCategoryAdapter(listCategory);

        // 리사이클러뷰에 어댑터 설정
        recyclerViewCategory.setAdapter(recyclerViewCategoryAdapter);

        // 리사이클러뷰 어댑터에 클릭리스너 등록
        recyclerViewCategoryAdapter.setOnRecyclerViewClickListener(this);

        linearLayoutManagerCategory = new LinearLayoutManager(getContext())
        {
            @Override
            public void onLayoutCompleted(RecyclerView.State state)
            {
                super.onLayoutCompleted(state);

                try
                {
                    View categoryView = linearLayoutManagerCategory.findViewByPosition(selectedPosition);
                    TextView textView = categoryView.findViewById(R.id.recyclerview_category_text_category);
                    textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.textview_rounded_rectangle_selected));
                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                }
                catch (NullPointerException e)
                {

                }

            }
        };

        linearLayoutManagerCategory.setOrientation(RecyclerView.HORIZONTAL);

        recyclerViewCategory.setLayoutManager(linearLayoutManagerCategory);

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
                    String url = getString(R.string.api_url_category_select);

                    ReturnableCallback categoryCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_CATEGORY);

                    okHttpUtil.sendGetRequest(url, categoryCallback);

                    // 지금은 테스트용으로 임의의 값을 넣는다.
                    url = getString(R.string.api_url_video_main);

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
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
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

                    // 타이머 객체 생성
                    CountDownTimer timer = new CountDownTimer(2000, 2000)
                    {
                        // 사용하지 않는다.
                        @Override
                        public void onTick(long l)
                        {

                        }

                        @Override
                        public void onFinish()
                        {
                            // 2초 전 멈췄을 때 저장한 레이아웃매니저의 첫 번째 아이템 포지션과 현재 포지션이 동일하다면. 재생 시작.
                            if(position == linearLayoutManager.findFirstCompletelyVisibleItemPosition())
                            {
                                RecyclerViewVideoItem item = list.get(position);
                                View view = linearLayoutManager.findViewByPosition(position);
                                FrameLayout layout = view.findViewById(R.id.recyclerview_video_layout_player);

                                onFragmentInteractionListener.onCompletelyVisible(layout, item.getVideoDTO().getUrl());
                            }
                        }
                    };

                    timer.start();
                }

            }
        });

        setOnDialogInteractionListener();

        return view;
    }

    @Override
    public void onCreateOptionsMenu( Menu menu,  MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_toolbar_notification:

                // 알람 프래그먼트 호출
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new NotificationFragment(onCallbackResponseListener), FragmentTagUtil.FRAGMENT_TAG_NOTIFICATION);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;

            case R.id.menu_toolbar_search:

                // 검색 프래그먼트 호출
                fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new SearchFragment(onCallbackResponseListener), FragmentTagUtil.FRAGMENT_TAG_SEARCH);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;

            case R.id.menu_toolbar_profile:

                if (!drawerLayout.isDrawerOpen(Gravity.RIGHT))
                {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                    ((MainActivity)getActivity()).requestChannelData();
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

                fragmentManager = getParentFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ModifyChannelInformationFragment(onCallbackResponseListener), FragmentTagUtil.FRAGMENT_TAG_MODIFY_CHANNEL_INFORMATION);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.fragment_home_drawer_my_channel_layout :

                requestChannelDataAll();

                break;
            case R.id.fragment_home_drawer_logout_layout :

                // 기존에 저장된 AccessToken, RefreshToken, channelSequence를 전부 지워야 한다.
                PreferenceManager.removeKey(getContext().getApplicationContext(), PreferenceManager.PREFERENCES_ACCESSKEY);
                PreferenceManager.removeKey(getContext().getApplicationContext(), PreferenceManager.PREFERENCES_REFRESHKEY);
                PreferenceManager.removeKey(getContext().getApplicationContext(), PreferenceManager.PREFERENCES_CHANNEL_SEQUENCE);

                fragmentManager = getParentFragmentManager();
                // 스택에 쌓인 모든 프래그먼트를 제거 후 로그인 프래그먼트 호출.
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new LoginFragment(onCallbackResponseListener), FragmentTagUtil.FRAGMENT_TAG_LOGIN);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

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

    /*
    private void requestChannelData()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_channel_data) + PreferenceManager.getChannelSequence(getContext().getApplicationContext());

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_CHANNEL_DATA);

        okHttpUtil.sendGetRequest(url, returnableCallback);
    }
     */

    private void requestChannelDataAll()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.apu_url_channel_data_all);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_CHANNEL_DATA_ALL);

        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    public void setChannelData(ChannelDTO channel)
    {
        this.channel = channel;

        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                textName.setText(channel.getName());

                Glide.with(getContext())
                        .asBitmap()
                        .load(getString(R.string.api_url_image_profile) + channel.getProfile() + ".jpg")
                        .error(ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_account_circle_24))
                        .override(profile.getWidth(), profile.getHeight())
                        .centerCrop()
                        .apply(new RequestOptions().circleCrop())
                        .into(profile);

            }
        });
    }

    public void setRecyclerViewVideo(List<VideoDTO> videoList, ArrayList<ChannelDTO> channelList)
    {
        if(!list.isEmpty())
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
                //recyclerViewCategoryAdapter.notifyDataSetChanged();
            }
        });

    }

    public void setRecyclerViewCategory(List<CategoryDTO> categoryList)
    {
        if(!listCategory.isEmpty())
        {
            return;
        }

        listCategory.add(new CategoryDTO(0, "전체"));

        for (CategoryDTO item : categoryList)
        {
            listCategory.add(item);
        }

        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                recyclerViewCategoryAdapter.notifyDataSetChanged();
            }
        });
    }

    public void showChannelSelectDialog(List<ChannelDTO> channelList)
    {
        ArrayList<ChannelDTO> list = new ArrayList<>();
        list.addAll(channelList);

        ChannelSelectOptionFragment channelSelectOptionFragment = new ChannelSelectOptionFragment(onDialogInteractionListener, list);
        channelSelectOptionFragment.show(getParentFragmentManager(), channelSelectOptionFragment.getTag());
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

                break;
            case R.id.recyclerview_video_image_profile :

                item = list.get(position);

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ChannelBaseFragment(getContext(), onCallbackResponseListener, item.getChannelDTO().getChannelId()), FragmentTagUtil.FRAGMENT_TAG_CHANNEL_BASE);
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

            case R.id.recyclerview_category_text_category :

                if(selectedPosition != null)
                {
                    view = linearLayoutManagerCategory.findViewByPosition(selectedPosition);
                    TextView category = view.findViewById(R.id.recyclerview_category_text_category);
                    category.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.textview_rounded_rectangle));
                    category.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
                }

                // 만약 동영상(첫 번째) 카테고리를 클릭하였고, 이전에 선택한 카테고리와 다르다면.
                if(position == 0)
                {
                    // 전체 카테고리.
                    recyclerViewVideoAdapter = new RecyclerViewVideoAdapter(list);
                }
                else
                {
                    filteredList.clear();

                    for(RecyclerViewVideoItem listItem : list)
                    {
                        if(listCategory.get(position).getCategoryId() == listItem.getVideoDTO().getCategoryId())
                        {
                            filteredList.add(listItem);
                        }
                    }

                    recyclerViewVideoAdapter = new RecyclerViewVideoAdapter(filteredList);
                }

                recyclerView.setAdapter(recyclerViewVideoAdapter);
                recyclerViewVideoAdapter.setOnRecyclerViewClickListener(this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerViewVideoAdapter.notifyDataSetChanged();

                selectedPosition = position;

                view = linearLayoutManagerCategory.findViewByPosition(selectedPosition);
                TextView category = view.findViewById(R.id.recyclerview_category_text_category);
                category.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.textview_rounded_rectangle_selected));
                category.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

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

    private void setOnDialogInteractionListener()
    {
        onDialogInteractionListener = new OnDialogInteractionListener()
        {
            @Override
            public void onAddVoteResponse(String voteItem)
            {

            }

            @Override
            public void onDeletePlaylistItem(int position)
            {

            }

            @Override
            public void onSetVideoResolution(int position)
            {

            }

            @Override
            public void onDeleteCommunity()
            {

            }

            @Override
            public void onModifyCommunity()
            {

            }

            @Override
            public void onDeleteNotification()
            {

            }

            @Override
            public void onSelectChannel(int position, int channelId)
            {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ChannelBaseFragment(getContext(), onCallbackResponseListener, channelId), FragmentTagUtil.FRAGMENT_TAG_CHANNEL_BASE);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };
    }

}