package com.example.baetube.dto;

public class CommunityDTO
{
    private int communityId;
    private int channelId;
    private int voteId;
    private int likeCount;
    private int hateCount;
    private int replyCount;
    private String imageUrl;
    private String comment;
    private String date;

    // getter
    public int getCommunityId()
    {
        return communityId;
    }

    public int getChannelId()
    {
        return channelId;
    }

    public int getVoteId()
    {
        return voteId;
    }

    public int getLikeCount()
    {
        return likeCount;
    }

    public int getHateCount()
    {
        return hateCount;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public String getComment()
    {
        return comment;
    }

    public String getDate()
    {
        return date;
    }

    public int getReplyCount()
    {
        return replyCount;
    }

    // setter
    public void setCommunityId(int communityId)
    {
        this.communityId = communityId;
    }

    public void setChannelId(int channelId)
    {
        this.channelId = channelId;
    }

    public void setVoteId(int voteId)
    {
        this.voteId = voteId;
    }

    public void setLikeCount(int likeCount)
    {
        this.likeCount = likeCount;
    }

    public void setHateCount(int hateCount)
    {
        this.hateCount = hateCount;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setReplyCount(int replyCount)
    {
        this.replyCount = replyCount;
    }
}
