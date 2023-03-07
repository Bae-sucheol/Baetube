package com.example.baetube.recyclerview.item;

import com.example.baetube.dto.VoteDTO;

public class RecyclerViewVoteItem
{
    private VoteDTO voteDTO;
    private boolean isSelected;

    public RecyclerViewVoteItem(VoteDTO voteDTO, boolean isSelected)
    {
        this.voteDTO = voteDTO;
        this.isSelected = isSelected;
    }

    // getter
    public VoteDTO getVoteDTO()
    {
        return voteDTO;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    // setter
    public void setVoteDTO(VoteDTO voteDTO)
    {
        this.voteDTO = voteDTO;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }
}
