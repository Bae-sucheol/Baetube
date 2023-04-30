package com.example.baetube;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class VideoBottomSheetCallback extends BottomSheetBehavior.BottomSheetCallback implements OnBottomSheetInteractionListener, View.OnTouchListener
{
    private int height;
    private int width;
    private View player;
    private View description;
    private int state;
    private BottomSheetBehavior behavior;
    private int peekHeight;
    private int peekWidth;
    private OnBottomSheetInteractionListener onBottomSheetInteractionListener;

    public VideoBottomSheetCallback(Context context, View player, View description, BottomSheetBehavior behavior, int peekHeight)
    {
        this.player = player;
        this.description = description;
        this.behavior = behavior;
        this.peekHeight = peekHeight;

        description.setOnTouchListener(this);
        player.setOnTouchListener(this);

        height = (int)(UserDisplay.getWidth() * UserDisplay.getRatio());
        width = (int)UserDisplay.getWidth();
        peekWidth = (int)(peekHeight * 2.33);

        behavior.setPeekHeight(peekHeight);

        onBottomSheetInteractionListener = (OnBottomSheetInteractionListener) context;
    }

    @Override
    public void onStateChanged( View bottomSheet, int newState)
    {
        state = newState;
    }
    // 선형 보간법을 통해 offset이 0.1 까지 갈 때까지 선형적으로 높이가 줄어드는 형태로 만들어야 한다.
    @Override
    public void onSlide( View bottomSheet, float slideOffset)
    {
        float interval = 1 - slideOffset;

        if(slideOffset <= 0.1)
        {
            player.getLayoutParams().width = (int) LinearInterpolation.Lerp(width, peekWidth, 1 - slideOffset * 10);
        }
        else
        {
            player.getLayoutParams().width = width;
        }

        if(slideOffset >= 0)
        {
            player.getLayoutParams().height = (int) LinearInterpolation.Lerp(height, peekHeight, interval);
            player.setLayoutParams(player.getLayoutParams());

            description.setAlpha(slideOffset);
            bottomSheet.setAlpha(1);
        }
        else
        {
            bottomSheet.setAlpha(1 + slideOffset);
        }

        onBottomSheetInteractionListener.onSlide(bottomSheet, slideOffset);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        /*
         * 플레이어를 제외한 나머지를 터치했을 경우 드래그가 불가능한 상태로 설정한다.
         */

        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN :

                if(view.getId() == R.id.bottomsheetdialogfragment_video_player)
                {
                    behavior.setDraggable(true);
                }
                else
                {
                    behavior.setDraggable(false);
                }

                break;
            case MotionEvent.ACTION_UP :

                behavior.setDraggable(true);

                break;
            default :
                // 의도하지 않은 터치 처리
                break;
        }

        return false;
    }
}
