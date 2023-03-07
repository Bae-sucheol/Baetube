package com.example.baetube.dto;

public class SearchHistoryDTO
{
    private Integer userId;
    private String keywords;

    public SearchHistoryDTO()
    {
        super();
    }

    public SearchHistoryDTO(Integer userId, String keywords)
    {
        super();
        this.userId = userId;
        this.keywords = keywords;
    }

    public Integer getUserId()
    {
        return userId;
    }
    public String getKeywords()
    {
        return keywords;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }
    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
    }
}
