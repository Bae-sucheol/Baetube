package com.example.baetube;

import okhttp3.Response;

public interface OnCallbackResponseListener
{
    public void onResponse(Response response);
    public void onLoginUserResponse(String object);
    public void onVisitChannelResponse(String object);
    public void onSubscribersChannelResponse(String object);
    public void onVisitCommunityResponse(String object);
    public void onSelectReplyResponse(String object);
    public void onSelectNestedReplyResponse(String object);
    public void onSelectPlaylistResponse(String object);
    public void onSelectSearchHistoryResponse(String object);
    public void onSelectChannelVideoResponse(String object);
    public void onSelectHistoryVideoResponse(String object);
    public void onSelectMainVideoResponse(String object);
    public void onSelectPlaylistVideoResponse(String object);
    public void onSelectSubscribeVideoResponse(String object);
    public void onSelectViewVideoResponse(String object);
    public void onSelectVoteOptionResponse(String object);
    public void onSelectSubscribeResponse(String object);
    public void onInsertResponse(String object);
}
