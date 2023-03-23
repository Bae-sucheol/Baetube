package com.example.baetube;

public interface OnDialogInteractionListener
{
    public void onAddVoteResponse(String voteItem);
    public void onDeletePlaylistItem(int position);
    public void onSetVideoResolution(int position);
}
