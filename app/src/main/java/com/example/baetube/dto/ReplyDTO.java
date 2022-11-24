package com.example.baetube.dto;

public class ReplyDTO
{
    private int replyId;
    private int userID;
    private int videoId;
    private int communityId;
    private String comment;
    private int like;
    private int hate;
    private String date;

    // getter
    public int getReplyId()
    {
        return replyId;
    }

    public int getUserID()
    {
        return userID;
    }

    public int getVideoId()
    {
        return videoId;
    }

    public int getCommunityId()
    {
        return communityId;
    }

    public String getComment()
    {
        return comment;
    }

    public int getLike()
    {
        return like;
    }

    public int getHate()
    {
        return hate;
    }

    public String getDate()
    {
        return date;
    }

    // setter
    public void setReplyId(int replyId)
    {
        this.replyId = replyId;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    public void setVideoId(int videoId)
    {
        this.videoId = videoId;
    }

    public void setCommunityId(int communityId)
    {
        this.communityId = communityId;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public void setLike(int like)
    {
        this.like = like;
    }

    public void setHate(int hate)
    {
        this.hate = hate;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

}
