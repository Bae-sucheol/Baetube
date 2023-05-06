package com.example.baetube.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.baetube.OnDialogInteractionListener;
import com.example.baetube.OnFragmentInteractionListener;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.bottomsheetdialog.NotificationOptionFragment;
import com.example.baetube.dto.CategoryDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.channel.ChannelBaseFragment;
import com.example.baetube.recyclerview.adapter.RecyclerViewCategoryAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewNotificationAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewNotificationItem;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.util.ArrayList;

public class NotificationFragment extends Fragment implements OnRecyclerViewClickListener
{
    private View view;

    private RecyclerView recyclerView;
    private RecyclerViewNotificationAdapter recyclerViewNotificationAdapter;
    private ArrayList<RecyclerViewNotificationItem> listVideo;
    private ArrayList<RecyclerViewNotificationItem> listCommunity;

    private LinearLayoutManager linearLayoutManagerCategory;

    private RecyclerView recyclerViewCategory;
    private RecyclerViewCategoryAdapter recyclerViewCategoryAdapter;
    private ArrayList<CategoryDTO> listCategory;

    private OnCallbackResponseListener onCallbackResponseListener;

    private Integer selectedPosition;

    private OkHttpUtil okHttpUtil;

    private OnFragmentInteractionListener onFragmentInteractionListener;
    private OnDialogInteractionListener onDialogInteractionListener;

    private int selectedNotificaiton;

    public NotificationFragment(OnCallbackResponseListener onCallbackResponseListener)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_notification, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar.setTitle(getString(R.string.menu_toolbar_notification));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */

        recyclerView = view.findViewById(R.id.fragment_notification_recyclerview);
        recyclerViewNotificationAdapter = new RecyclerViewNotificationAdapter(null);
        recyclerView.setAdapter(recyclerViewNotificationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewNotificationAdapter.setOnRecyclerViewClickListener(this);

        selectedPosition = 0;

        // 리스트 초기화
        listCategory = new ArrayList<>();

        // 리사이클러뷰 요소 찾기
        recyclerViewCategory = view.findViewById(R.id.fragment_notification_recyclerview_category);

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

        requestVideoNotification();

        requestCheckNotifications();

        onFragmentInteractionListener = (OnFragmentInteractionListener) getContext();

        setDialogInteractionListener();

        // Inflate the layout for this fragment
        return view;
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

            case R.id.menu_toolbar_search:
                //
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void requestCheckNotifications()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_notification_check);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    private void requestVideoNotification()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_notification_video_select);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_VIDEO_NOTIFICATION);

        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    private void requestCommunityNotification()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_notification_community_select);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_COMMUNITY_NOTIFICATION);

        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    public void setListVideo(ArrayList<RecyclerViewNotificationItem> notificationVideoItemList)
    {
        if(listVideo != null)
        {
            return;
        }

        listVideo = notificationVideoItemList;

        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                recyclerViewNotificationAdapter.setList(listVideo);
                recyclerViewNotificationAdapter.notifyDataSetChanged();
            }
        });
    }

    public void setListCommunity(ArrayList<RecyclerViewNotificationItem> notificationCommunityItemList)
    {
        if(listCommunity != null)
        {
            return;
        }

        listCommunity = notificationCommunityItemList;

        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                recyclerViewNotificationAdapter.setList(listCommunity);
                recyclerViewNotificationAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setCategory()
    {
        String categories[] = getResources().getStringArray(R.array.category_notification);

        for (int i = 0; i < categories.length; i++)
        {
            CategoryDTO category = new CategoryDTO(i, categories[i]);
            listCategory.add(category);
        }

        recyclerViewCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (view.getId())
        {
            case R.id.recyclerview_notification_image_profile :

                Integer channelId;

                // 동영상 알림이라면
                if(selectedPosition == 0)
                {
                    channelId = listVideo.get(position).getChannelDTO().getChannelId();
                }
                else // 게시글 알림이라면
                {
                    channelId = listCommunity.get(position).getChannelDTO().getChannelId();
                }

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ChannelBaseFragment(getContext(), onCallbackResponseListener, channelId), FragmentTagUtil.FRAGMENT_TAG_CHANNEL_BASE);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.recyclerview_notification_image_thumbnail :

            case R.id.recyclerview_notification_text_title :

            case R.id.recyclerview_notification_text_date :

                // 동영상 알림이라면
                if(selectedPosition == 0)
                {
                    RecyclerViewVideoItem videoItem = new RecyclerViewVideoItem();
                    videoItem.setVideoDTO(listVideo.get(position).getVideoDTO());
                    videoItem.setChannelDTO(listVideo.get(position).getChannelDTO());
                    onFragmentInteractionListener.onVideoItemClick(videoItem);
                }
                else // 게시글 알림이라면
                {
                    fragmentManager = getParentFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.activity_main_layout, new ChannelBaseFragment(getContext(), onCallbackResponseListener, listCommunity.get(position).getChannelDTO().getChannelId()), FragmentTagUtil.FRAGMENT_TAG_CHANNEL_BASE);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }

                break;
            case R.id.recyclerview_notification_image_option :

                selectedNotificaiton = position;

                NotificationOptionFragment notificationOptionFragment = new NotificationOptionFragment(getContext(), onDialogInteractionListener);
                notificationOptionFragment.show(getParentFragmentManager(), notificationOptionFragment.getTag());

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
                    recyclerViewNotificationAdapter.setList(listVideo);
                    recyclerViewNotificationAdapter.notifyDataSetChanged();
                }
                else
                {
                    if(listCommunity == null)
                    {
                        requestCommunityNotification();
                    }
                    else
                    {
                        recyclerViewNotificationAdapter.setList(listCommunity);
                        recyclerViewNotificationAdapter.notifyDataSetChanged();
                    }
                }


                selectedPosition = position;

                view = linearLayoutManagerCategory.findViewByPosition(selectedPosition);
                TextView category = view.findViewById(R.id.recyclerview_category_text_category);
                category.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.textview_rounded_rectangle_selected));
                category.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

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

    private void setDialogInteractionListener()
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
                // 해당 리스트에서 아이템을 삭제한다.

                // 동영상인 경우.
                if(selectedPosition == 0)
                {
                    listVideo.remove(selectedNotificaiton);
                }
                else // 게시글인 경우
                {
                    listCommunity.remove(selectedNotificaiton);
                }

                recyclerViewNotificationAdapter.notifyItemRemoved(selectedNotificaiton);

                // 서버에 해당 알림 삭제를 요청한다.
                if(okHttpUtil == null)
                {
                    okHttpUtil = new OkHttpUtil();
                }

                String url = getString(R.string.api_url_notification_delete);

                ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

                // 동영상인 경우.
                if(selectedPosition == 0)
                {
                    okHttpUtil.sendPostRequest(listVideo.get(selectedNotificaiton).getNotificationDTO().getContentsId(), url, returnableCallback);
                }
                else // 게시글인 경우
                {
                    okHttpUtil.sendPostRequest(listCommunity.get(selectedNotificaiton).getNotificationDTO().getContentsId(), url, returnableCallback);
                }
            }

            @Override
            public void onSelectChannel(int position, int channelId)
            {

            }
        };
    }
}