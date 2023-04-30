package com.example.baetube;

public interface OnDialogInteractionListener
{
    public void onAddVoteResponse(String voteItem);
    public void onDeletePlaylistItem(int position);
    public void onSetVideoResolution(int position);
    public void onDeleteCommunity();
    public void onModifyCommunity();
    public void onDeleteNotification();
    public void onSelectChannel(int position, int channelId);
}
