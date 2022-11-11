package com.example.baetube.dto;

import android.graphics.PorterDuff;

public class ChannelDTO
{
    private int channelId;
    private int userId;
    private int subs;
    private int videoCount;
    private String name;
    private String description;
    private String arts;
    private String regDate;

    // constructor
    public ChannelDTO()
    {

    }

    public ChannelDTO(int channelId, int userId, int subs, int videoCount, String name, String description, String arts, String regDate)
    {
        this.channelId = channelId;
        this.userId = userId;
        this.subs = subs;
        this.videoCount = videoCount;
        this.name = name;
        this.description = description;
        this.arts = arts;
        this.regDate = regDate;
    }

    // getter
    public int getChannelId()
    {
        return channelId;
    }

    public int getUserId()
    {
        return userId;
    }

    public int getSubs()
    {
        return subs;
    }

    public int getVideoCount()
    {
        return videoCount;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public String getArts()
    {
        return arts;
    }

    public String getRegDate()
    {
        return regDate;
    }

    // setter
    public void setChannelId(int channelId)
    {
        this.channelId = channelId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public void setSubs(int subs)
    {
        this.subs = subs;
    }

    public void setVideoCount(int videoCount)
    {
        this.videoCount = videoCount;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setArts(String arts)
    {
        this.arts = arts;
    }

    public void setRegDate(String regDate)
    {
        this.regDate = regDate;
    }

}
