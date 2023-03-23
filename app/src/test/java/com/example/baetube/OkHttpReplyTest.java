package com.example.baetube;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.dto.ReplyDTO;
import com.google.gson.JsonArray;
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
public class OkHttpReplyTest
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
                // Gson의 JsonParser를 사용하여 String을 파싱.
                JsonParser parser = new JsonParser();
                JsonArray elementArray = parser.parse(object).getAsJsonArray();
                JsonElement element = elementArray.get(0);

                // accessToken, refreshToken을 가져온다.
                String channelId = element.getAsJsonObject().get("channelId").getAsString();
                String comment = element.getAsJsonObject().get("comment").getAsString();

                Assert.assertEquals("4", channelId);
                Assert.assertEquals("test", comment);

                countDownLatch.countDown();
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
        };
    }

    // insert
    @Ignore
    @Test
    public void testA() throws Exception
    {
        ReplyDTO reply = new ReplyDTO(82L, 4, "test");
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/reply/insert";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(reply, url, returnableCallback);

        countDownLatch.await();
    }

    // update
    // 삭제는 contents..
    @Ignore
    @Test
    public void testB() throws Exception
    {
        ReplyDTO reply = new ReplyDTO();
        reply.setReplyId(26);
        reply.setComment("testUpdate");
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/reply/update";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(reply, url, returnableCallback);

        countDownLatch.await();
    }
    // select
    @Test
    public void testC() throws Exception
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/reply/select/82";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_REPLY);

        okHttpUtil.sendGetRequest(url, returnableCallback);

        countDownLatch.await();
    }


}
