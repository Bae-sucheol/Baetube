package com.example.baetube;

import android.content.Context;

import androidx.viewpager2.widget.ViewPager2;

import com.example.baetube.Callback.ReturnableCallback;

public class ViewPagerCallback extends ViewPager2.OnPageChangeCallback
{
    private Context context;
    private OnCallbackResponseListener onCallbackResponseListener;
    private OkHttpUtil okHttpUtil;
    private boolean visited[] = new boolean[5];
    private Integer channelId;

    public ViewPagerCallback(Context context , OnCallbackResponseListener onCallbackResponseListener, Integer channelId)
    {
        super();
        this.context = context;
        this.onCallbackResponseListener = onCallbackResponseListener;
        this.channelId = channelId;
    }

    @Override
    public void onPageSelected(int position)
    {
        super.onPageSelected(position);

        if(visited[position] == true)
        {
            return;
        }

        switch (position)
        {
            case 0 :

                okHttpUtil = new OkHttpUtil();

                String url = context.getString(R.string.api_url_channel_visit)  + channelId;

                ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_VISIT_CHANNEL);

                okHttpUtil.sendGetRequest(url, returnableCallback);

                // 채널 동영상 정보를 가져온다.

                url = context.getString(R.string.api_url_video_channel) + channelId;

                returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_CHANNEL_VIDEO);

                okHttpUtil.sendGetRequest(url, returnableCallback);

                break;
            case 1 :

                okHttpUtil = new OkHttpUtil();

                // 채널 동영상 정보를 가져온다.

                url = context.getString(R.string.api_url_video_channel) + channelId;

                returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_CHANNEL_VIDEO);

                okHttpUtil.sendGetRequest(url, returnableCallback);

                break;
            case 2 :

                okHttpUtil = new OkHttpUtil();

                // 채널 동영상 정보를 가져온다.

                url = context.getString(R.string.api_url_playlist_channel) + channelId;

                returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_PLAYLIST);

                okHttpUtil.sendGetRequest(url, returnableCallback);

                break;
            case 3 :

                okHttpUtil = new OkHttpUtil();

                // 채널 커뮤니티 정보를 가져온다.

                url = context.getString(R.string.api_url_community_visit) + channelId + "/" + PreferenceManager.getChannelSequence(context.getApplicationContext());

                returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_VISIT_COMMUNITY);

                okHttpUtil.sendGetRequest(url, returnableCallback);

                break;

            case 4 :

                break;

        }

        visited[position] = true;
    }
}
