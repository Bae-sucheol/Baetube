package com.example.baetube.dto;

public class RateDTO
{
    private Long contentsId;
    private Integer channelId;
    private Integer value;
    private Integer result;

    public RateDTO()
    {
        super();
    }

    public RateDTO(Long contentsId, Integer channelId, Integer value, Integer result)
    {
        super();
        this.contentsId = contentsId;
        this.channelId = channelId;
        this.value = value;
        this.result = result;
    }

    public RateDTO(Long contentsId, Integer value)
    {
        super();
        this.contentsId = contentsId;
        this.value = value;
    }

    public RateDTO(Long contentsId, Integer channelId, Integer value)
    {
        this.contentsId = contentsId;
        this.channelId = channelId;
        this.value = value;
    }

    // getter
    public Long getContentsId()
    {
        return contentsId;
    }
    public Integer getChannelId()
    {
        return channelId;
    }
    public Integer getValue()
    {
        return value;
    }
    public Integer getResult()
    {
        return result;
    }

    // setter
    public void setContentsId(Long contentsId)
    {
        this.contentsId = contentsId;
    }
    public void setChannelId(Integer channelId)
    {
        this.channelId = channelId;
    }
    public void setValue(Integer value)
    {
        this.value = value;
    }
    public void setResult(Integer result)
    {
        this.result = result;
    }

}
