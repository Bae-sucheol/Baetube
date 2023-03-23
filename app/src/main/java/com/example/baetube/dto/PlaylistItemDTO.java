package com.example.baetube.dto;

public class PlaylistItemDTO
{
    private Integer playlistId;
    private Integer videoId;

    public PlaylistItemDTO()
    {
        super();
    }

    /**
     * @param playlistId
     * @param videoId
     */
    public PlaylistItemDTO(Integer playlistId, Integer videoId)
    {
        super();
        this.playlistId = playlistId;
        this.videoId = videoId;
    }

    // getter
    public Integer getPlaylistId()
    {
        return playlistId;
    }
    public Integer getVideoId()
    {
        return videoId;
    }

    // setter
    public void setPlaylistId(Integer playlistId)
    {
        this.playlistId = playlistId;
    }
    public void setVideoId(Integer videoId)
    {
        this.videoId = videoId;
    }
}
