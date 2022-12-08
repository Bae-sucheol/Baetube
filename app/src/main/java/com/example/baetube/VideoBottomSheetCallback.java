package com.example.baetube;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.shape.InterpolateOnScrollPositionChangeHelper;

public class VideoBottomSheetCallback extends BottomSheetBehavior.BottomSheetCallback
{
    private int height;
    private View player;

    public VideoBottomSheetCallback(int height, View player)
    {
        this.height = height;
        this.player = player;
        Log.d("test", "height : " + height);
    }

    @Override
    public void onStateChanged(@NonNull View bottomSheet, int newState)
    {

    }
    // 선형 보간법을 통해 offset이 0.1 까지 갈 때까지 선형적으로 높이가 줄어드는 형태로 만들어야 한다.
    @Override
    public void onSlide(@NonNull View bottomSheet, float slideOffset)
    {
        Log.d("test", "offset : " + slideOffset);

        if(slideOffset < 1.0)
        {
            player.getLayoutParams().height = (int)(height * slideOffset);
            player.setLayoutParams(player.getLayoutParams());
        }
        else if(slideOffset < 0.1)
        {

        }

    }

    /**
     * 선형 보간
     */
    private float Lerp(float start, float end, float interval)
    {
        return start * (1 - interval) + end * interval;
    }
}
