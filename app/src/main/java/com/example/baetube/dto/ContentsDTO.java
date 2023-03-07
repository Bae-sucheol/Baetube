package com.example.baetube.dto;

public class ContentsDTO
{
    private Long contentsId;
    private Integer type;
    private Integer like;
    private Integer hate;
    private Integer replyCount;

    public ContentsDTO()
    {
        super();
    }

    public ContentsDTO(Long contentsId, Integer type, Integer like, Integer hate, Integer replyCount)
    {
        super();
        this.contentsId = contentsId;
        this.type = type;
        this.like = like;
        this.hate = hate;
        this.replyCount = replyCount;
    }

    public ContentsDTO(Long contentsId, Integer type)
    {
        super();
        this.contentsId = contentsId;
        this.type = type;
    }

    // getter
    public Long getContentsId()
    {
        return contentsId;
    }
    public Integer getType()
    {
        return type;
    }
    public Integer getLike()
    {
        return like;
    }
    public Integer getHate()
    {
        return hate;
    }
    public Integer getReplyCount()
    {
        return replyCount;
    }

    // setter
    public void setContentsId(Long contentsId)
    {
        this.contentsId = contentsId;
    }
    public void setType(Integer type)
    {
        this.type = type;
    }
    public void setLike(Integer like)
    {
        this.like = like;
    }
    public void setHate(Integer hate)
    {
        this.hate = hate;
    }
    public void setReplyCount(Integer replyCount)
    {
        this.replyCount = replyCount;
    }
}
