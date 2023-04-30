package com.example.baetube.dto;

import java.sql.Timestamp;

public class CommunityDTO
{
    private Integer communityId;
    private Long contentsId;
    private Integer channelId;
    private Integer likeCount;
    private Integer hateCount;
    private Integer replyCount;
    private String imageUrl;
    private String comment;
    private Timestamp date;
    private Integer voteId;
    private String title;
    private Integer voteOptionId;
    private String option;
    private Integer count;
    private Integer selectedChannelId;
    private String name;
    private String profile;

    // constructor

    public CommunityDTO()
    {
        super();
    }

    public CommunityDTO(Integer communityId, Long contentsId, Integer channelId, Integer likeCount, Integer hateCount, Integer replyCount, String imageUrl, String comment, Timestamp date, Integer voteId, String title, Integer voteOptionId, String option, Integer count, Integer selectedChannelId, String name, String profile)
    {
        this.communityId = communityId;
        this.contentsId = contentsId;
        this.channelId = channelId;
        this.likeCount = likeCount;
        this.hateCount = hateCount;
        this.replyCount = replyCount;
        this.imageUrl = imageUrl;
        this.comment = comment;
        this.date = date;
        this.voteId = voteId;
        this.title = title;
        this.voteOptionId = voteOptionId;
        this.option = option;
        this.count = count;
        this.selectedChannelId = selectedChannelId;
        this.name = name;
        this.profile = profile;
    }

    public CommunityDTO(Integer communityId, Long contentsId, Integer channelId, Integer likeCount, Integer hateCount, Integer replyCount, String imageUrl, String comment, Timestamp date, Integer voteId, String title, Integer voteOptionId, String option, Integer count, Integer selectedChannelId, String name)
    {
        this.communityId = communityId;
        this.contentsId = contentsId;
        this.channelId = channelId;
        this.likeCount = likeCount;
        this.hateCount = hateCount;
        this.replyCount = replyCount;
        this.imageUrl = imageUrl;
        this.comment = comment;
        this.date = date;
        this.voteId = voteId;
        this.title = title;
        this.voteOptionId = voteOptionId;
        this.option = option;
        this.count = count;
        this.selectedChannelId = selectedChannelId;
        this.name = name;
    }

    public CommunityDTO(Integer communityId, Long contentsId, Integer channelId, Integer likeCount, Integer hateCount, Integer replyCount, String imageUrl, String comment, Timestamp date, Integer voteId, String title, Integer voteOptionId, String option, Integer count, Integer selectedChannelId)
    {
        this.communityId = communityId;
        this.contentsId = contentsId;
        this.channelId = channelId;
        this.likeCount = likeCount;
        this.hateCount = hateCount;
        this.replyCount = replyCount;
        this.imageUrl = imageUrl;
        this.comment = comment;
        this.date = date;
        this.voteId = voteId;
        this.title = title;
        this.voteOptionId = voteOptionId;
        this.option = option;
        this.count = count;
        this.selectedChannelId = selectedChannelId;
    }

    public CommunityDTO(Integer communityId, Long contentsId, Integer channelId, Integer likeCount, Integer hateCount,
                        Integer replyCount, String imageUrl, String comment, Timestamp date, Integer voteId, String title,
                        Integer voteOptionId, String option, Integer count)
    {
        super();
        this.communityId = communityId;
        this.contentsId = contentsId;
        this.channelId = channelId;
        this.likeCount = likeCount;
        this.hateCount = hateCount;
        this.replyCount = replyCount;
        this.imageUrl = imageUrl;
        this.comment = comment;
        this.date = date;
        this.voteId = voteId;
        this.title = title;
        this.voteOptionId = voteOptionId;
        this.option = option;
        this.count = count;
    }

    public CommunityDTO(Integer communityId, Long contentsId, Integer channelId, Integer likeCount,
                     Integer hateCount, Integer replyCount, String imageUrl, String comment, Timestamp date)
    {
        super();
        this.communityId = communityId;
        this.contentsId = contentsId;
        this.channelId = channelId;
        this.likeCount = likeCount;
        this.hateCount = hateCount;
        this.replyCount = replyCount;
        this.imageUrl = imageUrl;
        this.comment = comment;
        this.date = date;
    }

    public CommunityDTO(Integer channelId, String imageUrl, String comment, Timestamp date)
    {
        super();
        this.channelId = channelId;
        this.imageUrl = imageUrl;
        this.comment = comment;
        this.date = date;
    }



    // getter
    public Integer getCommunityId()
    {
        return communityId;
    }
    public Long getContentsId()
    {
        return contentsId;
    }
    public Integer getChannelId()
    {
        return channelId;
    }
    public Integer getLikeCount()
    {
        return likeCount;
    }
    public Integer getHateCount()
    {
        return hateCount;
    }
    public Integer getReplyCount()
    {
        return replyCount;
    }
    public String getImageUrl()
    {
        return imageUrl;
    }
    public String getComment()
    {
        return comment;
    }
    public Timestamp getDate()
    {
        return date;
    }


    public Integer getVoteId()
    {
        return voteId;
    }

    public String getTitle()
    {
        return title;
    }

    public Integer getVoteOptionId()
    {
        return voteOptionId;
    }

    public String getOption()
    {
        return option;
    }

    public Integer getCount()
    {
        return count;
    }

    public Integer getSelectedChannelId()
    {
        return selectedChannelId;
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
    public void setCommunityId(Integer communityId)
    {
        this.communityId = communityId;
    }
    public void setContentsId(Long contentsId)
    {
        this.contentsId = contentsId;
    }
    public void setChannelId(Integer channelId)
    {
        this.channelId = channelId;
    }
    public void setLikeCount(Integer likeCount)
    {
        this.likeCount = likeCount;
    }
    public void setHateCount(Integer hateCount)
    {
        this.hateCount = hateCount;
    }
    public void setReplyCount(Integer replyCount)
    {
        this.replyCount = replyCount;
    }
    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }
    public void setComment(String comment)
    {
        this.comment = comment;
    }
    public void setDate(Timestamp date)
    {
        this.date = date;
    }

    public void setVoteId(Integer voteId)
    {
        this.voteId = voteId;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setVoteOptionId(Integer voteOptionId)
    {
        this.voteOptionId = voteOptionId;
    }

    public void setOption(String option)
    {
        this.option = option;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }

    public void setSelectedChannelId(Integer selectedChannelId)
    {
        this.selectedChannelId = selectedChannelId;
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
