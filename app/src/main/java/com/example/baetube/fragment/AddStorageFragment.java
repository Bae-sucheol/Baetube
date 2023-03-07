package com.example.baetube.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.OkHttpUtil;
import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.OnDialogInteractionListener;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.bottomsheetdialog.AddStorageDialogFragment;
import com.example.baetube.dto.PlaylistDTO;
import com.example.baetube.dto.PlaylistItemDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoCheckableAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewVideoCheckableItem;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class AddStorageFragment extends Fragment implements OnRecyclerViewClickListener
{
    private View view;

    private RecyclerView recyclerView;
    private RecyclerViewVideoCheckableAdapter adapter;
    private ArrayList<RecyclerViewVideoCheckableItem> list;
    private HashSet<Integer> checkedItems;

    private EditText search;
    private TextView buttonCancel;

    private OnCallbackResponseListener onCallbackResponseListener;
    private OnDialogInteractionListener onDialogInteractionListener;

    private OkHttpUtil okHttpUtil;

    public AddStorageFragment(ArrayList<RecyclerViewVideoItem> list, OnCallbackResponseListener onCallbackResponseListener)
    {
        makeCheckableList(list);
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_add_storage, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        toolbar.setTitle(getString(R.string.fragment_upload_video_list_toolbar_title));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */

        recyclerView = view.findViewById(R.id.fragment_add_storage_recyclerview);
        adapter = new RecyclerViewVideoCheckableAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnRecyclerViewClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        checkedItems = new HashSet<>();

        onDialogInteractionListener = new OnDialogInteractionListener()
        {
            @Override
            public void onAddVoteResponse(String playlistTitle)
            {
                // 여기에 재생목록 업로드 요청.
                System.out.println("재생목록 업로드 요청");

                if(okHttpUtil == null)
                {
                    okHttpUtil = new OkHttpUtil();
                }

                PlaylistDTO playlist = new PlaylistDTO();
                playlist.setChannelId(4);
                playlist.setName(playlistTitle);
                playlist.setVisible(1);

                if(!checkedItems.isEmpty())
                {
                    playlist.setThumbnail(list.get(checkedItems.iterator().next()).getVideoDTO().getThumbnail());
                }

                String url = "http://192.168.0.4:9090/Baetube_backEnd/api/playlist/insert";

                ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_INSERT);

                okHttpUtil.sendPostRequest(playlist , url, returnableCallback);
            }

            @Override
            public void onDeletePlaylistItem(int position)
            {

            }

        };

        // Inflate the layout for this fragment
        return view;
    }

    public void requestInsertPlaylistItems(Integer playlistId)
    {
        System.out.println("생성된 playlistId : " + playlistId);

        if(checkedItems.isEmpty())
        {
            return;
        }

        List<PlaylistItemDTO> playlistItems = createPlaylistItemList(playlistId);

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/playlist/item/insert";

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(playlistItems, url, returnableCallback);
    }

    private List<PlaylistItemDTO> createPlaylistItemList(Integer playlistId)
    {
        ArrayList<PlaylistItemDTO> playlistItems = new ArrayList<>();

        Iterator<Integer> iterator = checkedItems.iterator();

        while(iterator.hasNext())
        {
            Integer next = iterator.next();

            Integer videoId = list.get(next).getVideoDTO().getVideoId();

            PlaylistItemDTO item = new PlaylistItemDTO();

            item.setPlaylistId(playlistId);
            item.setVideoId(videoId);

            playlistItems.add(item);
        }

        return playlistItems;
    }

    private void makeCheckableList(ArrayList<RecyclerViewVideoItem> oldList)
    {
        if(oldList == null)
        {
            return;
        }

        list = new ArrayList<>();

        for (RecyclerViewVideoItem item : oldList)
        {
            RecyclerViewVideoCheckableItem newItem = new RecyclerViewVideoCheckableItem(item.getChannelDTO(), item.getVideoDTO(), false);
            list.add(newItem);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_only_next_button, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home :

                getActivity().onBackPressed();

                break;

            case R.id.menu_toolbar_next :

                AddStorageDialogFragment addStorageDialogFragment = new AddStorageDialogFragment(onDialogInteractionListener);
                addStorageDialogFragment.show(getParentFragmentManager(), addStorageDialogFragment.getTag());

                break;

            default :
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (view.getId())
        {
            case R.id.recyclerview_video_checkable_layout :

                RecyclerViewVideoCheckableItem item = list.get(position);
                item.setChecked(!item.isChecked());

                if(item.isChecked())
                {
                    checkedItems.add(position);
                }
                else
                {
                    checkedItems.remove(position);
                }

                adapter.notifyItemChanged(position);

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
}