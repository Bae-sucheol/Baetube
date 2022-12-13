package com.example.baetube;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.shape.InterpolateOnScrollPositionChangeHelper;

public class VideoBottomSheetCallback extends BottomSheetBehavior.BottomSheetCallback
{
    private int height;
    private int width;
    private View player;
    private View description;
    private int state;
    private BottomSheetBehavior behavior;
    private int peekHeight;
    private int peekWidth;
    private BottomNavigationView bottomNavigationView;

    public VideoBottomSheetCallback(View player, View description, BottomSheetBehavior behavior, BottomNavigationView bottomNavigationView)
    {
        this.player = player;
        this.description = description;
        this.behavior = behavior;
        this.bottomNavigationView = bottomNavigationView;

        height = (int)(UserDisplay.getWidth() * UserDisplay.getRatio());
        width = (int)UserDisplay.getWidth();
        peekHeight = (int)(UserDisplay.getWidth() * 0.16);
        peekWidth = (int)(peekHeight * 2.33);

        behavior.setPeekHeight(peekHeight);
    }

    @Override
    public void onStateChanged(@NonNull View bottomSheet, int newState)
    {
        state = newState;
    }
    // 선형 보간법을 통해 offset이 0.1 까지 갈 때까지 선형적으로 높이가 줄어드는 형태로 만들어야 한다.
    @Override
    public void onSlide(@NonNull View bottomSheet, float slideOffset)
    {
        float interval = 1 - slideOffset;

        if(slideOffset <= 0.1)
        {
            player.getLayoutParams().width = (int) Lerp(width, peekWidth, 1 - slideOffset * 10);
        }
        else
        {
            player.getLayoutParams().width = width;
        }

        if(slideOffset >= 0)
        {
            player.getLayoutParams().height = (int) Lerp(height, peekHeight, interval);
            player.setLayoutParams(player.getLayoutParams());

            description.setAlpha(slideOffset);
            bottomSheet.setAlpha(1);
        }
        else
        {
            bottomSheet.setAlpha(1 + slideOffset);
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
