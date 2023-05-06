package com.example.baetube.Callback;

import com.example.baetube.OnCallbackResponseListener;

import java.io.IOException;
import java.net.HttpURLConnection;

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
    public static final int CALLBACK_RATE = 18;
    public static final int CALLBACK_SUBSCRIBE = 19;
    public static final int CALLBACK_UNSUBSCRIBE = 20;
    public static final int CALLBACK_REPLY = 21;
    public static final int CALLBACK_NESTED_REPLY = 22;
    public static final int CALLBACK_SIGN_IN = 23;
    public static final int CALLBACK_SELECT_RELATED_VIDEO = 24;
    public static final int CALLBACK_SAVE_FCM_TOKEN = 25;
    public static final int CALLBACK_GENERATE_ACCESS_TOKEN = 26;
    public static final int CALLBACK_SELECT_CHANNEL_DATA = 27;
    public static final int CALLBACK_UPDATE_CHANNEL = 28;
    public static final int CALLBACK_SELECT_COMMUNITY_DATA = 29;
    public static final int CALLBACK_UPDATE_COMMUNITY = 30;
    public static final int CALLBACK_SELECT_VIDEO_DATA = 31;
    public static final int CALLBACK_UPDATE_VIDEO = 32;
    public static final int CALLBACK_SELECT_PLAYLIST_DATA = 33;
    public static final int CALLBACK_UPDATE_PLAYLIST = 34;
    public static final int CALLBACK_SELECT_CATEGORY = 35;
    public static final int CALLBACK_SELECT_SUBSCRIBERS_COMMUNITY = 36;
    public static final int CALLBACK_SELECT_COMMUNITY_NOTIFICATION = 37;
    public static final int CALLBACK_SELECT_VIDEO_NOTIFICATION = 38;
    public static final int CALLBACK_SELECT_SEARCH_VIDEO = 39;
    public static final int CALLBACK_SELECT_SEARCH_CHANNEL = 40;
    public static final int CALLBACK_SELECT_CHANNEL_DATA_ALL = 41;
    public static final int CALLBACK_SELECT_NEW_NOTIFICATION = 42;
    public static final int CALLBACK_SELECT_USER_DATA = 43;

    private static final String EXPIRED_ACCESS_TOKEN = "ExpiredAccessToken";
    private static final String EXPIRED_REFRESH_TOKEN = "ExpiredRefreshTokenException";

    private static boolean isReissuingAccessToken = false;

    private OnCallbackResponseListener onCallbackResponseListener;
    private int type;
    private String message;

    public ReturnableCallback(OnCallbackResponseListener onCallbackResponseListener, int type)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
        this.type = type;

        if(type == CALLBACK_GENERATE_ACCESS_TOKEN && isReissuingAccessToken)
        {
            return;
        }

        if(type == CALLBACK_GENERATE_ACCESS_TOKEN && !isReissuingAccessToken)
        {
            isReissuingAccessToken = true;
        }
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
        String header = response.header("Exception", "");

        // 만약 엑세스 토큰 만료 예외 헤더가 존재할 경우
        // 관련 메소드를 실행시킨다.
        if(header.equals(EXPIRED_ACCESS_TOKEN) && type != CALLBACK_GENERATE_ACCESS_TOKEN)
        {
            System.out.println("엑세스토큰 만료 에러");
            onCallbackResponseListener.onExpiredAccessTokenResponse();
            return;
        }
        else if(header.equals(EXPIRED_REFRESH_TOKEN))
        {
            System.out.println("리프레시 토큰 만료 에러");
            onCallbackResponseListener.onExpiredRefreshTokenResponse();
            return;
        }

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
                System.out.println("내용 : " + object);
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

            case CALLBACK_RATE :
                onCallbackResponseListener.onRateResponse(object);
                break;

            case CALLBACK_SUBSCRIBE :
                onCallbackResponseListener.onSubscribeResponse(object);
                break;

            case CALLBACK_UNSUBSCRIBE :
                onCallbackResponseListener.onUnSubscribeResponse(object);
                break;

            case CALLBACK_REPLY :
                onCallbackResponseListener.onReplyResponse(object);
                break;

            case CALLBACK_NESTED_REPLY :
                onCallbackResponseListener.onNestedReplyResponse(object);
                break;

            case CALLBACK_SIGN_IN :
                onCallbackResponseListener.onSignInResponse(object);
                break;

            case CALLBACK_SELECT_RELATED_VIDEO :
                onCallbackResponseListener.onSelectRelatedVideoResponse(object);
                break;

            case CALLBACK_SAVE_FCM_TOKEN :

                    // 만약 응답 결과가 충돌이라면 토큰이 유효하지 않다는 의미.
                    if(response.code() == HttpURLConnection.HTTP_FORBIDDEN)
                    {
                        onCallbackResponseListener.onSaveFCMTokenResponse(false);
                        return;
                    }
                    onCallbackResponseListener.onSaveFCMTokenResponse(true);

                break;

            case CALLBACK_GENERATE_ACCESS_TOKEN :
                onCallbackResponseListener.onGeneratedAccessTokenResponse(object);
                isReissuingAccessToken = false;
                break;

            case CALLBACK_SELECT_CHANNEL_DATA :
                onCallbackResponseListener.onSelectChannelDataResponse(object);
                break;

            case CALLBACK_UPDATE_CHANNEL :
                onCallbackResponseListener.onUpdateChannelResponse(object);
                break;

            case CALLBACK_SELECT_COMMUNITY_DATA :
                onCallbackResponseListener.onSelectCommunityDataResponse(object);
                break;

            case CALLBACK_UPDATE_COMMUNITY :
                onCallbackResponseListener.onUpdateCommunityResponse(object);
                break;

            case CALLBACK_SELECT_VIDEO_DATA :
                onCallbackResponseListener.onSelectVideoDataResponse(object);
                break;

            case CALLBACK_UPDATE_VIDEO :
                onCallbackResponseListener.onUpdateVideoResponse(object);
                break;

            case CALLBACK_SELECT_PLAYLIST_DATA :
                onCallbackResponseListener.onSelectPlaylistDataResponse(object);
                break;

            case CALLBACK_UPDATE_PLAYLIST :
                onCallbackResponseListener.onUpdatePlaylistResponse(object);
                break;

            case CALLBACK_SELECT_CATEGORY :
                onCallbackResponseListener.onSelectCategoryResponse(object);
                break;

            case CALLBACK_SELECT_SUBSCRIBERS_COMMUNITY :
                onCallbackResponseListener.onSelectSubscribersCommunityResponse(object);
                break;

            case CALLBACK_SELECT_COMMUNITY_NOTIFICATION :
                onCallbackResponseListener.onSelectCommunityNotificationResponse(object);
                break;

            case CALLBACK_SELECT_VIDEO_NOTIFICATION :
                onCallbackResponseListener.onSelectVideoNotificationResponse(object);
                break;

            case CALLBACK_SELECT_SEARCH_VIDEO :
                onCallbackResponseListener.onSelectSearchVideoResponse(object);
                break;

            case CALLBACK_SELECT_SEARCH_CHANNEL :
                onCallbackResponseListener.onSelectSearchChannelResponse(object);
                break;

            case CALLBACK_SELECT_CHANNEL_DATA_ALL :
                onCallbackResponseListener.onSelectChannelDataAllResponse(object);
                break;

            case CALLBACK_SELECT_NEW_NOTIFICATION :
                onCallbackResponseListener.onSelectNewNotifications(object);
                break;

            case CALLBACK_SELECT_USER_DATA :
                onCallbackResponseListener.onSelectUserDataResponse(object);
                break;

            default :
                onCallbackResponseListener.onResponse(response);
                break;
        }

    }
}
