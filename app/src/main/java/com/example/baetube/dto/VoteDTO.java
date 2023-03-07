package com.example.baetube.dto;

public class VoteDTO
{
    private Integer voteId;
    private Integer communityId;
    private String title;
    private String comment;
    private Integer voteOptionId;
    private String option;
    private Integer count;

    // constructor
    public VoteDTO()
    {
        super();
    }

    public VoteDTO(Integer voteId, Integer communityId, String title, String comment, Integer voteOptionId, String option,
                Integer count)
    {
        super();
        this.voteId = voteId;
        this.communityId = communityId;
        this.title = title;
        this.comment = comment;
        this.voteOptionId = voteOptionId;
        this.option = option;
        this.count = count;
    }

    public VoteDTO(Integer communityId, String title, String comment)
    {
        super();
        this.communityId = communityId;
        this.title = title;
        this.comment = comment;
    }

    public VoteDTO(Integer voteId, Integer communityId, String title, String comment)
    {
        super();
        this.voteId = voteId;
        this.communityId = communityId;
        this.title = title;
        this.comment = comment;
    }

    public VoteDTO(Integer voteId, Integer voteOptionId, String option, Integer count)
    {
        super();
        this.voteId = voteId;
        this.voteOptionId = voteOptionId;
        this.option = option;
        this.count = count;
    }

    public VoteDTO(Integer voteId, String option)
    {
        this.voteId = voteId;
        this.option = option;
    }

    public VoteDTO(Integer voteId, Integer voteOptionId)
    {
        this.voteId = voteId;
        this.voteOptionId = voteOptionId;
    }

    // getter
    public Integer getVoteId()
    {
        return voteId;
    }
    public Integer getCommunityId()
    {
        return communityId;
    }
    public String getTitle()
    {
        return title;
    }
    public String getComment()
    {
        return comment;
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

    // setter
    public void setVoteId(Integer voteId)
    {
        this.voteId = voteId;
    }
    public void setCommunityId(Integer communityId)
    {
        this.communityId = communityId;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public void setComment(String comment)
    {
        this.comment = comment;
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
}
