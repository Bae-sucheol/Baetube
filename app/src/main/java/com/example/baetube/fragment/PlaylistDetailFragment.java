package com.example.baetube.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.OkHttpUtil;
import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.OnDialogInteractionListener;
import com.example.baetube.OnModifyListener;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.ViewType;
import com.example.baetube.bottomsheetdialog.PlaylistOptionManageFragment;
import com.example.baetube.bottomsheetdialog.VideoFragment;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.PlaylistDTO;
import com.example.baetube.dto.PlaylistItemDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.modify.ModifyPlaylistFragment;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewPlaylistItem;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDetailFragment extends Fragment implements OnRecyclerViewClickListener, View.OnClickListener
{
    private View view;

    private RecyclerView recyclerView;
    private RecyclerViewVideoAdapter recyclerViewVideoAdapter;
    private ArrayList<RecyclerViewVideoItem> list;

    private TextView playlistName;
    private TextView channelName;
    private TextView videoCount;
    private ImageView buttonEdit;
    private LinearLayout buttonPlay;
    private LinearLayout buttonShuffle;
    private RecyclerViewPlaylistItem recyclerViewPlaylistItem;

    private OnCallbackResponseListener onCallbackResponseListener;
    private OnDialogInteractionListener onDialogInteractionListener;
    private OnModifyListener onModifyListener;

    private OkHttpUtil okHttpUtil;

    public PlaylistDetailFragment(RecyclerViewPlaylistItem recyclerViewPlaylistItem , OnCallbackResponseListener onCallbackResponseListener)
    {
        this.recyclerViewPlaylistItem = recyclerViewPlaylistItem;
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_playlist_detail, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */
        recyclerView= view.findViewById(R.id.fragment_playlist_detail_recyclerview);
        list = new ArrayList<>();
        recyclerViewVideoAdapter = new RecyclerViewVideoAdapter(list);
        recyclerViewVideoAdapter.setOnRecyclerViewClickListener(this);
        recyclerView.setAdapter(recyclerViewVideoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        playlistName = view.findViewById(R.id.fragment_playlist_detail_text_name);
        channelName = view.findViewById(R.id.fragment_playlist_detail_text_channel_name);
        videoCount = view.findViewById(R.id.fragment_playlist_detail_text_video_count);
        buttonEdit = view.findViewById(R.id.fragment_playlist_detail_button_edit);
        buttonPlay = view.findViewById(R.id.fragment_playlist_detail_button_play);
        buttonShuffle = view.findViewById(R.id.fragment_playlist_detail_button_shuffle);

        playlistName.setText(recyclerViewPlaylistItem.getPlaylistDTO().getName());
        channelName.setText(recyclerViewPlaylistItem.getChannelDTO().getName());
        videoCount.setText(String.valueOf(recyclerViewPlaylistItem.getPlaylistDTO().getVideoCount()) + "개");

        buttonEdit.setOnClickListener(this);
        buttonPlay.setOnClickListener(this);
        buttonShuffle.setOnClickListener(this);

        onDialogInteractionListener = new OnDialogInteractionListener()
        {
            @Override
            public void onAddVoteResponse(String voteItem)
            {

            }

            @Override
            public void onDeletePlaylistItem(int position)
            {
                String url = "http://192.168.0.4:9090/Baetube_backEnd/api/playlist/item/delete";

                ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

                PlaylistItemDTO playlistItem = new PlaylistItemDTO(3, list.get(position).getVideoDTO().getVideoId());

                okHttpUtil.sendPostRequest(playlistItem, url, returnableCallback);

                list.remove(position);
                recyclerViewVideoAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onSetVideoResolution(int position)
            {

            }
        };

        onModifyListener = new OnModifyListener()
        {
            @Override
            public void OnModifyPlaylist(RecyclerViewPlaylistItem item)
            {
                recyclerViewPlaylistItem = item;

                playlistName.setText(recyclerViewPlaylistItem.getPlaylistDTO().getName());

                PlaylistDTO playlist = new PlaylistDTO();
                playlist.setName(recyclerViewPlaylistItem.getPlaylistDTO().getName());
                playlist.setVisible(recyclerViewPlaylistItem.getPlaylistDTO().getVisible());
                playlist.setThumbnail(recyclerViewPlaylistItem.getPlaylistDTO().getThumbnail());
                playlist.setPlaylistId(recyclerViewPlaylistItem.getPlaylistDTO().getPlaylistId());
                playlist.setChannelId(recyclerViewPlaylistItem.getChannelDTO().getChannelId());

                String url = "http://192.168.0.4:9090/Baetube_backEnd/api/playlist/update";

                ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

                okHttpUtil.sendPostRequest(playlist, url, returnableCallback);
            }

        };

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if(!list.isEmpty())
        {
            return;
        }

        okHttpUtil = new OkHttpUtil();

        // 지금은 테스트용으로 임의의 값을 넣는다.
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/video/playlist_video/3";

        ReturnableCallback mainVideoCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_PLAYLIST_VIDEO);

        okHttpUtil.sendGetRequest(url, mainVideoCallback);
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
                try
                {
                    getParentFragmentManager().popBackStack();
                }
                catch (IllegalStateException e)
                {

                }

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

                PlaylistOptionManageFragment playlistOptionManageFragment = new PlaylistOptionManageFragment(getContext(), position, onDialogInteractionListener);
                playlistOptionManageFragment.show(getParentFragmentManager(), playlistOptionManageFragment.getTag());

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

    public void setRecyclerViewVideo(List<VideoDTO> videoList, ArrayList<ChannelDTO> channelList)
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
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fragment_playlist_detail_button_edit :

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new ModifyPlaylistFragment(recyclerViewPlaylistItem , onModifyListener));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.fragment_playlist_detail_button_play :

                break;
            case R.id.fragment_playlist_detail_button_shuffle :

                break;
            default :
                break;
        }
    }
}