package com.example.baetube.fragment.channel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.baetube.recyclerview.adapter.RecyclerViewCategoryAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChannelVideoFragment extends Fragment implements OnRecyclerViewClickListener
{
    private View view;

    private RecyclerView recyclerView;
    private RecyclerViewVideoAdapter recyclerViewVideoAdapter;
    private ArrayList<RecyclerViewVideoItem> list = new ArrayList<>();

    private OnCallbackResponseListener onCallbackResponseListener;
    private OkHttpUtil okHttpUtil;

    private LinearLayoutManager linearLayoutManagerCategory;
    private RecyclerView recyclerViewCategory;
    private RecyclerViewCategoryAdapter recyclerViewCategoryAdapter;
    private ArrayList<CategoryDTO> listCategory;

    private OnFragmentInteractionListener onFragmentInteractionListener;

    private Integer selectedPosition = 0;

    public ChannelVideoFragment(OnCallbackResponseListener onCallbackResponseListener)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_channel_video, container, false);

        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */

        //test();

        recyclerView = view.findViewById(R.id.fragment_channel_video_recyclerview);
        recyclerViewVideoAdapter = new RecyclerViewVideoAdapter(list);
        recyclerViewVideoAdapter.setOnRecyclerViewClickListener(this);
        recyclerView.setAdapter(recyclerViewVideoAdapter);
        recyclerViewVideoAdapter.setOnRecyclerViewClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 리스트 초기화
        listCategory = new ArrayList<>();

        // 리사이클러뷰 요소 찾기
        recyclerViewCategory = view.findViewById(R.id.fragment_channel_video_recyclerview_category);

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

        onFragmentInteractionListener = (OnFragmentInteractionListener) getContext();

        // Inflate the layout for this fragment
        return view;
    }

    /*

    @Override
    public void onViewCreated( View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        okHttpUtil = new OkHttpUtil();

        // 채널 동영상 정보를 가져온다.

        String videoUrl = "http://192.168.0.4:9090/Baetube_backEnd/api/video/channel_video/4";

        ReturnableCallback videoCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_CHANNEL_VIDEO);

        okHttpUtil.sendGetRequest(videoUrl, videoCallback);

    }

     */

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
                    Collections.sort(list, new Comparator<RecyclerViewVideoItem>()
                    {
                        @Override
                        public int compare(RecyclerViewVideoItem o1, RecyclerViewVideoItem o2)
                        {
                            // 최신순이기 때문에 videoId 기준으로 내림차순 정렬하면 된다.
                            if(o1.getVideoDTO().getVideoId() > o2.getVideoDTO().getVideoId())
                            {
                                return -1;
                            }
                            else if(o1.getVideoDTO().getVideoId() < o2.getVideoDTO().getVideoId())
                            {
                                return 1;
                            }
                            else
                            {
                                return 0;
                            }
                        }
                    });

                }
                else
                {

                    Collections.sort(list, new Comparator<RecyclerViewVideoItem>()
                    {
                        @Override
                        public int compare(RecyclerViewVideoItem o1, RecyclerViewVideoItem o2)
                        {
                            return o1.getVideoDTO().getTitle().compareTo(o2.getVideoDTO().getTitle());
                        }
                    });

                }

                selectedPosition = position;

                //item = list.get(selectedPosition);
                view = linearLayoutManagerCategory.findViewByPosition(selectedPosition);
                TextView category = view.findViewById(R.id.recyclerview_category_text_category);
                category.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.textview_rounded_rectangle_selected));
                category.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

                recyclerViewVideoAdapter.notifyDataSetChanged();

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

    private void setCategory()
    {
        String categories[] = getResources().getStringArray(R.array.category_subscribe_detail);

        for (int i = 0; i < categories.length; i++)
        {
            CategoryDTO category = new CategoryDTO(i, categories[i]);
            listCategory.add(category);
        }

        recyclerViewCategoryAdapter.notifyDataSetChanged();
    }

}