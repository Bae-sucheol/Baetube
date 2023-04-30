package com.example.baetube.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.baetube.dto.PlaylistDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewCategoryAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewStorageAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewPlaylistItem;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StorageFragment extends Fragment implements OnRecyclerViewClickListener, View.OnClickListener
{
    private View view;
    private TextView buttonDetail;

    private RecyclerView recyclerViewVideoHistory;
    private RecyclerViewVideoAdapter recyclerViewVideoHistoryAdapter;
    private ArrayList<RecyclerViewVideoItem> videoHistoryList;

    private RecyclerView recyclerViewVideoStorage;
    private RecyclerViewStorageAdapter recyclerViewStorageAdapter;
    private ArrayList<RecyclerViewPlaylistItem> storageList;

    private OkHttpUtil okHttpUtil;
    private OnCallbackResponseListener onCallbackResponseListener;
    private OnFragmentInteractionListener onFragmentInteractionListener;

    private LinearLayoutManager videoHistoryLayoutManager;
    private LinearLayoutManager storageLayoutManager;
    private LinearLayoutManager linearLayoutManagerCategory;

    private RecyclerView recyclerViewCategory;
    private RecyclerViewCategoryAdapter recyclerViewCategoryAdapter;
    private ArrayList<CategoryDTO> listCategory;

    private Integer selectedPosition = 0;

    private boolean isCalled;

    public StorageFragment(OnCallbackResponseListener onCallbackResponseListener)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_storage, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);

        //test();
        //test2();

        buttonDetail = view.findViewById(R.id.fragment_storage_text_button_detail);
        buttonDetail.setOnClickListener(this);

        /*
         * 동영상 시청 기록 리스트 출력
         */
        // 리스트 초기화
        videoHistoryList = new ArrayList<>();

        // 리사이클러뷰 요소 찾기
        recyclerViewVideoHistory = view.findViewById(R.id.fragment_storage_history_recyclerview);

        // 리사이클러뷰 어댑터 객체 생성
        recyclerViewVideoHistoryAdapter = new RecyclerViewVideoAdapter(videoHistoryList);

        // 리사이클러뷰에 어댑터 설정
        recyclerViewVideoHistory.setAdapter(recyclerViewVideoHistoryAdapter);

        // 어댑터에 클릭 리스너 등록
        recyclerViewVideoHistoryAdapter.setOnRecyclerViewClickListener(this);

        // 레이아웃 매니저 객체 생성.
        videoHistoryLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);

        // 리사이클러뷰에 레이아웃 매니저 설정 (가로로 출력)
        recyclerViewVideoHistory.setLayoutManager(videoHistoryLayoutManager);

        /*
         * 보관함 리스트 출력
         */
        // 리스트 초기화
        storageList = new ArrayList<>();

        // 재생목록 추가, 나중에 볼 동영상, 좋아요 표시한 동영상 등 기본 보관함 목록을 추가해야 한다.
        setBasicStorageList();

        // 리사이클러뷰 요소 찾기
        recyclerViewVideoStorage = view.findViewById(R.id.fragment_storage_playlist_recyclerview);

        // 리사이클러뷰 어댑터 객체 생성
        recyclerViewStorageAdapter = new RecyclerViewStorageAdapter(storageList);

        // 리사이클러뷰에 어댑터 설정
        recyclerViewVideoStorage.setAdapter(recyclerViewStorageAdapter);

        // 어댑터에 클릭 리스너 등록
        recyclerViewStorageAdapter.setOnRecyclerViewClickListener(this);

        isCalled = false;

        // 레이아웃 매니저 객체 생성.
        storageLayoutManager = new LinearLayoutManager(getContext())
        {
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
                    // 지금은 테스트용으로 임의의 값을 넣는다.
                    String historyVideoUrl = getString(R.string.api_url_video_history);

                    ReturnableCallback historyVideoCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_HISTORY_VIDEO);

                    okHttpUtil.sendGetRequest(historyVideoUrl, historyVideoCallback);

                    String playlistUrl = getString(R.string.api_url_storage_channel) + PreferenceManager.getChannelSequence(getContext().getApplicationContext());

                    ReturnableCallback playlistCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_PLAYLIST);

                    okHttpUtil.sendGetRequest(playlistUrl, playlistCallback);

                    isCalled = true;
                }

            }
        };

        // 리사이클러뷰에 레이아웃 매니저 설정 (가로로 출력)
        recyclerViewVideoStorage.setLayoutManager(storageLayoutManager);

        onFragmentInteractionListener = (OnFragmentInteractionListener) getContext();

        // 리스트 초기화
        listCategory = new ArrayList<>();

        // 리사이클러뷰 요소 찾기
        recyclerViewCategory = view.findViewById(R.id.fragment_storage_recyclerview_category);

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

        // Inflate the layout for this fragment
        return view;
    }

    // 일단 재생목록 추가만.
    private void setBasicStorageList()
    {
        RecyclerViewPlaylistItem itemAdd = new RecyclerViewPlaylistItem();
        storageList.add(0, itemAdd);
    }

    @Override
    public void onCreateOptionsMenu( Menu menu,  MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_main, menu);
    }

    public void setRecyclerViewVideoHistory(List<VideoDTO> videoList, List<ChannelDTO> channelList)
    {
        if(videoHistoryList == null)
        {
            return;
        }

        for(int i = 0; i < videoList.size(); i++)
        {
            RecyclerViewVideoItem item = new RecyclerViewVideoItem();
            item.setViewType(ViewType.VIDEO_SMALL);
            item.setVideoDTO(videoList.get(i));
            item.setChannelDTO(channelList.get(i));

            videoHistoryList.add(item);
        }

        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run()
            {
                recyclerViewVideoHistoryAdapter.notifyDataSetChanged();
            }
        });
    }

    public void setRecyclerViewStorage(List<PlaylistDTO> playlistList)
    {
        if(storageList == null)
        {
            return;
        }

        ChannelDTO channel = new ChannelDTO();
        channel.setName("test");
        channel.setChannelId(4);

        for(int i = 0; i < playlistList.size(); i++)
        {
            RecyclerViewPlaylistItem item = new RecyclerViewPlaylistItem();

            item.setPlaylistDTO(playlistList.get(i));
            item.setChannelDTO(channel);

            storageList.add(item);
        }

        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run()
            {
                recyclerViewStorageAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fragment_storage_text_button_detail :

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout,
                        new HistoryDetailFragment(onCallbackResponseListener)
                        , FragmentTagUtil.FRAGMENT_TAG_HISTORY_DETAIL);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
        }
    }

    @Override
    public void onItemClick(View view, int position)
    {

        switch (view.getId())
        {
            case R.id.exo_artwork :

                //VideoFragment videoFragment = new VideoFragment(onCallbackResponseListener);
                //videoFragment.show(getParentFragmentManager(), videoFragment.getTag());

                RecyclerViewVideoItem item = videoHistoryList.get(position);
                onFragmentInteractionListener.onVideoItemClick(item);

                break;
            case R.id.recyclerview_video_image_thumbnail :

                //VideoFragment videoFragment = new VideoFragment(onCallbackResponseListener);
                //videoFragment.show(getParentFragmentManager(), videoFragment.getTag());

                item = videoHistoryList.get(position);
                onFragmentInteractionListener.onVideoItemClick(item);

                break;
            case R.id.recyclerview_video_image_option :

                VideoOptionFragment videoOptionFragment = new VideoOptionFragment(getContext());
                videoOptionFragment.show(getParentFragmentManager(), videoOptionFragment.getTag());
                ((MainActivity)getContext()).setManagedVideoItem(videoHistoryList.get(position));

                break;
            case R.id.recyclerview_video_layout_information :

                //videoFragment = new VideoFragment(onCallbackResponseListener);
                //videoFragment.show(getParentFragmentManager(), videoFragment.getTag());

                break;
            case R.id.recyclerview_storage_layout_main :

                // 0 번째 포지션이라면 새 재생목록 이라는 의미이므로 관련 기능을 제공해야 한다.
                if(position == 0)
                {
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.activity_main_layout,
                            new AddStorageFragment((ArrayList<RecyclerViewVideoItem>)videoHistoryList.clone(), onCallbackResponseListener), FragmentTagUtil.FRAGMENT_TAG_ADD_STORAGE);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    return;
                }

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new PlaylistDetailFragment(storageList.get(position) , onCallbackResponseListener),
                        FragmentTagUtil.FRAGMENT_TAG_PLAYLIST_DETAIL);
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

                // 새 재생목록을 삭제한다.
                storageList.remove(0);

                // 만약 동영상(첫 번째) 카테고리를 클릭하였고, 이전에 선택한 카테고리와 다르다면.
                if(position == 0)
                {
                    Collections.sort(storageList,new Comparator<RecyclerViewPlaylistItem>()
                    {
                        @Override
                        public int compare(RecyclerViewPlaylistItem o1, RecyclerViewPlaylistItem o2)
                        {
                            // 최신순이기 때문에 playlistId를 기준으로 오름차순 정렬하면 된다.
                            if(o1.getPlaylistDTO().getPlaylistId() < o2.getPlaylistDTO().getPlaylistId())
                            {
                                return -1;
                            }
                            else if(o1.getPlaylistDTO().getPlaylistId() > o2.getPlaylistDTO().getPlaylistId())
                            {
                                return 1;
                            }
                            else
                            {
                                return 0;
                            }
                        }
                    });

                    // 마지막에 새 재생목록을 추가한다.
                    setBasicStorageList();

                }
                else
                {

                    Collections.sort(storageList,new Comparator<RecyclerViewPlaylistItem>()
                    {
                        @Override
                        public int compare(RecyclerViewPlaylistItem o1, RecyclerViewPlaylistItem o2)
                        {
                            return o1.getPlaylistDTO().getName().compareTo(o2.getPlaylistDTO().getName());
                        }
                    });

                    // 마지막에 새 재생목록을 추가한다.
                    setBasicStorageList();
                }

                selectedPosition = position;

                //item = list.get(selectedPosition);
                view = linearLayoutManagerCategory.findViewByPosition(selectedPosition);
                TextView category = view.findViewById(R.id.recyclerview_category_text_category);
                category.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.textview_rounded_rectangle_selected));
                category.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

                recyclerViewStorageAdapter.notifyDataSetChanged();

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
    public void onVideoItemClick(RecyclerViewVideoItem videoItem)
    {
        onFragmentInteractionListener.onVideoItemClick(videoItem);
    }

    @Override
    public void onCompletelyVisible(FrameLayout layout, String uuid)
    {

    }
     */

    public boolean isCalled()
    {
        return isCalled;
    }

    private void setCategory()
    {
        String categories[] = getResources().getStringArray(R.array.category_storage);

        for (int i = 0; i < categories.length; i++)
        {
            CategoryDTO category = new CategoryDTO(i, categories[i]);
            listCategory.add(category);
        }

        recyclerViewCategoryAdapter.notifyDataSetChanged();
    }
}