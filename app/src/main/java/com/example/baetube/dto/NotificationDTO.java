package com.example.baetube.dto;

public class NotificationDTO
{
    private Integer userId;
    private Long contentsId;

    // constructor
    public NotificationDTO()
    {
        super();
    }

    public NotificationDTO(Integer userId, Long contentsId)
    {
        super();
        this.userId = userId;
        this.contentsId = contentsId;
    }

    // getter
    public Integer getUserId()
    {
        return userId;
    }
    public Long getContentsId()
    {
        return contentsId;
    }

    // setter
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }
    public void setContentsId(Long contentsId)
    {
        this.contentsId = contentsId;
    }
}
