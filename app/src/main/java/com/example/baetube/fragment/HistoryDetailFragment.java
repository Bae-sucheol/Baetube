package com.example.baetube.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.OkHttpUtil;
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

import java.util.ArrayList;
import java.util.List;

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

    private OkHttpUtil okHttpUtil;

    public HistoryDetailFragment(OnCallbackResponseListener onCallbackResponseListener)
    {
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
        list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.fragment_history_detail_recyclerview);
        adapter = new RecyclerViewVideoAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnRecyclerViewClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        search = view.findViewById(R.id.fragment_history_detail_edit_search);
        buttonCancel = view.findViewById(R.id.fragment_history_detail_text_button_cancel);
        layoutSearch = view.findViewById(R.id.fragment_history_detail_layout_search);

        search.setOnFocusChangeListener(this);
        buttonCancel.setOnClickListener(this);

        animationDuration = getContext().getResources().getInteger(R.integer.animation_duration_histort_detail);

        search.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                switch (i)
                {
                    case EditorInfo.IME_ACTION_SEARCH :

                        String keywords = textView.getText().toString().trim();

                        if(keywords == null || keywords.isEmpty())
                        {
                            Toast.makeText(getContext(), getString(R.string.toast_warning_keywords), Toast.LENGTH_SHORT);
                            return true;
                        }

                        requestHistoryVideo(keywords);
                        hideKeyPad();

                        break;
                }

                return true;
            }
        });

        requestHistoryVideo();

        // Inflate the layout for this fragment
        return view;
    }

    private void requestHistoryVideo()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_video_history);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_HISTORY_VIDEO);

        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    private void requestHistoryVideo(String keywords)
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_video_history) + "/" + keywords;

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_HISTORY_VIDEO);

        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    public void setHistoryList(List< VideoDTO > videoList, List< ChannelDTO > channelList)
    {
        if(!list.isEmpty())
        {
            list.clear();
        }

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
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu( Menu menu,  MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_sub, menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home :
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
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

                requestHistoryVideo();
                search.clearFocus();
                search.setText(null);
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