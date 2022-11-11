package com.example.baetube.dto;

public class PlaylistDTO
{
    private int playlistId;
    private int videoCount;
    private String name;
    private String thumnail;

    // getter
    public int getPlaylistId()
    {
        return playlistId;
    }

    public int getVideoCount()
    {
        return videoCount;
    }

    public String getName()
    {
        return name;
    }

    public String getThumnail()
    {
        return thumnail;
    }

    // setter
    public void setPlaylistId(int playlistId)
    {
        this.playlistId = playlistId;
    }

    public void setVideoCount(int videoCount)
    {
        this.videoCount = videoCount;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setThumnail(String thumnail)
    {
        this.thumnail = thumnail;
    }
}
