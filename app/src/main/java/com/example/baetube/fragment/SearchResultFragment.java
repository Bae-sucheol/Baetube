package com.example.baetube.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.baetube.R;
import com.example.baetube.ViewType;
import com.example.baetube.activity.MainActivity;
import com.example.baetube.bottomsheetdialog.VideoOptionFragment;
import com.example.baetube.dto.CategoryDTO;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.channel.ChannelBaseFragment;
import com.example.baetube.recyclerview.adapter.RecyclerViewCategoryAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewChannelAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.util.ArrayList;
import java.util.List;

public class SearchResultFragment extends Fragment implements OnRecyclerViewClickListener
{
    private View view;

    private String keywords;
    private OnCallbackResponseListener onCallbackResponseListener;
    private OnFragmentInteractionListener onFragmentInteractionListener;
    private OkHttpUtil okHttpUtil;

    private LinearLayoutManager linearLayoutManagerCategory;

    private RecyclerView recyclerViewCategory;
    private RecyclerViewCategoryAdapter recyclerViewCategoryAdapter;
    private ArrayList<CategoryDTO> listCategory;

    private LinearLayoutManager linearLayoutManagerVideo;
    private RecyclerView recyclerViewVideo;
    private RecyclerViewVideoAdapter recyclerViewVideoAdapter;
    private ArrayList<RecyclerViewVideoItem> listVideo;

    private LinearLayoutManager linearLayoutManagerChannel;
    private RecyclerView recyclerViewChannel;
    private RecyclerViewChannelAdapter recyclerViewChannelAdapter;
    private ArrayList<ChannelDTO> listChannel;

    private Integer selectedPosition;

    private boolean isCalled;
    private boolean isFirst;

    public SearchResultFragment(OnCallbackResponseListener onCallbackResponseListener, String keywords)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
        this.keywords = keywords;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_search_result, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedPosition = 0;

        // 리스트 초기화.
        listVideo = new ArrayList<>();

        // 리사이클러뷰 요소 찾기
        recyclerViewVideo = view.findViewById(R.id.fragment_search_result_recyclerview_video);

        // 리사이클러뷰 어댑터 객체 생성
        recyclerViewVideoAdapter = new RecyclerViewVideoAdapter(listVideo);

        // 리사이클러뷰에 어댑터 설정
        recyclerViewVideo.setAdapter(recyclerViewVideoAdapter);

        // 리사이클러뷰 어댑터에 클릭리스너 등록
        recyclerViewVideoAdapter.setOnRecyclerViewClickListener(this);

        // 리스트 초기화.
        listChannel = new ArrayList<>();

        // 리사이클러뷰 요소 찾기
        recyclerViewChannel = view.findViewById(R.id.fragment_search_result_recyclerview_channel);

        // 리사이클러뷰 어댑터 객체 생성
        recyclerViewChannelAdapter = new RecyclerViewChannelAdapter(listChannel);

        // 리사이클러뷰에 어댑터 설정
        recyclerViewChannel.setAdapter(recyclerViewChannelAdapter);

        // 리사이클러뷰 어댑터에 클릭리스너 등록
        recyclerViewChannelAdapter.setOnRecyclerViewClickListener(this);

        // 라사이클러뷰에 레이아웃 매니저 설정
        linearLayoutManagerChannel = new LinearLayoutManager(getContext());

        recyclerViewChannel.setLayoutManager(linearLayoutManagerChannel);

        // 리스트 초기화
        listCategory = new ArrayList<>();

        // 리사이클러뷰 요소 찾기
        recyclerViewCategory = view.findViewById(R.id.fragment_search_result_recyclerview_category);

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

        isCalled = false;
        isFirst = false;

        onFragmentInteractionListener = (OnFragmentInteractionListener) getContext();

        // 리사이클러뷰에 레이아웃 매니저 설정
        linearLayoutManagerVideo = new LinearLayoutManager(getContext())
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
                    String url = getString(R.string.api_url_search_video) + keywords;

