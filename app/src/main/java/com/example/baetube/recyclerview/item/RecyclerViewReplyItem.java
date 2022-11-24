package com.example.baetube.recyclerview.item;

import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.CommunityDTO;
import com.example.baetube.dto.ReplyDTO;
import com.example.baetube.dto.UserDTO;
import com.example.baetube.dto.VideoDTO;

public class RecyclerViewReplyItem
{
    private ReplyDTO replyDTO;
    private ChannelDTO channelDTO;

    // getter
    public ReplyDTO getReplyDTO()
    {
        return replyDTO;
    }

    public ChannelDTO getChannelDTO()
    {
        return channelDTO;
    }

    // setter
    public void setReplyDTO(ReplyDTO replyDTO)
    {
        this.replyDTO = replyDTO;
    }

    public void setChannelDTO(ChannelDTO channelDTO)
    {
        this.channelDTO = channelDTO;
    }

}
