package com.example.baetube.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.ViewType;
import com.example.baetube.WidthProperty;
import com.example.baetube.activity.MainActivity;
import com.example.baetube.bottomsheetdialog.HistoryVideoOptionManageFragment;
import com.example.baetube.bottomsheetdialog.VideoFragment;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.sql.Timestamp;
import java.util.ArrayList;

public class HistoryDetailFragment extends Fragment implements OnRecyclerViewClickListener, View.OnClickListener, View.OnFocusChangeListener
{
    private View view;

    private RecyclerView recyclerView;
    private RecyclerViewVideoAdapter adapter;
    private ArrayList<RecyclerViewVideoItem> list;

    private EditText search;
    private TextView buttonCancel;

    private int slideDistance;
    private int animationDuration;
    private ConstraintLayout layoutSearch;

    private OnCallbackResponseListener onCallbackResponseListener;

    public HistoryDetailFragment(ArrayList<RecyclerViewVideoItem> list, OnCallbackResponseListener onCallbackResponseListener)
    {
        this.list = list;
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_history_detail, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar.setTitle("기록");
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //test();
        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */

        setHistoryList();

        recyclerView = view.findViewById(R.id.fragment_history_detail_recyclerview);
        adapter = new RecyclerViewVideoAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnRecyclerViewClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        search = view.findViewById(R.id.fragment_history_detail_edit_search);
        search.setTag("asdasdasdasdsadasdasd");
        buttonCancel = view.findViewById(R.id.fragment_history_detail_text_button_cancel);
        layoutSearch = view.findViewById(R.id.fragment_history_detail_layout_search);

        search.setOnFocusChangeListener(this);
        buttonCancel.setOnClickListener(this);

        animationDuration = getContext().getResources().getInteger(R.integer.animation_duration_histort_detail);

        // Inflate the layout for this fragment
        return view;
    }

    private void setHistoryList()
    {
        for(RecyclerViewVideoItem item : list)
        {
            item.setViewType(ViewType.VIDEO_MEDIUM);
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_sub, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home :
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void test()
    {
        String channel_names[] = {"홍길동", "이순신", "장영실", "김유신", "허준"};
        String titles[] = {"쉽게 배우는 자바", "쉽게 배우는 학익진", "쉽게 배우는 거중기",
                "쉽게 배우는 전투법", "쉽게 배우는 침술"};

        RecyclerViewVideoItem item = new RecyclerViewVideoItem();

        ChannelDTO channelDTO = new ChannelDTO();
        VideoDTO videoDTO = new VideoDTO();

        item.setChannelDTO(channelDTO);
        item.setVideoDTO(videoDTO);

        item.setViewType(ViewType.VIDEO_DIVIDER);
        videoDTO.setDate(new Timestamp(System.currentTimeMillis()));

        list.add(item);

        for(int i = 0; i < 5; i++)
        {
            item = new RecyclerViewVideoItem();

            channelDTO = new ChannelDTO();
            videoDTO = new VideoDTO();

            item.setChannelDTO(channelDTO);
            item.setVideoDTO(videoDTO);
            item.setViewType(ViewType.VIDEO_MEDIUM);

            channelDTO.setName(channel_names[i]);
            videoDTO.setDate(new Timestamp(System.currentTimeMillis()));
            videoDTO.setTitle(titles[i]);
            videoDTO.setViews(500);

            list.add(item);
        }

        item = new RecyclerViewVideoItem();

        channelDTO = new ChannelDTO();
        videoDTO = new VideoDTO();

        item.setChannelDTO(channelDTO);
        item.setVideoDTO(videoDTO);

        item.setViewType(ViewType.VIDEO_DIVIDER);
        videoDTO.setDate(new Timestamp(System.currentTimeMillis()));

        list.add(item);

        for(int i = 0; i < 5; i++)
        {
            item = new RecyclerViewVideoItem();

            channelDTO = new ChannelDTO();
            videoDTO = new VideoDTO();

            item.setChannelDTO(channelDTO);
            item.setVideoDTO(videoDTO);
            item.setViewType(ViewType.VIDEO_MEDIUM);

            channelDTO.setName(channel_names[i]);
            videoDTO.setDate(new Timestamp(System.currentTimeMillis()));
            videoDTO.setTitle(titles[i]);
            videoDTO.setViews(500);

            list.add(item);
        }
    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (view.getId())
        {
            case R.id.recyclerview_video_image_thumbnail :

                VideoFragment videoFragment = new VideoFragment(onCallbackResponseListener);
                videoFragment.show(getParentFragmentManager(), videoFragment.getTag());

                break;
            case R.id.recyclerview_video_image_option :

                HistoryVideoOptionManageFragment historyVideoOptionManageFragment = new HistoryVideoOptionManageFragment(getContext(), adapter, list, position);
                historyVideoOptionManageFragment.show(getParentFragmentManager(), historyVideoOptionManageFragment.getTag());
                System.out.println("했는뎅...");
                ((MainActivity)getContext()).setManagedVideoItem(list.get(position));

                break;
            case R.id.recyclerview_video_layout_information :

                videoFragment = new VideoFragment(onCallbackResponseListener);
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

    @Override
    public void onCastVoteOption(VoteDTO voteData, boolean isCancel)
    {

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fragment_history_detail_text_button_cancel :

                search.clearFocus();
                hideKeyPad();

                break;
            default :
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus)
    {
        if(view.getId() == R.id.fragment_history_detail_edit_search)
        {
            slideDistance = buttonCancel.getWidth();
            ObjectAnimator objectAnimator;

            if(hasFocus)
            {
                objectAnimator = ObjectAnimator.ofInt(layoutSearch, new WidthProperty(), layoutSearch.getWidth(), layoutSearch.getWidth() - slideDistance);
            }
            else
            {
                objectAnimator = ObjectAnimator.ofInt(layoutSearch, new WidthProperty(), layoutSearch.getWidth(), layoutSearch.getWidth() + slideDistance);
            }

            objectAnimator.setDuration(animationDuration);
            objectAnimator.start();


        }
    }

    private void hideKeyPad()
    {
        InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(search.getWindowToken(), 0);
    }


}