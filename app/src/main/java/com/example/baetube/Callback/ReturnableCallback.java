package com.example.baetube.Callback;

import com.example.baetube.OnCallbackResponseListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ReturnableCallback implements Callback
{
    public static final int CALLBACK_NONE = 0;
    public static final int CALLBACK_LOGIN_USER = 1;
    public static final int CALLBACK_VISIT_CHANNEL = 2;
    public static final int CALLBACK_SUBSCRIBERS_CHANNEL = 3;
    public static final int CALLBACK_VISIT_COMMUNITY = 4;
    public static final int CALLBACK_SELECT_REPLY = 5;
    public static final int CALLBACK_SELECT_NESTED_REPLY = 6;
    public static final int CALLBACK_SELECT_PLAYLIST = 7;
    public static final int CALLBACK_SELECT_SEARCH_HISTORY = 8;
    public static final int CALLBACK_SELECT_CHANNEL_VIDEO = 9;
    public static final int CALLBACK_SELECT_HISTORY_VIDEO = 10;
    public static final int CALLBACK_SELECT_MAIN_VIDEO = 11;
    public static final int CALLBACK_SELECT_PLAYLIST_VIDEO = 12;
    public static final int CALLBACK_SELECT_SUBSCRIBE_VIDEO = 13;
    public static final int CALLBACK_SELECT_VIEW_VIDEO = 14;
    public static final int CALLBACK_SELECT_VOTE_OPTION = 15;
    public static final int CALLBACK_SELECT_SUBSCRIBE = 16;
    public static final int CALLBACK_INSERT = 17;


    private OnCallbackResponseListener onCallbackResponseListener;
    private int type;
    private String message;

    public ReturnableCallback(OnCallbackResponseListener onCallbackResponseListener, int type)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
        this.type = type;
    }

    public ReturnableCallback(OnCallbackResponseListener onCallbackResponseListener, int type, String message)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
        this.type = type;
        this.message = message;
    }

    @Override
    public void onFailure(Call call, IOException e)
    {
        e.printStackTrace();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException
    {
        String object = response.body().string();

        switch (type)
        {
            case CALLBACK_LOGIN_USER :
                onCallbackResponseListener.onLoginUserResponse(object);
                break;

            case CALLBACK_VISIT_CHANNEL :
                onCallbackResponseListener.onVisitChannelResponse(object);
                break;

            case CALLBACK_SUBSCRIBERS_CHANNEL :
                onCallbackResponseListener.onSubscribersChannelResponse(object);
                break;

            case CALLBACK_VISIT_COMMUNITY :
                onCallbackResponseListener.onVisitCommunityResponse(object);
                break;

            case CALLBACK_SELECT_REPLY :
                onCallbackResponseListener.onSelectReplyResponse(object);
                break;

            case CALLBACK_SELECT_NESTED_REPLY :
                onCallbackResponseListener.onSelectNestedReplyResponse(object);
                break;

            case CALLBACK_SELECT_PLAYLIST :
                onCallbackResponseListener.onSelectPlaylistResponse(object);
                break;

            case CALLBACK_SELECT_SEARCH_HISTORY :
                onCallbackResponseListener.onSelectSearchHistoryResponse(object);
                break;

            case CALLBACK_SELECT_CHANNEL_VIDEO :
                onCallbackResponseListener.onSelectChannelVideoResponse(object);
                break;

            case CALLBACK_SELECT_HISTORY_VIDEO :
                onCallbackResponseListener.onSelectHistoryVideoResponse(object);
                break;

            case CALLBACK_SELECT_MAIN_VIDEO :
                onCallbackResponseListener.onSelectMainVideoResponse(object);
                break;

            case CALLBACK_SELECT_PLAYLIST_VIDEO :
                onCallbackResponseListener.onSelectPlaylistVideoResponse(object);
                break;

            case CALLBACK_SELECT_SUBSCRIBE_VIDEO :
                onCallbackResponseListener.onSelectSubscribeVideoResponse(object);
                break;

            case CALLBACK_SELECT_VIEW_VIDEO :
                onCallbackResponseListener.onSelectViewVideoResponse(object);
                break;

            case CALLBACK_SELECT_VOTE_OPTION :
                onCallbackResponseListener.onSelectVoteOptionResponse(object);
                break;

            case CALLBACK_SELECT_SUBSCRIBE :
                onCallbackResponseListener.onSelectSubscribeResponse(object);
                break;

            case CALLBACK_INSERT :
                onCallbackResponseListener.onInsertResponse(object);
                break;

            default :
                onCallbackResponseListener.onResponse(response);
                break;
        }

    }
}
