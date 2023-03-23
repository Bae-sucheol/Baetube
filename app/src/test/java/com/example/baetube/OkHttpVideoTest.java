package com.example.baetube;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.dto.VideoDTO;
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
public class OkHttpVideoTest
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
                // Gson의 JsonParser를 사용하여 String을 파싱.
                JsonParser parser = new JsonParser();
                JsonArray elementArray = parser.parse(object).getAsJsonArray();
                JsonElement element = elementArray.get(0);

                // accessToken, refreshToken을 가져온다.
                String channelID = element.getAsJsonObject().get("channelId").getAsString();
                String url = element.getAsJsonObject().get("url").getAsString();

                Assert.assertEquals("4", channelID);
                Assert.assertEquals("test", url);

                countDownLatch.countDown();
            }
            @Override
            public void onSelectHistoryVideoResponse(String object)
            {
                // Gson의 JsonParser를 사용하여 String을 파싱.
                JsonParser parser = new JsonParser();
                JsonArray elementArray = parser.parse(object).getAsJsonArray();
                JsonElement element = elementArray.get(0);

                // accessToken, refreshToken을 가져온다.
                String channelID = element.getAsJsonObject().get("channelId").getAsString();
                String url = element.getAsJsonObject().get("url").getAsString();

                Assert.assertEquals("4", channelID);
                Assert.assertEquals("test", url);

                countDownLatch.countDown();
            }
            @Override
            public void onSelectMainVideoResponse(String object)
            {
                // Gson의 JsonParser를 사용하여 String을 파싱.
                JsonParser parser = new JsonParser();
                JsonArray elementArray = parser.parse(object).getAsJsonArray();
                JsonElement element = elementArray.get(0);

                // accessToken, refreshToken을 가져온다.
                String channelID = element.getAsJsonObject().get("channelId").getAsString();
                String url = element.getAsJsonObject().get("url").getAsString();

                Assert.assertEquals("4", channelID);
                Assert.assertEquals("test", url);

                countDownLatch.countDown();
            }
            @Override
            public void onSelectPlaylistVideoResponse(String object)
            {
                // Gson의 JsonParser를 사용하여 String을 파싱.
                JsonParser parser = new JsonParser();
                JsonArray elementArray = parser.parse(object).getAsJsonArray();
                JsonElement element = elementArray.get(0);

                // accessToken, refreshToken을 가져온다.
                String channelID = element.getAsJsonObject().get("channelId").getAsString();
                String url = element.getAsJsonObject().get("url").getAsString();

                Assert.assertEquals("4", channelID);
                Assert.assertEquals("test", url);

                countDownLatch.countDown();
            }
            @Override
            public void onSelectSubscribeVideoResponse(String object)
            {
                // Gson의 JsonParser를 사용하여 String을 파싱.
                JsonParser parser = new JsonParser();
                JsonArray elementArray = parser.parse(object).getAsJsonArray();
                JsonElement element = elementArray.get(0);

                // accessToken, refreshToken을 가져온다.
                String channelID = element.getAsJsonObject().get("channelId").getAsString();
                String url = element.getAsJsonObject().get("url").getAsString();

                Assert.assertEquals("4", channelID);
                Assert.assertEquals("test", url);

                countDownLatch.countDown();
            }
            @Override
            public void onSelectViewVideoResponse(String object)
            {
                // Gson의 JsonParser를 사용하여 String을 파싱.
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(object);

                // accessToken, refreshToken을 가져온다.
                String channelID = element.getAsJsonObject().get("channelId").getAsString();
                String url = element.getAsJsonObject().get("url").getAsString();

                Assert.assertEquals("4", channelID);
                Assert.assertEquals("test", url);

                countDownLatch.countDown();
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
    public void TestA() throws Exception
    {
        VideoDTO video = new VideoDTO(4, "test", 1, "test", "test", "test", "test", 1, 1);
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/video/insert";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(video, url, returnableCallback);

        countDownLatch.await();
    }

    // update
    @Ignore
    @Test
    public void TestB() throws Exception
    {
        VideoDTO video = new VideoDTO(43 , 4, "test2", 1, "test2", "test2", "test2", "test2", 1, 1);
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/video/update";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(video, url, returnableCallback);

        countDownLatch.await();
    }

    // select channel video
    @Ignore
    @Test
    public void TestC() throws Exception
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/video/channel_video/4";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_CHANNEL_VIDEO);

        okHttpUtil.sendGetRequest(url, returnableCallback);

        countDownLatch.await();
    }
    // select history video
    @Ignore
    @Test
    public void TestD() throws Exception
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/video/history_video/20";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_HISTORY_VIDEO);

        okHttpUtil.sendGetRequest(url, returnableCallback);

        countDownLatch.await();
    }

    // select main video
    @Ignore
    @Test
    public void TestE() throws Exception
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/video/main_video/20";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_MAIN_VIDEO);

        okHttpUtil.sendGetRequest(url, returnableCallback);

        countDownLatch.await();
    }

    // select playlist video
    @Ignore
    @Test
    public void TestF() throws Exception
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/video/playlist_video/3";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_PLAYLIST_VIDEO);

        okHttpUtil.sendGetRequest(url, returnableCallback);

        countDownLatch.await();
    }

    // select subscribe video
    @Ignore
    @Test
    public void TestG() throws Exception
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/video/subscribe_video/5";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_SUBSCRIBE_VIDEO);

        okHttpUtil.sendGetRequest(url, returnableCallback);

        countDownLatch.await();
    }

    // select view video
    @Test
    public void TestH() throws Exception
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/video/view_video/20/1";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_VIEW_VIDEO);

        okHttpUtil.sendGetRequest(url, returnableCallback);

        countDownLatch.await();
    }

}
