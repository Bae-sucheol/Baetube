package com.example.baetube.Callback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 오류 내용, 성공 여부만 간단하게 출력하는 DefaultCallback.
 *
 */
public class DefaultCallback implements Callback
{
    private String message;

    public DefaultCallback(String message)
    {
        this.message = message;
    }

    @Override
    public void onFailure(Call call, IOException e)
    {
        e.printStackTrace();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException
    {
    }
}
