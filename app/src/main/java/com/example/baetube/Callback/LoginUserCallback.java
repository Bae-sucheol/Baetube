package com.example.baetube.Callback;

import android.content.Context;

import com.example.baetube.PreferenceManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginUserCallback implements Callback
{
    private Context context;

    public LoginUserCallback(Context context)
    {
        this.context = context;
    }

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
}
