package com.example.baetube.dto;

import android.graphics.PorterDuff;

public class ChannelDTO
{
    private int channel_id;
    private int user_id;
    private int subs;
    private int video_count;
    private String name;
    private String description;
    private String arts;
    private String reg_date;

    // constructor
    public ChannelDTO()
    {

    }

    public ChannelDTO(int channel_id, int user_id, int subs, int video_count, String name, String description, String arts, String reg_date)
    {
        this.channel_id = channel_id;
        this.user_id = user_id;
        this.subs = subs;
        this.video_count = video_count;
        this.name = name;
        this.description = description;
        this.arts = arts;
        this.reg_date = reg_date;
    }

    // getter
    public int getChannel_id()
    {
        return channel_id;
    }

    public int getUser_id()
    {
        return user_id;
    }

    public int getSubs()
    {
        return subs;
    }

    public int getVideo_count()
    {
        return video_count;
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

    public String getReg_date()
    {
        return reg_date;
    }

    // setter
    public void setChannel_id(int channel_id)
    {
        this.channel_id = channel_id;
    }

    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }

    public void setSubs(int subs)
    {
        this.subs = subs;
    }

    public void setVideo_count(int video_count)
    {
        this.video_count = video_count;
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

    public void setReg_date(String reg_date)
    {
        this.reg_date = reg_date;
    }

}
