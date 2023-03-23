package com.example.baetube;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.dto.NestedReplyDTO;
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
public class OkHttpNestedReplyTest
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
            }

            @Override
            public void onSelectNestedReplyResponse(String object)
            {
                // Gson의 JsonParser를 사용하여 String을 파싱.
                JsonParser parser = new JsonParser();
                JsonArray elementArray = parser.parse(object).getAsJsonArray();
                JsonElement element = elementArray.get(0);

                // accessToken, refreshToken을 가져온다.
                String channelId = element.getAsJsonObject().get("channelId").getAsString();
                String comment = element.getAsJsonObject().get("comment").getAsString();

                Assert.assertEquals("5", channelId);
                Assert.assertEquals("testUpdate", comment);

                countDownLatch.countDown();
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
        NestedReplyDTO nestedReply = new NestedReplyDTO(26, 5, "test", 83L);
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/nestedreply/insert";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(nestedReply, url, returnableCallback);

        countDownLatch.await();
    }

    // update
    // 삭제는 contents..
    @Ignore
    @Test
    public void testB() throws Exception
    {
        NestedReplyDTO nestedReply = new NestedReplyDTO();
        nestedReply.setNestedReplyId(7);
        nestedReply.setComment("testUpdate");
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/nestedreply/update";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(nestedReply, url, returnableCallback);

        countDownLatch.await();
    }
    // select
    @Test
    public void testC() throws Exception
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/nestedreply/select/26";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_NESTED_REPLY);

        okHttpUtil.sendGetRequest(url, returnableCallback);

        countDownLatch.await();
    }
}
