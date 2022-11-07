package com.example.baetube.recyclerview.item;

import com.example.baetube.dto.ChannelDTO;

public class RecyclerViewSubscribeItem
{
    private ChannelDTO channelDTO;
    private int viewType;

    // getter
    public ChannelDTO getChannelDTO()
    {
        return channelDTO;
    }

    public int getViewType()
    {
        return viewType;
    }

    // setter
    public void setChannelDTO(ChannelDTO channelDTO)
    {
        this.channelDTO = channelDTO;
    }

    public void setViewType(int viewType)
    {
        this.viewType = viewType;
    }
}
