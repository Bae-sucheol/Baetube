package com.example.baetube.fragment.channel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.OkHttpUtil;
import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.bottomsheetdialog.CommunityOptionFragment;
import com.example.baetube.bottomsheetdialog.ReplyFragment;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.CommunityDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewCommunityAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewCommunityItem;
import com.example.baetube.recyclerview.item.RecyclerViewVoteItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChannelCommunityFragment extends Fragment implements OnRecyclerViewClickListener
{
    private View view;

    private RecyclerView recyclerView;
    private RecyclerViewCommunityAdapter recyclerViewCommunityAdapter;
    private ArrayList<RecyclerViewCommunityItem> list = new ArrayList<>();

    private OnCallbackResponseListener onCallbackResponseListener;

    private OkHttpUtil okHttpUtil;

    public ChannelCommunityFragment(OnCallbackResponseListener onCallbackResponseListener)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_channel_community, container, false);

        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */
        //test();

        recyclerView = view.findViewById(R.id.fragment_channel_community_recyclerview);
        recyclerViewCommunityAdapter = new RecyclerViewCommunityAdapter(list);
        recyclerViewCommunityAdapter.setOnRecyclerViewClickListener(this);
        recyclerView.setAdapter(recyclerViewCommunityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onItemClick(View view, int position)
    {

        switch (view.getId())
        {
            case R.id.recyclerview_community_image_option :

                CommunityOptionFragment communityOptionFragment = new CommunityOptionFragment(getContext());
                communityOptionFragment.show(getParentFragmentManager(), communityOptionFragment.getTag());

                break;
            case R.id.recyclerview_community_image_like :

                // 좋아요 기능

                break;
            case R.id.recyclerview_community_image_hate :

                // 싫어요 기능

                break;
            case R.id.recyclerview_community_image_reply :

                // 댓글 출력.
                ReplyFragment replyFragment = new ReplyFragment();
                replyFragment.show(getParentFragmentManager(), replyFragment.getTag());

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
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        // 임시로 사용.
        voteData.setCommunityId(4);

        System.out.println("channelId : " + voteData.getCommunityId());
        System.out.println("voteId : " + voteData.getVoteId());
        System.out.println("voteOptionId : " + voteData.getVoteOptionId());

        // 임시로 하드코딩.
        String url;

        if(isCancel)
        {
            url = "http://192.168.0.4:9090/Baetube_backEnd/api/vote/cancel";
        }
        else
        {
            url = "http://192.168.0.4:9090/Baetube_backEnd/api/vote/cast";
        }


        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(voteData, url, returnableCallback);
    }

    public void setRecyclerViewCommunity(List<CommunityDTO> communityList)
    {
        ChannelBaseFragment channelBaseFragment = (ChannelBaseFragment)getParentFragment();
        ChannelDTO channel = channelBaseFragment.getChannel();

        HashMap<Integer, Integer> hashMap = new HashMap<>();

        for(int i = 0; i < communityList.size(); i++)
        {
            RecyclerViewCommunityItem item = new RecyclerViewCommunityItem();

            // 이미 같은 커뮤니티id가 존재한다면. 투표가 있다고 가정하고
            // 기존에 들어있는 아이템의 투표리스트에 투표 정보를 주입해서 삽입한다.
            if(hashMap.containsKey(communityList.get(i).getCommunityId()))
            {
                RecyclerViewCommunityItem preItem = list.get(hashMap.get(communityList.get(i).getCommunityId()));
                ArrayList<RecyclerViewVoteItem> voteList = preItem.getVoteList();

                CommunityDTO community = communityList.get(i);
                VoteDTO vote = new VoteDTO();

                vote.setVoteId(community.getVoteId());
                vote.setCommunityId(community.getCommunityId());
                vote.setVoteOptionId(community.getVoteOptionId());
                vote.setOption(community.getOption());
                vote.setTitle(community.getTitle());
                vote.setCount(community.getCount());

                if(community.getSelectedChannelId() == null)
                {
                    voteList.add(new RecyclerViewVoteItem(vote, false));
                }
                else
                {
                    voteList.add(new RecyclerViewVoteItem(vote, true));
                }
            }
            else // 같은 커뮤니티id가 존재하지 않는다면 그냥 삽입한다.
            {
                CommunityDTO community = communityList.get(i);

                hashMap.put(community.getCommunityId(), list.size());
                item.setCommunityDTO(community);
                item.setChannelDTO(channel);

                ArrayList<RecyclerViewVoteItem> voteList = new ArrayList<>();

                if (community.getVoteId() != null)
                {
                    VoteDTO vote = new VoteDTO();

                    vote.setVoteId(community.getVoteId());
                    vote.setCommunityId(community.getCommunityId());
                    vote.setVoteOptionId(community.getVoteOptionId());
                    vote.setOption(community.getOption());
                    vote.setTitle(community.getTitle());
                    vote.setCount(community.getCount());

                    if(community.getSelectedChannelId() == null)
                    {
                        voteList.add(new RecyclerViewVoteItem(vote, false));
                    }
                    else
                    {
                        voteList.add(new RecyclerViewVoteItem(vote, true));
                    }
                }

                item.setVoteList(voteList);

                list.add(item);
            }

        }

        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run()
            {
                recyclerViewCommunityAdapter.notifyDataSetChanged();
            }
        });
    }

    /*
    public void test()
    {
        String channel_names[] = {"홍길동", "이순신", "장영실", "김유신", "허준"};
        String comments[] = {"테스트1", "테스트2", "테스트3", "테스트4", "테스트5"};

        for(int i = 0; i < 5; i++)
        {
            RecyclerViewCommunityItem item = new RecyclerViewCommunityItem();

            CommunityDTO communityDTO = new CommunityDTO();
            ChannelDTO channelDTO = new ChannelDTO();

            communityDTO.setComment(comments[i]);
            communityDTO.setDate("1시간 전");
            communityDTO.setLikeCount(100);
            communityDTO.setHateCount(100);
            communityDTO.setReplyCount(100);
            channelDTO.setName(channel_names[i]);

            item.setCommunityDTO(communityDTO);
            item.setChannelDTO(channelDTO);

            list.add(item);
        }

    }

     */
}