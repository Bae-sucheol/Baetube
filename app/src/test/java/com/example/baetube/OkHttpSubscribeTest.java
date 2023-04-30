package com.example.baetube;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.dto.SubscribersDTO;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.CountDownLatch;

import okhttp3.Response;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OkHttpSubscribeTest
{
    private OkHttpUtil okHttpUtil;
    private OnCallbackResponseListener onCallbackResponseListener;
    private CountDownLatch countDownLatch;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.openMocks(this);
        okHttpUtil = new OkHttpUtil();
        onCallbackResponseListener = new OnCallbackResponseListener()
        {
            @Override
            public void onResponse(Response response)
            {
                Assert.assertEquals(true, response.isSuccessful());
                countDownLatch.countDown();
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
                // Gson의 JsonParser를 사용하여 String을 파싱.
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(object);

                String channelId = element.getAsJsonObject().get("channelId").getAsString();
                String subscriberId = element.getAsJsonObject().get("subscriberId").getAsString();

                Assert.assertEquals("4", channelId);
                Assert.assertEquals("5", subscriberId);

                countDownLatch.countDown();
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

            }
        };
    }

    // insert
    @Ignore
    @Test
    public void TestA() throws Exception
    {
        SubscribersDTO subscribers = new SubscribersDTO(4, 5);
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/subscribe/subscribe";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(subscribers, url, returnableCallback);

        countDownLatch.await();
    }

    // delete
    @Ignore
    @Test
    public void TestB() throws Exception
    {
        SubscribersDTO subscribers = new SubscribersDTO(4, 5);
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/subscribe/unsubscribe/0";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(subscribers, url, returnableCallback);

        countDownLatch.await();
    }

    // select
    @Test
    public void TestC() throws Exception
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/subscribe/select/4/5";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_SUBSCRIBE);

        okHttpUtil.sendGetRequest(url, returnableCallback);

        countDownLatch.await();
    }
}
