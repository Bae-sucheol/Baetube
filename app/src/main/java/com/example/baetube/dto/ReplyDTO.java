package com.example.baetube.dto;

import java.sql.Timestamp;

public class ReplyDTO
{
    private Integer replyId;
    private Long contentsId;
    private Long attachedId;
    private Integer channelId;
    private String comment;
    private Integer like;
    private Integer hate;
    private Timestamp date;
    private Integer nestedCount;
    private String name;
    private String profile;

    // constructor
    public ReplyDTO()
    {
        super();
    }

    public ReplyDTO(Integer replyId, Long contentsId, Long attachedId, Integer channelId, String comment, Integer like,
                 Integer hate, Timestamp date, Integer nestedCount, String name, String profile)
    {
        super();
        this.replyId = replyId;
        this.contentsId = contentsId;
        this.attachedId = attachedId;
        this.channelId = channelId;
        this.comment = comment;
        this.like = like;
        this.hate = hate;
        this.date = date;
        this.nestedCount = nestedCount;
        this.name = name;
        this.profile = profile;
    }

    public ReplyDTO(Long attachedId, Integer channelId, String comment, Timestamp date)
    {
        super();
        this.attachedId = attachedId;
        this.channelId = channelId;
        this.comment = comment;
        this.date = date;
    }

    public ReplyDTO(Long attachedId, Integer channelId, String comment)
    {
        super();
        this.attachedId = attachedId;
        this.channelId = channelId;
        this.comment = comment;
    }

    // getter
    public Integer getReplyId()
    {
        return replyId;
    }
    public Long getContentsId()
    {
        return contentsId;
    }
    public Long getAttachedId()
    {
        return attachedId;
    }
    public Integer getChannelId()
    {
        return channelId;
    }
    public String getComment()
    {
        return comment;
    }
    public Integer getLike()
    {
        return like;
    }
    public Integer getHate()
    {
        return hate;
    }
    public Timestamp getDate()
    {
        return date;
    }
    public Integer getNestedCount()
    {
        return nestedCount;
    }
    public String getName()
    {
        return name;
    }
    public String getProfile()
    {
        return profile;
    }

    // setter
    public void setReplyId(Integer replyId)
    {
        this.replyId = replyId;
    }
    public void setContentsId(Long contentsId)
    {
        this.contentsId = contentsId;
    }
    public void setAttachedId(Long attachedId)
    {
        this.attachedId = attachedId;
    }
    public void setChannelId(Integer channelId)
    {
        this.channelId = channelId;
    }
    public void setComment(String comment)
    {
        this.comment = comment;
    }
    public void setLike(Integer like)
    {
        this.like = like;
    }
    public void setHate(Integer hate)
    {
        this.hate = hate;
    }
    public void setDate(Timestamp date)
    {
        this.date = date;
    }
    public void setNestedCount(Integer nestedCount)
    {
        this.nestedCount = nestedCount;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setProfile(String profile)
    {
        this.profile = profile;
    }

}
