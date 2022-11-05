package com.example.baetube.dto;

public class PlaylistDTO
{
    private int playlist_id;
    private int video_count;
    private String name;
    private String thumnail;

    // getter
    public int getPlaylist_id()
    {
        return playlist_id;
    }

    public int getVideo_count()
    {
        return video_count;
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
    public void setPlaylist_id(int playlist_id)
    {
        this.playlist_id = playlist_id;
    }

    public void setVideo_count(int video_count)
    {
        this.video_count = video_count;
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
