package com.example.baetube;

import android.view.View;

import com.example.baetube.dto.VoteDTO;

public interface OnRecyclerViewClickListener
{
    public void onItemClick(View view, int position);
    public void onItemLongClick(View view, int position);
    public void onCastVoteOption(VoteDTO voteData, boolean isCancel);
}
