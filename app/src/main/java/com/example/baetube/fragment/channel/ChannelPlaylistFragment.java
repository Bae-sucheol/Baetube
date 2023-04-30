package com.example.baetube.fragment.channel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
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
import com.example.baetube.dto.CategoryDTO;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.PlaylistDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.PlaylistDetailFragment;
import com.example.baetube.recyclerview.adapter.RecyclerViewCategoryAdapter;
import com.example.baetube.recyclerview.adapter.RecyclerViewPlaylistAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewPlaylistItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChannelPlaylistFragment extends Fragment implements OnRecyclerViewClickListener
{

    private View view;

    private RecyclerView recyclerView;
    private RecyclerViewPlaylistAdapter recyclerViewPlaylistAdapter;
    private ArrayList<RecyclerViewPlaylistItem> list = new ArrayList<>();

    private OnCallbackResponseListener onCallbackResponseListener;

    private LinearLayoutManager linearLayoutManagerCategory;
    private RecyclerView recyclerViewCategory;
    private RecyclerViewCategoryAdapter recyclerViewCategoryAdapter;
    private ArrayList<CategoryDTO> listCategory;

    private Integer selectedPosition = 0;

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

        // 리스트 초기화
        listCategory = new ArrayList<>();

        // 리사이클러뷰 요소 찾기
        recyclerViewCategory = view.findViewById(R.id.fragment_channel_playlist_recyclerview_category);

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
                    Collections.sort(list, new Comparator<RecyclerViewPlaylistItem>()
                    {
                        @Override
                        public int compare(RecyclerViewPlaylistItem o1, RecyclerViewPlaylistItem o2)
                        {
                            // 최신순이기 때문에 videoId 기준으로 오름차순 정렬하면 된다.
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

                }
                else
                {

                    Collections.sort(list, new Comparator<RecyclerViewPlaylistItem>()
                    {
                        @Override
                        public int compare(RecyclerViewPlaylistItem o1, RecyclerViewPlaylistItem o2)
                        {
                            return o1.getPlaylistDTO().getName().compareTo(o2.getPlaylistDTO().getName());
                        }
                    });

                }

                selectedPosition = position;

                //item = list.get(selectedPosition);
                view = linearLayoutManagerCategory.findViewByPosition(selectedPosition);
                TextView category = view.findViewById(R.id.recyclerview_category_text_category);
                category.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.textview_rounded_rectangle_selected));
                category.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

                recyclerViewPlaylistAdapter.notifyDataSetChanged();

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