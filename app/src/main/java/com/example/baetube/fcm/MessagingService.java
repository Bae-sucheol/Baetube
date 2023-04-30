package com.example.baetube.fcm;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.OkHttpUtil;
import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.PreferenceManager;
import com.example.baetube.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

import okhttp3.Response;

public class MessagingService extends FirebaseMessagingService
{
    public MessagingService()
    {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage message)
    {
        super.onMessageReceived(message);
    }

    @Override
    public void onNewToken(String token)
    {
        super.onNewToken(token);

        System.out.println("새로운 fcm 토큰 발급 : " + token);

        // SharedPreferences 에 fcm 토큰을 저장한다.
        PreferenceManager.setString(getApplicationContext(), PreferenceManager.PREFERENCES_FCM, token);

        OnCallbackResponseListener onCallbackResponseListener = new OnCallbackResponseListener()
        {
            @Override
            public void onResponse(Response response) throws IOException
            {

            }

            @Override
            public void onExpiredAccessTokenResponse()
            {

            }

            @Override
            public void onExpiredRefreshTokenResponse()
            {

            }

            @Override
            public void onGeneratedAccessTokenResponse(String object)
            {

            }

            @Override
            public void onLoginUserResponse(String object)
            {

            }

            @Override
            public void onVisitChannelResponse(String object)
            {

            }

            @Override
            public void onSubscribersChannelResponse(String object)
            {

            }

            @Override
            public void onVisitCommunityResponse(String object)
            {

            }

            @Override
            public void onSelectReplyResponse(String object)
            {

            }

            @Override
            public void onSelectNestedReplyResponse(String object)
            {

            }

            @Override
            public void onSelectPlaylistResponse(String object)
            {

            }

            @Override
            public void onSelectSearchHistoryResponse(String object)
            {

            }

            @Override
            public void onSelectChannelVideoResponse(String object)
            {

            }

            @Override
            public void onSelectHistoryVideoResponse(String object)
            {

            }

            @Override
            public void onSelectMainVideoResponse(String object)
            {

            }

            @Override
            public void onSelectPlaylistVideoResponse(String object)
            {

            }

            @Override
            public void onSelectSubscribeVideoResponse(String object)
            {

            }

            @Override
            public void onSelectViewVideoResponse(String object)
            {

            }

            @Override
            public void onSelectVoteOptionResponse(String object)
            {

            }

            @Override
            public void onSelectSubscribeResponse(String object)
            {

            }

            @Override
            public void onInsertResponse(String object)
            {

            }

            @Override
            public void onRateResponse(String object)
            {

            }

            @Override
            public void onSubscribeResponse(String object)
            {

            }

            @Override
            public void onUnSubscribeResponse(String object)
            {

            }

            @Override
            public void onReplyResponse(String object)
            {

            }

            @Override
            public void onNestedReplyResponse(String object)
            {

            }

            @Override
            public void onSignInResponse(String object)
            {

            }

            @Override
            public void onSelectRelatedVideoResponse(String object)
            {

            }

            @Override
            public void onSaveFCMTokenResponse(boolean result)
            {
                // jwt 토큰이 유효하여 fcm 토큰이 성공적으로 갱신되었다면.
                if(result)
                {
                    // 그냥 토큰을 삭제한다.
                    System.out.println("저장된 fcm 토큰을 삭제했습니다.");
                    PreferenceManager.removeKey(getApplicationContext(), PreferenceManager.PREFERENCES_FCM);
                }
                // 실패했다면 다음 로그인 까지 남겨두어야 하므로 다른 행동을 취하지 않는다.
            }

            @Override
            public void onSelectChannelDataResponse(String object)
            {

            }

            @Override
            public void onUpdateChannelResponse(String object)
            {

            }

            @Override
            public void onSelectCommunityDataResponse(String object)
            {

            }

            @Override
            public void onUpdateCommunityResponse(String object)
            {

            }

            @Override
            public void onSelectVideoDataResponse(String object)
            {

            }

            @Override
            public void onUpdateVideoResponse(String object)
            {

            }

            @Override
            public void onSelectPlaylistDataResponse(String object)
            {

            }

            @Override
            public void onUpdatePlaylistResponse(String object)
            {

            }

            @Override
            public void onSelectCategoryResponse(String object)
            {

            }

            @Override
            public void onSelectSubscribersCommunityResponse(String object)
            {

            }

            @Override
            public void onSelectVideoNotificationResponse(String object)
            {

            }

            @Override
            public void onSelectCommunityNotificationResponse(String object)
            {

            }

            @Override
            public void onSelectSearchVideoResponse(String object)
            {

            }

            @Override
            public void onSelectSearchChannelResponse(String object)
            {

            }

            @Override
            public void onSelectChannelDataAllResponse(String object)
            {

            }

        };

        // fcm 토큰 저장을 요청한다.
        OkHttpUtil okHttpUtil = new OkHttpUtil();
        String url = getApplicationContext().getString(R.string.api_url_fcm_save);
        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SAVE_FCM_TOKEN);
        okHttpUtil.sendPostRequest(token, url, returnableCallback);
    }
}
