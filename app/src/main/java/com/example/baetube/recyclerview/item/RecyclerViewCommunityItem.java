package com.example.baetube.recyclerview.item;

import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.CommunityDTO;

public class RecyclerViewCommunityItem
{
    private CommunityDTO communityDTO;
    private ChannelDTO channelDTO;

    // getter
    public CommunityDTO getCommunityDTO()
    {
        return communityDTO;
    }

    public ChannelDTO getChannelDTO()
    {
        return channelDTO;
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
}
