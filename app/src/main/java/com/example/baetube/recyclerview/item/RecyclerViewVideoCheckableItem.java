package com.example.baetube.recyclerview.item;

import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VideoDTO;

public class RecyclerViewVideoCheckableItem
{
    private ChannelDTO channelDTO;
    private VideoDTO videoDTO;
    private boolean isChecked;

    // constructor
    public RecyclerViewVideoCheckableItem(ChannelDTO channelDTO, VideoDTO videoDTO, boolean isChecked)
    {
        this.channelDTO = channelDTO;
        this.videoDTO = videoDTO;
        this.isChecked = isChecked;
    }

    // getter
    public ChannelDTO getChannelDTO()
    {
        return channelDTO;
    }

    public VideoDTO getVideoDTO()
    {
        return videoDTO;
    }

    public boolean isChecked()
    {
        return isChecked;
    }

    // setter
    public void setChannelDTO(ChannelDTO channelDTO)
    {
        this.channelDTO = channelDTO;
    }
    public void setVideoDTO(VideoDTO videoDTO)
    {
        this.videoDTO = videoDTO;
    }

    public void setChecked(boolean checked)
    {
        isChecked = checked;
    }
}
