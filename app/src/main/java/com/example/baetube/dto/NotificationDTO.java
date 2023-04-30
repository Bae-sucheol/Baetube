package com.example.baetube.dto;

import java.sql.Timestamp;

public class NotificationDTO
{
    private Integer userId;
    private Long contentsId;
    private Timestamp date;
    private Integer checked;

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

    public NotificationDTO(Integer userId, Long contentsId, Timestamp date, Integer checked)
    {
        this.userId = userId;
        this.contentsId = contentsId;
        this.date = date;
        this.checked = checked;
    }

    public NotificationDTO(Integer userId, Long contentsId, Integer checked)
    {
        this.userId = userId;
        this.contentsId = contentsId;
        this.checked = checked;
    }

    public NotificationDTO(Integer userId, Long contentsId, Timestamp date)
    {
        this.userId = userId;
        this.contentsId = contentsId;
        this.date = date;
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
    public Timestamp getDate()
    {
        return date;
    }
    public Integer getChecked()
    {
        return checked;
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
    public void setDate(Timestamp date)
    {
        this.date = date;
    }
    public void setChecked(Integer checked)
    {
        this.checked = checked;
    }
}
