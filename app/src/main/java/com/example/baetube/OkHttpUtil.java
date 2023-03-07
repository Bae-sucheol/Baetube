package com.example.baetube;

import com.example.baetube.dto.FileUploadDTO;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpUtil
{
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(600, TimeUnit.SECONDS)
            .readTimeout(600, TimeUnit.SECONDS)
            .writeTimeout(600, TimeUnit.SECONDS)
            .build();

    private String createContent(Object object)
    {
        String content = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()
            .toJson(object);

        return content;
    }

    private Request createPostRequest(String url, String content)
    {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), content))
                .build();

        return request;
    }

    private Request createGetRequest(String url)
    {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        return request;
    }

    public void sendPostRequest(Object object, String url, Callback callback)
    {
        String content = createContent(object);
        Request request = createPostRequest(url, content);

        okHttpClient.newCall(request).enqueue(callback);
    }

    public void sendGetRequest(String url, Callback callback)
    {
        Request request = createGetRequest(url);

        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void sendFileRequest(FileUploadDTO fileUploadDTO, Callback callback)
    {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("type", fileUploadDTO.getType())
                .addFormDataPart("purpose", fileUploadDTO.getPurpose())
                .addFormDataPart("uuid", fileUploadDTO.getId())
                .addFormDataPart("file", fileUploadDTO.getFile().getName(),
                        RequestBody.create(MultipartBody.FORM, fileUploadDTO.getFile()))
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.0.4:9090/Baetube_backEnd/api/file/upload")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    // 로그아웃도 추후 구현해야 함.
    /*
     * User Controller
     * regist, unregist, login, update, changePassword
     * count : 5
     */

    /*

    public void registUser(UserDTO user) throws Exception
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/user/regist";
        String content = createContent(user);
        Request request = createPostRequest(url, content);

        // Callback 도 하나하나 클래스로 만들어서 처리하면 좋을 것 같다.
        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                // 가입 성공 토스트 메시지 출력.
            }
        });

    }

    public void unRegistUser(UserDTO user) throws Exception
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/user/unregist";
        String content = createContent(user);
        Request request = createPostRequest(url, content);

        // Callback 도 하나하나 클래스로 만들어서 처리하면 좋을 것 같다.
        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {

            }
        });
    }

    public void loginUser(UserDTO user) throws Exception
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/user/unregist";
        String content = createContent(user);
        Request request = createPostRequest(url, content);

        // Callback 도 하나하나 클래스로 만들어서 처리하면 좋을 것 같다.
        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                // String으로 body의 내용을 가져온다.
                String responseBody = response.body().string();
                // Gson의 JsonParser를 사용하여 String을 파싱.
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(responseBody);

                // accessToken, refreshToken을 가져온다.
                String accessToken = element.getAsJsonObject().get("accessToken").getAsString();
                String refreshToken = element.getAsJsonObject().get("refreshToken").getAsString();

                // PreferenceManager를 사용하여 accessToken과 refreshToken을 저장한다.
                PreferenceManager.setString(context, PreferenceManager.PREFERENCES_ACCESSKEY, accessToken);
                PreferenceManager.setString(context, PreferenceManager.PREFERENCES_REFRESHKEY, refreshToken);
            }
        });

    }

    public void updateUser(UserDTO user) throws Exception
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/user/unregist";
        String content = createContent(user);
        Request request = createPostRequest(url, content);

        // Callback 도 하나하나 클래스로 만들어서 처리하면 좋을 것 같다.
        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {

            }
        });

    }

    public void changePasswordUser(ChangePasswordRequest changePasswordRequest) throws Exception
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/user/unregist";
        String content = createContent(changePasswordRequest);
        Request request = createPostRequest(url, content);

        // Callback 도 하나하나 클래스로 만들어서 처리하면 좋을 것 같다.
        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {

            }
        });

    }


     */

    /*
     * Channel Controller
     * insert, delete, update, channelVisit, subscribers
     * count : 5
     */

    /*
    public void insertChannel(ChannelDTO channelDTO)
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/channel/insert";
        String content = createContent(channelDTO);
        Request request = createPostRequest(url, content);

        // Callback 도 하나하나 클래스로 만들어서 처리하면 좋을 것 같다.
        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {

            }
        });
    }

    public void deleteChannel(ChannelDTO channelDTO)
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/channel/delete";
        String content = createContent(channelDTO);
        Request request = createPostRequest(url, content);

        // Callback 도 하나하나 클래스로 만들어서 처리하면 좋을 것 같다.
        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {

            }
        });
    }

    public void updateChannel(ChannelDTO channelDTO)
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/channel/update";
        String content = createContent(channelDTO);
        Request request = createPostRequest(url, content);

        // Callback 도 하나하나 클래스로 만들어서 처리하면 좋을 것 같다.
        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {

            }
        });
    }

    public void visitChannel(Integer channelId)
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/channel/visit/" + channelId;
        Request request = createGetRequest(url);

        // Callback 도 하나하나 클래스로 만들어서 처리하면 좋을 것 같다.
        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {

            }
        });
    }

    public void subscribersChannel(Integer channelId)
    {
        String url = "http://192.168.0.4:9090/Baetube_backEnd/api/channel/subscribers/" + channelId;
        Request request = createGetRequest(url);

        // Callback 도 하나하나 클래스로 만들어서 처리하면 좋을 것 같다.
        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {

            }
        });
    }


     */
    /*
     * Community Controller
     * insert, delete, update, channelVisit, subscribers
     * count : 5
     */


}
