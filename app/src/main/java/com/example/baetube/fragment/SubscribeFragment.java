package com.example.baetube.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
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
import com.example.baetube.PreferenceManager;
import com.example.baetube.R;
import com.example.baetube.ViewType;
import com.example.baetube.activity.MainActivity;
import com.example.baetube.bottomsheetdialog.VideoOptionFragment;
import com.example.baetube.dto.CategoryDTO;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.CommunityDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.channel.ChannelBaseFragment;
import com.example.baetube.recyclerview.adapter.RecyclerViewCategoryAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewCommunityAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewSubscribeAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewCommunityItem;
import com.example.baetube.recyclerview.item.RecyclerViewSubscribeItem;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;
import com.example.baetube.recyclerview.item.RecyclerViewVoteItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubscribeFragment extends Fragment implements OnRecyclerViewClickListener, View.OnClickListener, OnFragmentInteractionListener
{

    private View view;
    private TextView buttonDetail;

    // 구독한 채널 관련 리사이클러뷰, 어댑터, 리스트
    private RecyclerView recyclerViewScribe;
    private RecyclerViewSubscribeAdapter recyclerViewSubscribeAdapter;
    private ArrayList<RecyclerViewSubscribeItem> recyclerViewSubscribeList;

    // 동영상 관련 리사이클러뷰, 어댑터, 리스트
    private RecyclerView recyclerViewVideo;
    private RecyclerViewVideoAdapter recyclerViewVideoAdapter;
    private ArrayList<RecyclerViewVideoItem> recyclerViewVideoList;

    private OkHttpUtil okHttpUtil;

    private OnCallbackResponseListener onCallbackResponseListener;
    private OnFragmentInteractionListener onFragmentInteractionListener;

    // 리사이클러뷰에 사용되는 레이아웃 매니져
    private LinearLayoutManager linearLayoutManagerSubscribe;
    private LinearLayoutManager linearLayoutManagerVideo;
    private LinearLayoutManager linearLayoutManagerCommunity;
    private LinearLayoutManager linearLayoutManagerCategory;

    // 카테고리 관련 리사이클러뷰, 어댑터, 리스트
    private RecyclerView recyclerViewCategory;
    private RecyclerViewCategoryAdapter recyclerViewCategoryAdapter;
    private ArrayList<CategoryDTO> listCategory;

    // 커뮤니티 게시글 관련 리사이클러뷰, 어댑터, 리스트
    private RecyclerView recyclerViewCommunity;
    private RecyclerViewCommunityAdapter recyclerViewCommunityAdapter;
    private ArrayList<RecyclerViewCommunityItem> listCommunity;

    private Integer selectedPosition = 0;

    // 무의미하게 서버에 요청하는 것을 방지하기 위한 boolean 타입의 변수.
    // 서버에 한번 요청하게 되면 true로 전환.
    private boolean isVideoCalled;
    private boolean isCommunityCalled;
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
        linearLayoutManagerSubscribe = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false)
        {
            @Override
            public void onLayoutCompleted(RecyclerView.State state)
            {
                return;
            }
        };

        // 리사이클러뷰에 레이아웃 매니저 설정 (가로로 출력)
        recyclerViewScribe.setLayoutManager(linearLayoutManagerSubscribe);

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

        isVideoCalled = false;

        // 리사이클러뷰에 레이아웃 매니저 설정
        linearLayoutManagerVideo = new LinearLayoutManager(getContext()) {
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

                if (!isVideoCalled)
                {
                    String subscribersUrl = getString(R.string.api_url_subscribers_channel) + PreferenceManager.getChannelSequence(getContext().getApplicationContext());

                    ReturnableCallback subscribersCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SUBSCRIBERS_CHANNEL);

                    okHttpUtil.sendGetRequest(subscribersUrl, subscribersCallback);

                    String subscribersVideoUrl = getString(R.string.api_url_video_subscribe) + PreferenceManager.getChannelSequence(getContext().getApplicationContext());

                    ReturnableCallback subscribersVideoCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_SUBSCRIBE_VIDEO);

                    okHttpUtil.sendGetRequest(subscribersVideoUrl, subscribersVideoCallback);

                    isVideoCalled = true;
                }

                if(!recyclerViewVideoList.isEmpty() && recyclerViewVideo.getChildCount() > 0 && !isFirst)
                {
                    // 현재 화면에 전부 보이는 첫 번째 뷰의 어댑터 위치를 반환한다.
                    int position = linearLayoutManagerVideo.findFirstCompletelyVisibleItemPosition();
                    System.out.println("리사이클러뷰 비디오 리스트 포지션 : " + position);

                    if(position != 0)
                    {
                        return;
                    }

                    RecyclerViewVideoItem item = recyclerViewVideoList.get(position);
                    View view = linearLayoutManagerVideo.findViewByPosition(position);
                    FrameLayout layout = view.findViewById(R.id.recyclerview_video_layout_player);

                    onFragmentInteractionListener.onCompletelyVisible(layout, item.getVideoDTO().getUrl());

                    isFirst = true;
                }



            }
        };
        recyclerViewVideo.setLayoutManager(linearLayoutManagerVideo);

        onFragmentInteractionListener = (OnFragmentInteractionListener) getContext();

        recyclerViewVideo.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged( RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);

                // 스크롤을 하지 않는 상태일 때.
                if(newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    // 현재 화면에 전부 보이는 첫 번째 뷰의 어댑터 위치를 반환한다.
                    int position = linearLayoutManagerVideo.findFirstCompletelyVisibleItemPosition();

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
                            if(position == linearLayoutManagerVideo.findFirstCompletelyVisibleItemPosition())
                            {
                                RecyclerViewVideoItem item = recyclerViewVideoList.get(position);
                                View view = linearLayoutManagerVideo.findViewByPosition(position);
                                FrameLayout layout = view.findViewById(R.id.recyclerview_video_layout_player);

                                onFragmentInteractionListener.onCompletelyVisible(layout, item.getVideoDTO().getUrl());
                            }
                        }
                    };

                    timer.start();
                }
            }
        });

        // 리스트 초기화
        listCategory = new ArrayList<>();

        // 리사이클러뷰 요소 찾기
        recyclerViewCategory = view.findViewById(R.id.fragment_subscribe_recyclerview_category);

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

                View view = linearLayoutManagerCategory.findViewByPosition(selectedPosition);
                TextView textView = view.findViewById(R.id.recyclerview_category_text_category);
                textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.textview_rounded_rectangle_selected));
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            }
        };
        linearLayoutManagerCategory.setOrientation(RecyclerView.HORIZONTAL);

        recyclerViewCategory.setLayoutManager(linearLayoutManagerCategory);

        setCategory();

        // 리스트 초기화
        listCommunity = new ArrayList<>();

        // 리사이클러뷰 요소 찾기
        recyclerViewCommunity = view.findViewById(R.id.fragment_subscribe_community_recyclerview);

        // 리사이클러뷰 어댑터 객체 생성
        recyclerViewCommunityAdapter = new RecyclerViewCommunityAdapter(listCommunity);

        // 리사이클러뷰 어댑터 설정
        recyclerViewCommunity.setAdapter(recyclerViewCommunityAdapter);

        // 리사이클러뷰 어댑터 클릭리스너 등록
        recyclerViewCommunityAdapter.setOnRecyclerViewClickListener(this);

        linearLayoutManagerCommunity = new LinearLayoutManager(getContext());
        recyclerViewCommunity.setLayoutManager(linearLayoutManagerCommunity);

        isCommunityCalled = false;

        return view;
    }


    @Override
    public void onCreateOptionsMenu( Menu menu,  MenuInflater inflater)
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

    public void setRecyclerViewCommunity(List<CommunityDTO> communityList)
    {
        HashMap<Integer, Integer> hashMap = new HashMap<>();

        for(int i = 0; i < communityList.size(); i++)
        {
            RecyclerViewCommunityItem item = new RecyclerViewCommunityItem();

            // 이미 같은 커뮤니티id가 존재한다면. 투표가 있다고 가정하고
            // 기존에 들어있는 아이템의 투표리스트에 투표 정보를 주입해서 삽입한다.
            if(hashMap.containsKey(communityList.get(i).getCommunityId()))
            {
                RecyclerViewCommunityItem preItem = listCommunity.get(hashMap.get(communityList.get(i).getCommunityId()));
                ArrayList<RecyclerViewVoteItem> voteList = preItem.getVoteList();

                CommunityDTO community = communityList.get(i);
                VoteDTO vote = new VoteDTO();

                vote.setVoteId(community.getVoteId());
                vote.setCommunityId(community.getCommunityId());
                vote.setVoteOptionId(community.getVoteOptionId());
                vote.setOption(community.getOption());
                vote.setTitle(community.getTitle());
                vote.setCount(community.getCount());

                if(community.getSelectedChannelId() == null)
                {
                    voteList.add(new RecyclerViewVoteItem(vote, false));
                }
                else
                {
                    voteList.add(new RecyclerViewVoteItem(vote, true));
                }
            }
            else // 같은 커뮤니티id가 존재하지 않는다면 그냥 삽입한다.
            {
                CommunityDTO community = communityList.get(i);

                hashMap.put(community.getCommunityId(), listCommunity.size());
                item.setCommunityDTO(community);

                ChannelDTO channel = new ChannelDTO();
                channel.setChannelId(community.getChannelId());
                channel.setName(community.getName());
                channel.setProfile(community.getProfile());

                item.setChannelDTO(channel);

                ArrayList<RecyclerViewVoteItem> voteList = new ArrayList<>();

                if (community.getVoteId() != null)
                {
                    VoteDTO vote = new VoteDTO();

                    vote.setVoteId(community.getVoteId());
                    vote.setCommunityId(community.getCommunityId());
                    vote.setVoteOptionId(community.getVoteOptionId());
                    vote.setOption(community.getOption());
                    vote.setTitle(community.getTitle());
                    vote.setCount(community.getCount());

                    if(community.getSelectedChannelId() == null)
                    {
                        voteList.add(new RecyclerViewVoteItem(vote, false));
                    }
                    else
                    {
                        voteList.add(new RecyclerViewVoteItem(vote, true));
                    }
                }

                item.setVoteList(voteList);

                listCommunity.add(item);
            }

        }

        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run()
            {
                recyclerViewCommunityAdapter.notifyDataSetChanged();
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
                fragmentTransaction.replace(R.id.activity_main_layout, new ChannelBaseFragment(getContext(), onCallbackResponseListener,
                        recyclerViewVideoList.get(position).getChannelDTO().getChannelId()), FragmentTagUtil.FRAGMENT_TAG_CHANNEL_BASE);
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
                fragmentTransaction.replace(R.id.activity_main_layout, new ChannelBaseFragment(getContext(), onCallbackResponseListener,
                        recyclerViewSubscribeList.get(position).getChannelDTO().getChannelId()), FragmentTagUtil.FRAGMENT_TAG_CHANNEL_BASE);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;

            case R.id.recyclerview_category_text_category :

                if(selectedPosition != null)
                {
                    //item = list.get(selectedPosition);
                    view = linearLayoutManagerCategory.findViewByPosition(selectedPosition);
                    TextView category = view.findViewById(R.id.recyclerview_category_text_category);
                    category.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.textview_rounded_rectangle));
                    category.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
                }

                // 만약 동영상(첫 번째) 카테고리를 클릭하였고, 이전에 선택한 카테고리와 다르다면.
                if(position == 0)
                {
                    recyclerViewCommunity.setVisibility(View.GONE);
                    recyclerViewVideo.setVisibility(View.VISIBLE);
                }
                else
                {
                    recyclerViewCommunity.setVisibility(View.VISIBLE);
                    recyclerViewVideo.setVisibility(View.GONE);
                    requestCommunityData();
                }


                selectedPosition = position;

                //item = list.get(selectedPosition);
                view = linearLayoutManagerCategory.findViewByPosition(selectedPosition);
                TextView category = view.findViewById(R.id.recyclerview_category_text_category);
                category.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.textview_rounded_rectangle_selected));
                category.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

                break;
            default :
                // 비디오 서페이스
                item = recyclerViewVideoList.get(position);
                onFragmentInteractionListener.onVideoItemClick(item);
                break;
        }
    }

    private void requestCommunityData()
    {
        if(!isCommunityCalled)
        {
            isCommunityCalled = true;

            String url = getString(R.string.api_url_subscribers_community_select) + PreferenceManager.getChannelSequence(getContext().getApplicationContext());

            ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_SUBSCRIBERS_COMMUNITY);

            okHttpUtil.sendGetRequest(url, returnableCallback);
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

    private void setCategory()
    {
        String categories[] = getResources().getStringArray(R.array.category_subscribe);

        for (int i = 0; i < categories.length; i++)
        {
            CategoryDTO category = new CategoryDTO(i, categories[i]);
            listCategory.add(category);
        }

        recyclerViewCategoryAdapter.notifyDataSetChanged();
    }
}