package com.example.baetube.dto;

public class VoteResultDTO
{
    private VoteDTO voteDTO;
    private boolean isCancel;

    public VoteResultDTO()
    {
    }

    public VoteResultDTO(VoteDTO voteDTO, boolean isCancel)
    {
        this.voteDTO = voteDTO;
        this.isCancel = isCancel;
    }

    // getter
    public VoteDTO getVoteDTO()
    {
        return voteDTO;
    }

    public boolean isCancel()
    {
        return isCancel;
    }

    // setter
    public void setVoteDTO(VoteDTO voteDTO)
    {
        this.voteDTO = voteDTO;
    }

    public void setCancel(boolean cancel)
    {
        isCancel = cancel;
    }
}
