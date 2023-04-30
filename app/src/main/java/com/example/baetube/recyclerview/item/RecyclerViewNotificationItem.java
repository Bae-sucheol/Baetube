package com.example.baetube.recyclerview.item;

import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.CommunityDTO;
import com.example.baetube.dto.NotificationDTO;
import com.example.baetube.dto.VideoDTO;

public class RecyclerViewNotificationItem
{
    private NotificationDTO notificationDTO;
    private ChannelDTO channelDTO;
    private VideoDTO videoDTO;
    private CommunityDTO communityDTO;
    private int viewType;
    private String divideString;

    public RecyclerViewNotificationItem(NotificationDTO notificationDTO, ChannelDTO channelDTO, VideoDTO videoDTO, int viewType)
    {
        this.notificationDTO = notificationDTO;
        this.channelDTO = channelDTO;
        this.videoDTO = videoDTO;
        this.viewType = viewType;
    }

    public RecyclerViewNotificationItem(NotificationDTO notificationDTO, ChannelDTO channelDTO, CommunityDTO communityDTO, int viewType)
    {
        this.notificationDTO = notificationDTO;
        this.channelDTO = channelDTO;
        this.communityDTO = communityDTO;
        this.viewType = viewType;
    }

    public RecyclerViewNotificationItem(int viewType, String divideString)
    {
        this.viewType = viewType;
        this.divideString = divideString;
    }

    // getter
    public NotificationDTO getNotificationDTO()
    {
        return notificationDTO;
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
    public void setNotificationDTO(NotificationDTO notificationDTO)
    {
        this.notificationDTO = notificationDTO;
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
