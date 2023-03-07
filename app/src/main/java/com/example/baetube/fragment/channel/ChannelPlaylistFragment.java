package com.example.baetube.fragment.channel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.FragmentTagUtil;
import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.bottomsheetdialog.PlaylistOptionFragment;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.PlaylistDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.PlaylistDetailFragment;
import com.example.baetube.recyclerview.adapter.RecyclerViewPlaylistAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewPlaylistItem;

import java.util.ArrayList;
import java.util.List;

public class ChannelPlaylistFragment extends Fragment implements OnRecyclerViewClickListener
{

    private View view;

    private RecyclerView recyclerView;
    private RecyclerViewPlaylistAdapter recyclerViewPlaylistAdapter;
    private ArrayList<RecyclerViewPlaylistItem> list = new ArrayList<>();

    private OnCallbackResponseListener onCallbackResponseListener;

    public ChannelPlaylistFragment(OnCallbackResponseListener onCallbackResponseListener)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_channel_playlist, container, false);

        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */
        //test();

        recyclerView = view.findViewById(R.id.fragment_channel_playlist_recyclerview);
        recyclerViewPlaylistAdapter = new RecyclerViewPlaylistAdapter(list);
        recyclerViewPlaylistAdapter.setOnRecyclerViewClickListener(this);
        recyclerView.setAdapter(recyclerViewPlaylistAdapter);
        recyclerViewPlaylistAdapter.setOnRecyclerViewClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (view.getId())
        {
            case R.id.recyclerview_playlist_layout_main :

                FragmentManager fragmentManager = getParentFragment().getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.activity_main_layout, new PlaylistDetailFragment(list.get(position), onCallbackResponseListener),
                        FragmentTagUtil.FRAGMENT_TAG_PLAYLIST_DETAIL);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.recyclerview_playlist_image_option :

                /*
                 * 내 채널인지 아닌지 구분하여
                 * PlaylistOptionFragment, PlaylistOptionManageFragment 둘 중 하나를 사용한다.
                 */
                PlaylistOptionFragment playlistOptionFragment = new PlaylistOptionFragment(getContext());
                playlistOptionFragment.show(getParentFragmentManager(), playlistOptionFragment.getTag());

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

    public void setRecyclerViewPlaylist(List<PlaylistDTO> playlistList)
    {
        ChannelBaseFragment channelBaseFragment = (ChannelBaseFragment)getParentFragment();
        ChannelDTO channel = channelBaseFragment.getChannel();

        for(int i = 0; i < playlistList.size(); i++)
        {
            RecyclerViewPlaylistItem item = new RecyclerViewPlaylistItem();
            item.setPlaylistDTO(playlistList.get(i));
            item.setChannelDTO(channel);

            list.add(item);
        }

        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run()
            {
                recyclerViewPlaylistAdapter.notifyDataSetChanged();
            }
        });
    }

}