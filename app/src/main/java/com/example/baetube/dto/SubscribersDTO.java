package com.example.baetube.dto;

public class SubscribersDTO
{
    private Integer channelId;
    private Integer subscriberId;

    public SubscribersDTO()
    {
        super();
    }

    public SubscribersDTO(Integer channelId, Integer subscriberId)
    {
        super();
        this.channelId = channelId;
        this.subscriberId = subscriberId;
    }

    // getter
    public Integer getChannelId()
    {
        return channelId;
    }

    public Integer getSubscriberId()
    {
        return subscriberId;
    }

    // setter
    public void setChannelId(Integer channelId)
    {
        this.channelId = channelId;
    }

    public void setSubscriberId(Integer subscriberId)
    {
        this.subscriberId = subscriberId;
    }
}
