package com.example.baetube.recyclerview.item;

import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.PlaylistDTO;

public class RecyclerViewPlaylistItem
{
    private PlaylistDTO playlistDTO;
    private ChannelDTO channelDTO;

    // getter
    public PlaylistDTO getPlaylistDTO()
    {
        return playlistDTO;
    }

    public ChannelDTO getChannelDTO()
    {
        return channelDTO;
    }

    // setter
    public void setPlaylistDTO(PlaylistDTO playlistDTO)
    {
        this.playlistDTO = playlistDTO;
    }

    public void setChannelDTO(ChannelDTO channelDTO)
    {
        this.channelDTO = channelDTO;
    }

}
