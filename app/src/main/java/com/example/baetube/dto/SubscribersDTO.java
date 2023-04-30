package com.example.baetube.dto;

import java.sql.Timestamp;

public class SubscribersDTO
{
    private Integer channelId;
    private Integer subscriberId;
    private Timestamp date;

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

    public SubscribersDTO(Integer channelId, Integer subscriberId, Timestamp date)
    {
        this.channelId = channelId;
        this.subscriberId = subscriberId;
        this.date = date;
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

    public Timestamp getDate()
    {
        return date;
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

    public void setDate(Timestamp date)
    {
        this.date = date;
    }
}
