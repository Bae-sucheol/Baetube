package com.example.baetube;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

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

    public VideoBottomSheetCallback(int height, View player, View description, BottomSheetBehavior behavior)
    {
        this.height = height;
        this.player = player;
        this.description = description;
        this.behavior = behavior;

        width = (int)UserDisplay.getWidth();
        peekHeight = behavior.getPeekHeight();
        peekWidth = (int)(peekHeight * 2.33);
    }

    @Override
    public void onStateChanged(@NonNull View bottomSheet, int newState)
    {
        state = newState;

        switch (state)
        {
            case BottomSheetBehavior.STATE_DRAGGING :
                Log.d("test", "state : Dragging");
                break;
            case BottomSheetBehavior.STATE_SETTLING :
                Log.d("test", "state : Setting");
                break;
            case BottomSheetBehavior.STATE_EXPANDED :
                Log.d("test", "state : Expanded");
                break;
            case BottomSheetBehavior.STATE_COLLAPSED :
                Log.d("test", "state : Collapsed");
                break;
            case BottomSheetBehavior.STATE_HIDDEN :
                Log.d("test", "state : Hidden");
                break;
            case BottomSheetBehavior.STATE_HALF_EXPANDED :
                Log.d("test", "state : Half_Expanded");
                break;
        }


        if(state == BottomSheetBehavior.STATE_COLLAPSED)
        {
            description.setVisibility(View.GONE);
        }
        else if(state == BottomSheetBehavior.STATE_DRAGGING)
        {
            description.setVisibility(View.VISIBLE);
        }
    }
    // 선형 보간법을 통해 offset이 0.1 까지 갈 때까지 선형적으로 높이가 줄어드는 형태로 만들어야 한다.
    @Override
    public void onSlide(@NonNull View bottomSheet, float slideOffset)
    {
        float interval = 1 - slideOffset;

        if(state != BottomSheetBehavior.STATE_COLLAPSED)
        {
            if(slideOffset <= 0.1)
            {
                player.getLayoutParams().width = (int)Lerp(width, peekWidth, 1 - slideOffset * 10);
            }
            else
            {
                player.getLayoutParams().width = width;
            }

            player.getLayoutParams().height = (int) Lerp(height, behavior.getPeekHeight(), interval);
            player.setLayoutParams(player.getLayoutParams());

            description.setAlpha(Lerp(1.0f, 0.0f, interval));
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
