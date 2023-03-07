package com.example.baetube.dto;

public class PlaylistDTO
{
    private Integer playlistId;
    private Integer channelId;
    private String name;
    private Integer visible;
    private Integer videoCount;
    private String thumbnail;

    // constructor
    public PlaylistDTO()
    {
        super();
    }

    public PlaylistDTO(Integer playlistId, Integer channelId, String name, Integer visible, Integer videoCount,
                    String thumbnail)
    {
        super();
        this.playlistId = playlistId;
        this.channelId = channelId;
        this.name = name;
        this.visible = visible;
        this.videoCount = videoCount;
        this.thumbnail = thumbnail;
    }

    public PlaylistDTO(Integer channelId, String name, Integer visible, Integer videoCount, String thumbnail)
    {
        super();
        this.channelId = channelId;
        this.name = name;
        this.visible = visible;
        this.videoCount = videoCount;
        this.thumbnail = thumbnail;
    }


    public PlaylistDTO(Integer channelId, String name, Integer visible, String thumbnail)
    {
        super();
        this.channelId = channelId;
        this.name = name;
        this.visible = visible;
        this.thumbnail = thumbnail;
    }

    // getter
    public Integer getPlaylistId()
    {
        return playlistId;
    }

    public Integer getChannelId()
    {
        return channelId;
    }

    public String getName()
    {
        return name;
    }

    public Integer getVisible()
    {
        return visible;
    }

    public Integer getVideoCount()
    {
        return videoCount;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }

    // setter
    public void setPlaylistId(Integer playlistId)
    {
        this.playlistId = playlistId;
    }

    public void setChannelId(Integer channelId)
    {
        this.channelId = channelId;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setVisible(Integer visible)
    {
        this.visible = visible;
    }

    public void setVideoCount(Integer videoCount)
    {
        this.videoCount = videoCount;
    }

    public void setThumbnail(String thumbnail)
    {
        this.thumbnail = thumbnail;
    }
}
