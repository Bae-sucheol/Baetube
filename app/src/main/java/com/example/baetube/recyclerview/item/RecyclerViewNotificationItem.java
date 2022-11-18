package com.example.baetube.recyclerview.item;

import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.CommunityDTO;
import com.example.baetube.dto.VideoDTO;

public class RecyclerViewNotificationItem
{
    private int notificationId;
    private ChannelDTO channelDTO;
    private VideoDTO videoDTO;
    private CommunityDTO communityDTO;
    private int viewType;
    private String divideString;

    // getter
    public int getNotificationId()
    {
        return notificationId;
    }

    public VideoDTO getVideoDTO()
    {
        return videoDTO;
    }

    public CommunityDTO getCommunityDTO()
    {
        return communityDTO;
    }

    public ChannelDTO getChannelDTO()
    {
        return channelDTO;
    }

    public int getViewType()
    {
        return viewType;
    }

    public String getDivideString()
    {
        return divideString;
    }

    // setter
    public void setNotificationId(int notificationId)
    {
        this.notificationId = notificationId;
    }

    public void setVideoDTO(VideoDTO videoDTO)
    {
        this.videoDTO = videoDTO;
    }

    public void setCommunityDTO(CommunityDTO communityDTO)
    {
        this.communityDTO = communityDTO;
    }

    public void setChannelDTO(ChannelDTO channelDTO)
    {
        this.channelDTO = channelDTO;
    }

    public void setViewType(int viewType)
    {
        this.viewType = viewType;
    }

    public void setDivideString(String divideString)
    {
        this.divideString = divideString;
    }
}
