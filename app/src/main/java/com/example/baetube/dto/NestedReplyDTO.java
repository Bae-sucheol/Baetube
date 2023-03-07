package com.example.baetube.dto;

import java.sql.Timestamp;

public class NestedReplyDTO
{
    private Integer nestedReplyId;
    private Integer replyId;
    private Long contentsId;
    private Integer channelId;
    private String comment;
    private Integer like;
    private Integer hate;
    private Timestamp date;
    private String name;
    private String profile;
    private Long attachedId;

    // constructor
    public NestedReplyDTO()
    {

    }

    public NestedReplyDTO(Integer nestedReplyId, Integer replyId, Long contentsId, Integer channelId, String comment,
                       Integer like, Integer hate, Timestamp date, String name, String profile, Long attachedId)
    {
        super();
        this.nestedReplyId = nestedReplyId;
        this.replyId = replyId;
        this.contentsId = contentsId;
        this.channelId = channelId;
        this.comment = comment;
        this.like = like;
        this.hate = hate;
        this.date = date;
        this.name = name;
        this.profile = profile;
        this.attachedId = attachedId;
    }

    public NestedReplyDTO(Integer nestedReplyId, Integer replyId, Long contentsId, Integer channelId, String comment,
                       Integer like, Integer hate, Timestamp date, String name, String profile)
    {
        super();
        this.nestedReplyId = nestedReplyId;
        this.replyId = replyId;
        this.contentsId = contentsId;
        this.channelId = channelId;
        this.comment = comment;
        this.like = like;
        this.hate = hate;
        this.date = date;
        this.name = name;
        this.profile = profile;
    }



    public NestedReplyDTO(Integer nestedReplyId, Integer replyId, Long contentsId, Integer channelId, String comment,
                       Timestamp date, String name, String profile)
    {
        super();
        this.nestedReplyId = nestedReplyId;
        this.replyId = replyId;
        this.contentsId = contentsId;
        this.channelId = channelId;
        this.comment = comment;
        this.date = date;
        this.name = name;
        this.profile = profile;
    }

    public NestedReplyDTO(Integer nestedReplyId, Integer replyId, Long contentsId, Integer channelId, String comment,
                       Timestamp date, String name, String profile, Long attachedId)
    {
        super();
        this.nestedReplyId = nestedReplyId;
        this.replyId = replyId;
        this.contentsId = contentsId;
        this.channelId = channelId;
        this.comment = comment;
        this.date = date;
        this.name = name;
        this.profile = profile;
        this.attachedId = attachedId;
    }

    public NestedReplyDTO(Integer replyId, Integer channelId, String comment, Timestamp date)
    {
        super();
        this.replyId = replyId;
        this.channelId = channelId;
        this.comment = comment;
        this.date = date;
    }

    public NestedReplyDTO(Integer replyId, Integer channelId, String comment, Long attachedId)
    {
        super();
        this.replyId = replyId;
        this.channelId = channelId;
        this.comment = comment;
        this.attachedId = attachedId;
    }

    // getter
    public Integer getNestedReplyId()
    {
        return nestedReplyId;
    }

    public Integer getReplyId()
    {
        return replyId;
    }

    public Long getContentsId()
    {
        return contentsId;
    }

    public Integer getChannelId()
    {
        return channelId;
    }

    public String getComment()
    {
        return comment;
    }

    public Timestamp getDate()
    {
        return date;
    }

    public String getName()
    {
        return name;
    }

    public String getProfile()
    {
        return profile;
    }

    public Integer getLike()
    {
        return like;
    }

    public Integer getHate()
    {
        return hate;
    }

    public Long getAttachedId()
    {
        return attachedId;
    }

    // setter
    public void setNestedReplyId(Integer nestedReplyId)
    {
        this.nestedReplyId = nestedReplyId;
    }

    public void setReplyId(Integer replyId)
    {
        this.replyId = replyId;
    }

    public void setContentsId(Long contentsId)
    {
        this.contentsId = contentsId;
    }

    public void setChannelId(Integer channelId)
    {
        this.channelId = channelId;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public void setDate(Timestamp date)
    {
        this.date = date;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setProfile(String profile)
    {
        this.profile = profile;
    }

    public void setLike(Integer like)
    {
        this.like = like;
    }

    public void setHate(Integer hate)
    {
        this.hate = hate;
    }

    public void setAttachedId(Long attachedId)
    {
        this.attachedId = attachedId;
    }
}
