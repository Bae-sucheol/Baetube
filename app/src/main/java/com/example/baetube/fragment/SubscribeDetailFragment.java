package com.example.baetube.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
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
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.PreferenceManager;
import com.example.baetube.R;
import com.example.baetube.ViewType;
import com.example.baetube.dto.CategoryDTO;
import com.example.baetube.dto.SubscribersDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.channel.ChannelBaseFragment;
import com.example.baetube.recyclerview.adapter.RecyclerViewCategoryAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewSubscribeAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewSubscribeItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SubscribeDetailFragment extends Fragment implements OnRecyclerViewClickListener, View.OnClickListener
{
    private View view;

    private RecyclerView recyclerViewScribe;
    private RecyclerViewSubscribeAdapter recyclerViewSubscribeAdapter;
    private ArrayList<RecyclerViewSubscribeItem> subscribeList;
    private ArrayList<RecyclerViewSubscribeItem> selectedList;

    private int slideDistance = 0;
    private int animationDuration = 0;

    private TextView buttonDelete;
    private TextView buttonManage;
    private OkHttpUtil okHttpUtil;
    private OnCallbackResponseListener onCallbackResponseListener;

    private boolean isManageMode;

    private LinearLayoutManager linearLayoutManagerCategory;
    private RecyclerView recyclerViewCategory;
    private RecyclerViewCategoryAdapter recyclerViewCategoryAdapter;
    private ArrayList<CategoryDTO> listCategory;

    private Integer selectedPosition = 0;

    public SubscribeDetailFragment(ArrayList<RecyclerViewSubscribeItem> subscribeList , OnCallbackResponseListener onCallbackResponseListener)
    {
        this.subscribeList = subscribeList;
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_subscribe_detail, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        toolbar.setTitle(getString(R.string.fragment_subscribe_detail_toolbar_title));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        * 1. 리사이클러뷰 요소 찾기
        * 2. 리사이클러뷰 어댑터 객체 생성
        * 3. 리사이클러뷰 어댑터 설정
        * 4. 리사이클러뷰 레이아웃 매니저 설정
         */

        setSubscribeList();

        buttonDelete = view.findViewById(R.id.fragment_subscribe_clickable_text_delete_button);
        buttonManage = view.findViewById(R.id.fragment_subscribe_clickable_text_manage_button);

        recyclerViewScribe = view.findViewById(R.id.fragment_subscribe_detail_recyclerview);
        recyclerViewSubscribeAdapter = new RecyclerViewSubscribeAdapter(subscribeList);
        recyclerViewSubscribeAdapter.setOnRecyclerViewClickListener(this);
        recyclerViewScribe.setAdapter(recyclerViewSubscribeAdapter);
        recyclerViewScribe.setLayoutManager(new LinearLayoutManager(getContext()));

        buttonDelete.setOnClickListener(this);
        buttonManage.setOnClickListener(this);

        animationDuration = getContext().getResources().getInteger(R.integer.animation_duration_subscribe);
        slideDistance = (int) getContext().getResources().getDimension(R.dimen.width_subscribe_slide);

        selectedList = new ArrayList<>();

        // 리스트 초기화
        listCategory = new ArrayList<>();

        // 리사이클러뷰 요소 찾기
        recyclerViewCategory = view.findViewById(R.id.fragment_subscribe_detail_recyclerview_category);

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

    private void setSubscribeList()
    {
        for(RecyclerViewSubscribeItem item : subscribeList)
        {
            item.setViewType(ViewType.SUBSCRIBE_HORIZONTAL);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_none, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
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
            case R.id.recyclerview_subscribe_layout :

                if(isManageMode)
                {
                   return;
                }

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ChannelBaseFragment(getContext(), onCallbackResponseListener, subscribeList.get(position).getChannelDTO().getChannelId()), FragmentTagUtil.FRAGMENT_TAG_CHANNEL_BASE);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;

            case R.id.recyclerview_subscribe_layout_back :

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                        .setTitle("삭제 경고")
                        .setMessage("정말로 구독을 취소하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                // 삭제 처리
                                // 삭제 요청을 보낸다.
                                requestUnsubscribe(position);

                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                // 취소 처리
                            }
                        });

                builder.show();

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
                    Collections.sort(subscribeList,new Comparator<RecyclerViewSubscribeItem>()
                    {
                        @Override
                        public int compare(RecyclerViewSubscribeItem o1, RecyclerViewSubscribeItem o2)
                        {
                            // 최신순이기 때문에 date를 기준으로 정렬한다.
                            return o1.getChannelDTO().getRegDate().compareTo(o2.getChannelDTO().getRegDate());
                        }
                    });

                }
                else
                {

                    Collections.sort(subscribeList,new Comparator<RecyclerViewSubscribeItem>()
                    {
                        @Override
                        public int compare(RecyclerViewSubscribeItem o1, RecyclerViewSubscribeItem o2)
                        {
                            return o1.getChannelDTO().getName().compareTo(o2.getChannelDTO().getName());
                        }
                    });

                }

                selectedPosition = position;

                //item = list.get(selectedPosition);
                view = linearLayoutManagerCategory.findViewByPosition(selectedPosition);
                TextView category = view.findViewById(R.id.recyclerview_category_text_category);
                category.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.textview_rounded_rectangle_selected));
                category.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

                recyclerViewSubscribeAdapter.notifyDataSetChanged();

                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position)
    {
        if(!isManageMode)
        {
            return;
        }

        // 클릭한 아이템이 선택 리스트에 존재하는지 판별한다 (없으면 -1, 있으면 해당 인덱스 반환)
        //int index = selectedPositions.indexOf(position);
        int index = selectedList.indexOf(subscribeList.get(position));
        // 리스트에 없다면 애니메이션을 실행하고 해당 아이템을 선택 리스트에 추가
        if(index == -1)
        {
            TranslateAnimation translateAnimation = new TranslateAnimation(0, -slideDistance, 0, 0);

            translateAnimation.setDuration(animationDuration);
            translateAnimation.setFillAfter(true);

            view.findViewById(R.id.recyclerview_subscribe_layout_front).startAnimation(translateAnimation);
            view.findViewById(R.id.recyclerview_subscribe_layout_back).bringToFront();

            selectedList.add(subscribeList.get(position));
            //selectedPositions.add(position);
        }
        else // 리스트에 이미 존재한다면 애니메이션을 실행하고 해당 아이템을 선택 리스트에서 제거
        {
            TranslateAnimation translateAnimation = new TranslateAnimation(-slideDistance, 0, 0, 0);

            translateAnimation.setDuration(animationDuration);
            translateAnimation.setFillAfter(true);

            view.findViewById(R.id.recyclerview_subscribe_layout_front).startAnimation(translateAnimation);
            view.findViewById(R.id.recyclerview_subscribe_layout_front).bringToFront();

            selectedList.remove(index);
            //selectedPositions.remove(position);
        }
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
            case R.id.fragment_subscribe_clickable_text_delete_button :

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                        .setTitle("삭제 경고")
                        .setMessage("정말로 구독을 취소하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                // 삭제 처리
                                // 삭제 요청을 보낸다.
                                requestUnsubscribe();

                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                // 취소 처리
                            }
                        });

                builder.show();

                break;
            case R.id.fragment_subscribe_clickable_text_manage_button :

                isManageMode = !isManageMode;

                if(isManageMode)
                {
                    buttonManage.setText(getString(R.string.subscribe_back));
                    buttonDelete.setVisibility(View.VISIBLE);
                }
                else
                {
                    buttonManage.setText(getString(R.string.subscribe_manage));
                    buttonDelete.setVisibility(View.GONE);
                }

                break;
        }

    }

    // 복수 구독취소 요청
    private void requestUnsubscribe()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_unsubscribe) + PreferenceManager.getString(getContext().getApplicationContext(), PreferenceManager.PREFERENCES_CHANNEL_SEQUENCE);

        List<SubscribersDTO> subscribers = new ArrayList<>();

        for (int i = 0; i < selectedList.size(); i++)
        {
            SubscribersDTO subscriber = new SubscribersDTO();
            subscriber.setChannelId(selectedList.get(i).getChannelDTO().getChannelId());

            subscribers.add(subscriber);
        }

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_UNSUBSCRIBE);

        okHttpUtil.sendPostRequest(subscribers, url, returnableCallback);

        // 리사이클러뷰 아이템에서 선택된 아이템을 삭제하고 리사이클러뷰 어뎁터를 갱신한다.
        for (int i = 0; i < selectedList.size(); i++)
        {
            int index = subscribeList.indexOf(selectedList.get(i));
            subscribeList.remove(index);
            recyclerViewSubscribeAdapter.notifyItemRemoved(index);
        }

        selectedList.clear();

    }

    // 단일 구독취소 요청
    private void requestUnsubscribe(int position)
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_unsubscribe) + PreferenceManager.getString(getContext().getApplicationContext(), PreferenceManager.PREFERENCES_CHANNEL_SEQUENCE);;

        List<SubscribersDTO> subscribers = new ArrayList<>();

        SubscribersDTO subscriber = new SubscribersDTO();
        subscriber.setChannelId(subscribeList.get(position).getChannelDTO().getChannelId());
        subscribers.add(subscriber);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_UNSUBSCRIBE);

        okHttpUtil.sendPostRequest(subscribers, url, returnableCallback);

        // 리사이클러뷰 아이템에서 선택된 아이템을 삭제하고 리사이클러뷰 어뎁터를 갱신한다.
        selectedList.remove(subscribeList.get(position));
        subscribeList.remove(position);
        recyclerViewSubscribeAdapter.notifyItemRemoved(position);

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