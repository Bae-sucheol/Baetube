package com.example.baetube;

import android.content.Context;

import com.example.baetube.Callback.ReturnableCallback;
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

    private static Context applicationContext;

    public static void setApplicationContext(Context context)
    {
        applicationContext = context;
    }

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
                .addHeader("Authorization", "Bearer " + PreferenceManager.getString(applicationContext, PreferenceManager.PREFERENCES_ACCESSKEY))
                .post(RequestBody.create(MediaType.parse("application/json"), content))
                .build();

        return request;
    }

    private Request createGetRequest(String url)
    {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + PreferenceManager.getString(applicationContext, PreferenceManager.PREFERENCES_ACCESSKEY))
                .get()
                .build();

        return request;
    }

    public void sendPostRequest(Object object, String url, Callback callback)
    {
        if(checkReissuing(callback))
        {
            return;
        }

        String content = createContent(object);
        Request request = createPostRequest(url, content);

        okHttpClient.newCall(request).enqueue(callback);
    }

    public void sendGetRequest(String url, Callback callback)
    {
        if(checkReissuing(callback))
        {
            return;
        }

        Request request = createGetRequest(url);

        okHttpClient.newCall(request).enqueue(callback);
    }

    public void sendFileRequest(FileUploadDTO fileUploadDTO, Callback callback)
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
                .url(applicationContext.getString(R.string.api_url_file_upload))
                .addHeader("Authorization", "Bearer " + PreferenceManager.getString(applicationContext, PreferenceManager.PREFERENCES_ACCESSKEY))
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    private boolean checkReissuing(Callback callback)
    {
        ReturnableCallback returnableCallback = (ReturnableCallback) callback;

        if(returnableCallback.getType() == ReturnableCallback.CALLBACK_GENERATE_ACCESS_TOKEN)
        {
            if(ReturnableCallback.isReissuingAccessToken())
            {
                return true;
            }

            ReturnableCallback.setIsReissuingAccessToken(true);
        }

        return false;
    }

}
