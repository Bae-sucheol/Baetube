package com.example.baetube;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.dto.VoteDTO;
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

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import okhttp3.Response;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OkHttpVoteTest
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
                // Gson의 JsonParser를 사용하여 String을 파싱.
                JsonParser parser = new JsonParser();
                JsonArray elementArray = parser.parse(object).getAsJsonArray();
                JsonElement element = elementArray.get(0);

                // accessToken, refreshToken을 가져온다.
                String voteId = element.getAsJsonObject().get("voteId").getAsString();
                String voteOptionId = element.getAsJsonObject().get("voteOptionId").getAsString();
                String option = element.getAsJsonObject().get("option").getAsString();

                Assert.assertEquals("18", voteId);
                Assert.assertEquals("2", voteOptionId);
                Assert.assertEquals("test", option);

                countDownLatch.countDown();
            }

            @Override
            public void onSelectSubscribeResponse(String object)
            {

            }

            @Override
            public void onInsertResponse(String object)
            {

            }
        };
    }

    // insert vote
    @Test
    public void testA() throws Exception
    {
        //VoteDTO vote = new VoteDTO(5, "test", "test");
        VoteDTO vote = new VoteDTO();
        vote.setCommunityId(6);
        vote.setComment("test");

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/vote/insert";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(vote, url, returnableCallback);

        countDownLatch.await();
    }

    // delete vote
    @Ignore
    @Test
    public void testB() throws Exception
    {
        VoteDTO vote = new VoteDTO();
        vote.setVoteId(18);

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/vote/delete";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(vote, url, returnableCallback);

        countDownLatch.await();
    }

    // update vote
    @Ignore
    @Test
    public void testC() throws Exception
    {
        VoteDTO vote = new VoteDTO(18, 5, "testUpdate", "testUpdate");

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/vote/update";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(vote, url, returnableCallback);

        countDownLatch.await();
    }

    // insert vote option
    @Ignore
    @Test
    public void testD() throws Exception
    {
        VoteDTO vote = new VoteDTO(18, "test");

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/vote/insert/option";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(vote, url, returnableCallback);

        countDownLatch.await();
    }

    // delete vote option
    @Ignore
    @Test
    public void testE() throws Exception
    {
        VoteDTO vote = new VoteDTO();
        vote.setVoteId(18);
        vote.setVoteOptionId(1);

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/vote/delete/option";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(vote, url, returnableCallback);

        countDownLatch.await();
    }

    // update vote option
    @Ignore
    @Test
    public void testF() throws Exception
    {
        VoteDTO vote = new VoteDTO();
        vote.setVoteId(18);
        vote.setVoteOptionId(2);
        vote.setOption("testUpdate");

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/vote/update/option";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(vote, url, returnableCallback);

        countDownLatch.await();
    }

    // select vote option
    @Ignore
    @Test
    public void testG() throws Exception
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/vote/select/option/18";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_VOTE_OPTION);

        okHttpUtil.sendGetRequest(url, returnableCallback);

        countDownLatch.await();
    }

    // insert vote option multi
    @Ignore
    @Test
    public void testH() throws Exception
    {
        ArrayList<VoteDTO> voteOptionList = new ArrayList<>();
        voteOptionList.add(new VoteDTO(18, "test2"));
        voteOptionList.add(new VoteDTO(18, "test3"));
        voteOptionList.add(new VoteDTO(18, "test4"));

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/vote/insert/option/multi";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(voteOptionList , url, returnableCallback);

        countDownLatch.await();
    }

    // delete vote option multi
    @Ignore
    @Test
    public void testI() throws Exception
    {
        ArrayList<VoteDTO> voteOptionList = new ArrayList<>();
        voteOptionList.add(new VoteDTO(18, 3));
        voteOptionList.add(new VoteDTO(18, 4));
        voteOptionList.add(new VoteDTO(18, 5));

        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/vote/delete/option/multi";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(voteOptionList , url, returnableCallback);

        countDownLatch.await();
    }
}
