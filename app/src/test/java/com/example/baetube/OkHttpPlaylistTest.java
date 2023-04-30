package com.example.baetube;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.dto.PlaylistDTO;
import com.example.baetube.dto.PlaylistItemDTO;
import com.google.gson.GsonBuilder;
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
public class OkHttpPlaylistTest
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
                // Gson의 JsonParser를 사용하여 String을 파싱.
                JsonParser parser = new JsonParser();
                JsonArray elementArray = parser.parse(object).getAsJsonArray();
                JsonElement element = elementArray.get(0);

                // accessToken, refreshToken을 가져온다.
                String channelId = element.getAsJsonObject().get("channelId").getAsString();
                String name = element.getAsJsonObject().get("name").getAsString();

                Assert.assertEquals("4", channelId);
                Assert.assertEquals("test", name);

                countDownLatch.countDown();
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

    // insert playlist
    @Ignore
    @Test
    public void testA() throws Exception
    {
        PlaylistDTO playlist = new PlaylistDTO(4, "test", 1, "test");
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/playlist/insert";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(playlist, url, returnableCallback);

        countDownLatch.await();
    }

    // delete playlist
    @Ignore
    @Test
    public void testB() throws Exception
    {
        PlaylistDTO playlist = new PlaylistDTO();
        playlist.setPlaylistId(4);
        playlist.setChannelId(4);

        String content = new GsonBuilder()
                .create().toJson(playlist);

        System.out.println(" content : " + content);

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/playlist/delete/0";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(playlist, url, returnableCallback);

        countDownLatch.await();
    }

    // update playlist
    @Test
    public void testC() throws Exception
    {
        PlaylistDTO playlist = new PlaylistDTO(11, 4, "testPlaylistTitle222", 0, 0,
                "3f205674-89e9-442b-8f2a-8599511d2a2c");

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/playlist/update";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(playlist, url, returnableCallback);

        countDownLatch.await();
    }

    // select playlist
    @Ignore
    @Test
    public void testD() throws Exception
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/playlist/channel/4";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_PLAYLIST);

        okHttpUtil.sendGetRequest(url, returnableCallback);

        countDownLatch.await();
    }

    // insert playlistItem
    @Ignore
    @Test
    public void testE() throws Exception
    {
        PlaylistItemDTO playlistItem = new PlaylistItemDTO(3, 1);
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/playlist/item/insert";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(playlistItem, url, returnableCallback);

        countDownLatch.await();
    }

    // delete playlistItem
    @Ignore
    @Test
    public void testF() throws Exception
    {
        PlaylistItemDTO playlistItem = new PlaylistItemDTO(3, 1);
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/playlist/item/delete";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(playlistItem, url, returnableCallback);

        countDownLatch.await();
    }

}
