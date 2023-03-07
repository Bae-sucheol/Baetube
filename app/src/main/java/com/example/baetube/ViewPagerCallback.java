package com.example.baetube;

import androidx.viewpager2.widget.ViewPager2;

import com.example.baetube.Callback.ReturnableCallback;

public class ViewPagerCallback extends ViewPager2.OnPageChangeCallback
{
    private OnCallbackResponseListener onCallbackResponseListener;
    private OkHttpUtil okHttpUtil;
    private boolean visited[] = new boolean[5];

    public ViewPagerCallback(OnCallbackResponseListener onCallbackResponseListener)
    {
        super();
        this.onCallbackResponseListener = onCallbackResponseListener;
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

                // 채널 Id를 기입해야 한다. 지금은 테스트용으로 임의의 값을 넣는다.
                String url = "http://192.168.0.4:9090/Baetube_backEnd/api/channel/visit/4";

                ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_VISIT_CHANNEL);

                okHttpUtil.sendGetRequest(url, returnableCallback);

                // 채널 동영상 정보를 가져온다.

                url = "http://192.168.0.4:9090/Baetube_backEnd/api/video/channel_video/4";

                returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_CHANNEL_VIDEO);

                okHttpUtil.sendGetRequest(url, returnableCallback);

                break;
            case 1 :

                okHttpUtil = new OkHttpUtil();

                // 채널 동영상 정보를 가져온다.

                url = "http://192.168.0.4:9090/Baetube_backEnd/api/video/channel_video/4";

                returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_CHANNEL_VIDEO);

                okHttpUtil.sendGetRequest(url, returnableCallback);

                break;
            case 2 :

                okHttpUtil = new OkHttpUtil();

                // 채널 동영상 정보를 가져온다.

                url = "http://192.168.0.4:9090/Baetube_backEnd/api/playlist/channel/4";

                returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_PLAYLIST);

                okHttpUtil.sendGetRequest(url, returnableCallback);

                break;
            case 3 :

                okHttpUtil = new OkHttpUtil();

                // 채널 커뮤니티 정보를 가져온다.

                url = "http://192.168.0.4:9090/Baetube_backEnd/api/community/channel_visit/5/4";

                returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_VISIT_COMMUNITY);

                okHttpUtil.sendGetRequest(url, returnableCallback);

                break;

            case 4 :

                break;

        }

        visited[position] = true;
    }
}
