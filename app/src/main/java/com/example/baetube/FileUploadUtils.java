package com.example.baetube;
import android.content.Context;
import android.util.Log;

import com.google.android.exoplayer2.ui.StyledPlayerView;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FileUploadUtils
{
    public static void send2Server(File file, StyledPlayerView player, Context context)
    {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("type", "image")
                .addFormDataPart("purpose", "profile")
                .addFormDataPart("id", "1")
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MultipartBody.FORM, file))
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.0.4:9090/Baetube_backEnd/api/file/upload")
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                Log.d("TEST : ", response.body().string());

                /*
                ExoPlayer exoPlayer = new ExoPlayer.Builder(context).build();
                player.setPlayer(exoPlayer);

                MediaItem mediaItem = MediaItem.fromUri("file:///G:/baetube/temp/23b82159-4730-4752-bad7-5dc992857e9e_1_1080.m3u8");
                exoPlayer.addMediaItem(mediaItem);

                exoPlayer.prepare();
                exoPlayer.setPlayWhenReady(true);
            */
            }

        });
    }

}
