package com.example.baetube.recyclerview.item;

import android.graphics.drawable.Drawable;

import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VideoDTO;

public class RecyclerViewVideoItem
{
    private ChannelDTO channelDTO;
    private VideoDTO videoDTO;
    private int viewType;

    // getter
    public ChannelDTO getChannelDTO()
    {
        return channelDTO;
    }

    public VideoDTO getVideoDTO()
    {
        return videoDTO;
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

    public void setVideoDTO(VideoDTO videoDTO)
    {
        this.videoDTO = videoDTO;
    }

    public void setViewType(int viewType)
    {
        this.viewType = viewType;
    }
}
