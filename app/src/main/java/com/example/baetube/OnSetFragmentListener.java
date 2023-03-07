package com.example.baetube;

import com.example.baetube.dto.VoteDTO;

import java.util.List;

public interface OnSetFragmentListener
{
    public void onResponseDescription(String str);
    public void onResponsePublic(Integer value);
    public void onResponseLocation(String str);
    public void onResponseAge(Integer value);
    public void onResponseVote(VoteDTO vote, List<VoteDTO> voteOptions);
}