                    ReturnableCallback mainVideoCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_SEARCH_VIDEO);

                    okHttpUtil.sendGetRequest(url, mainVideoCallback);

                    isCalled = true;
                }

                if(!listVideo.isEmpty() && recyclerViewVideo.getChildCount() > 0 && !isFirst)
                {
                    // 현재 화면에 전부 보이는 첫 번째 뷰의 어댑터 위치를 반환한다.
                    int position = linearLayoutManagerVideo.findFirstCompletelyVisibleItemPosition();

                    if(position != 0)
                    {
                        return;
                    }

                    RecyclerViewVideoItem item = listVideo.get(position);
                    View view = linearLayoutManagerVideo.findViewByPosition(position);
                    FrameLayout layout = view.findViewById(R.id.recyclerview_video_layout_player);

                    onFragmentInteractionListener.onCompletelyVisible(layout, item.getVideoDTO().getUrl());

                    isFirst = true;
                }

            }
        };

        recyclerViewVideo.setLayoutManager(linearLayoutManagerVideo);

        recyclerViewVideo.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
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
                                RecyclerViewVideoItem item = listVideo.get(position);
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

        setCategory();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item)
    {
        switch (item.getItemId())
        {
            /*
             * 뒤로가기 버튼을 눌렀을 때
             * 액티비티의 onBackPressed() 메소드를 실행.
             * onBackPressed() 메소드에서는 fragmentManager를 통해
             * popBackStack() 메소드를 사용하여 뒤로가기 기능을 구현.
             */
            case android.R.id.home :

                getActivity().onBackPressed();

            default :
                return super.onOptionsItemSelected(item);
        }
    }

    private void setCategory()
    {
        String categories[] = getResources().getStringArray(R.array.category_search_result);

        for (int i = 0; i < categories.length; i++)
        {
            CategoryDTO category = new CategoryDTO(i, categories[i]);
            listCategory.add(category);
        }

        recyclerViewCategoryAdapter.notifyDataSetChanged();
    }

    public void setRecyclerViewVideo(List<VideoDTO> videoList, ArrayList<ChannelDTO> channelList)
    {
        if(!listVideo.isEmpty())
        {
            return;
        }

        for(int i = 0; i < videoList.size(); i++)
        {
            RecyclerViewVideoItem item = new RecyclerViewVideoItem();
            item.setViewType(ViewType.VIDEO_LARGE);
            item.setVideoDTO(videoList.get(i));
            item.setChannelDTO(channelList.get(i));

            listVideo.add(item);
        }

        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run()
            {
                recyclerViewVideoAdapter.notifyDataSetChanged();
            }
        });

    }


    private void requestChannelDataKeywords()
    {
        String url = getString(R.string.api_url_search_channel) + keywords;

        ReturnableCallback mainVideoCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_SEARCH_CHANNEL);

        okHttpUtil.sendGetRequest(url, mainVideoCallback);
    }


    public void setRecyclerViewChannel(List<ChannelDTO> channelList)
    {
        if(!listChannel.isEmpty())
        {
            return;
        }

        listChannel.addAll(channelList);

        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run()
            {
                recyclerViewChannelAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (view.getId())
        {
            case R.id.recyclerview_video_layout_player :

                RecyclerViewVideoItem item = listVideo.get(position);
                //this.onVideoItemClick(item.getVideoDTO().getUrl());
                onFragmentInteractionListener.onVideoItemClick(item);

            case R.id.recyclerview_video_image_thumbnail :

                item = listVideo.get(position);
                //this.onVideoItemClick(item.getVideoDTO().getUrl());
                onFragmentInteractionListener.onVideoItemClick(item);

                break;
            case R.id.recyclerview_video_image_profile :

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ChannelBaseFragment(getContext(), onCallbackResponseListener, listVideo.get(position).getChannelDTO().getChannelId()), FragmentTagUtil.FRAGMENT_TAG_CHANNEL_BASE);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.recyclerview_video_image_option :

                VideoOptionFragment videoOptionFragment = new VideoOptionFragment(getContext());
                videoOptionFragment.show(getParentFragmentManager(), videoOptionFragment.getTag());
                ((MainActivity)getContext()).setManagedVideoItem(listVideo.get(position));

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
                    recyclerViewVideo.setVisibility(View.VISIBLE);
                    recyclerViewChannel.setVisibility(View.GONE);
                }
                else
                {
                    recyclerViewChannel.setVisibility(View.VISIBLE);
                    recyclerViewVideo.setVisibility(View.GONE);
                    //((MainActivity)getActivity()).requestChannelData();
                    requestChannelDataKeywords();
                }

                selectedPosition = position;

                //item = list.get(selectedPosition);
                view = linearLayoutManagerCategory.findViewByPosition(selectedPosition);
                TextView category = view.findViewById(R.id.recyclerview_category_text_category);
                category.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.textview_rounded_rectangle_selected));
                category.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

                break;
            case R.id.recyclerview_channel_image_option :

                // 옵션 추가해서 넣기.

                break;
            case R.id.recyclerview_channel_image_profile :

            case R.id.recyclerview_channel_layout_information :

                item = listVideo.get(position);

                fragmentManager = getParentFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ChannelBaseFragment(getContext(), onCallbackResponseListener, item.getChannelDTO().getChannelId()), FragmentTagUtil.FRAGMENT_TAG_CHANNEL_BASE);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.recyclerview_channel_text_button_sub :

                // 구독 기능.

                break;

            default :

                // 나머지는 비디오 서페이스 등.
                item = listVideo.get(position);
                onFragmentInteractionListener.onVideoItemClick(item);
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


}