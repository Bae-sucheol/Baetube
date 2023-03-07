package com.example.baetube.recyclerview.item;

import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.NestedReplyDTO;

public class RecyclerViewNestedReplyItem
{
    private NestedReplyDTO nestedReplyDTO;
    private ChannelDTO channelDTO;

    // getter
    public NestedReplyDTO getNestedReplyDTO()
    {
        return nestedReplyDTO;
    }

    public ChannelDTO getChannelDTO()
    {
        return channelDTO;
    }

    // setter
    public void setNestedReplyDTO(NestedReplyDTO nestedReplyDTO)
    {
        this.nestedReplyDTO = nestedReplyDTO;
    }

    public void setChannelDTO(ChannelDTO channelDTO)
    {
        this.channelDTO = channelDTO;
    }


}
