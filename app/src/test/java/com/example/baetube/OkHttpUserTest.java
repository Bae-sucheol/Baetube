package com.example.baetube;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.dto.ChangePasswordRequest;
import com.example.baetube.dto.UserDTO;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.concurrent.CountDownLatch;

import okhttp3.Response;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OkHttpUserTest
{
    @Mock
    private static MockedStatic<PreferenceManager> preferenceManager;
    @Mock
    private SharedPreferences sharedPreferences;
    @Mock
    private SharedPreferences.Editor editor;
    @Mock
    private Context context;

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
                // Gson의 JsonParser를 사용하여 String을 파싱.
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(object);

                // accessToken, refreshToken을 가져온다.
                String accessToken = element.getAsJsonObject().get("accessToken").getAsString();
                String refreshToken = element.getAsJsonObject().get("refreshToken").getAsString();

                System.out.println("accessToken : " + accessToken);
                System.out.println("refreshToken : " + refreshToken);

                Assert.assertNotEquals(null, accessToken);
                Assert.assertNotEquals(null, refreshToken);
                Assert.assertNotEquals("", accessToken);
                Assert.assertNotEquals("", refreshToken);

                countDownLatch.countDown();
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
        };

        Mockito.when(PreferenceManager.getPreferences(context)).thenReturn(sharedPreferences);
        Mockito.when(sharedPreferences.edit()).thenReturn(editor);
    }

    // Regist
    @Ignore
    @Test
    public void testA() throws Exception
    {
        UserDTO user = new UserDTO("test8@naver.com", "1234", "test8", 1, new Timestamp(System.currentTimeMillis()), "fcmToken", "00000000000", "test", null);
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/user/regist";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(user, url, returnableCallback);

        countDownLatch.await();
    }

    // Unregist
    @Ignore
    @Test
    public void testB() throws Exception
    {
        UserDTO user = new UserDTO("test8@naver.com");
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/user/unregist";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(user, url, returnableCallback);

        countDownLatch.await();
    }

    // login
    @Ignore
    @Test
    public void testC() throws Exception
    {
        UserDTO user = new UserDTO("test4@naver.com", "1234", "test", 1, new Timestamp(System.currentTimeMillis()), "fcmToken", "00000000000", "test", null);
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/user/login";
        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_LOGIN_USER);

        okHttpUtil.sendPostRequest(user, url, returnableCallback);

        countDownLatch.await();
    }

    // update
    @Ignore
    @Test
    public void testD() throws Exception
    {
        // 동시에 불러와야 하는 경우도 있기 때문에 싱글톤으로는 안된다.
        // 대신 okHttpClient에 connnection pool이 있기 때문에 클라이언트를 클래스 변수로 만들어
        // connection pool을 사용한다.
        UserDTO user = new UserDTO("test@naver.com", "1234", "test", 1, new Timestamp(System.currentTimeMillis()), "fcmToken", "11111111111", "test", null);
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/user/update";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(user, url, returnableCallback);

        countDownLatch.await();
    }

    // changePassword
    @Ignore
    @Test
    public void testE() throws Exception
    {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("testtest@naver.com", "test", "1234");
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/user/change_password";

        countDownLatch = new CountDownLatch(1);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(changePasswordRequest, url, returnableCallback);

        countDownLatch.await();
    }

    @After
    public void tearDown() throws Exception
    {
        //mockWebServer.shutdown();
        preferenceManager.close();
    }

}
