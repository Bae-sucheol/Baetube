package com.example.baetube.recyclerview.item;

import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.CommunityDTO;

import java.util.ArrayList;

public class RecyclerViewCommunityItem
{
    private CommunityDTO communityDTO;
    private ChannelDTO channelDTO;
    private ArrayList<RecyclerViewVoteItem> voteList;

    // getter
    public CommunityDTO getCommunityDTO()
    {
        return communityDTO;
    }

    public ChannelDTO getChannelDTO()
    {
        return channelDTO;
    }

    public ArrayList<RecyclerViewVoteItem> getVoteList()
    {
        return voteList;
    }

    // setter
    public void setCommunityDTO(CommunityDTO communityDTO)
    {
        this.communityDTO = communityDTO;
    }

    public void setChannelDTO(ChannelDTO channelDTO)
    {
        this.channelDTO = channelDTO;
    }

    public void setVoteList(ArrayList<RecyclerViewVoteItem> voteList)
    {
        this.voteList = voteList;
    }
}
