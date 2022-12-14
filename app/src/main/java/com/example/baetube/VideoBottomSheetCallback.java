package com.example.baetube;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.shape.InterpolateOnScrollPositionChangeHelper;

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

        height = (int)(UserDisplay.getWidth() * UserDisplay.getRatio());
        width = (int)UserDisplay.getWidth();
        peekWidth = (int)(peekHeight * 2.33);

        behavior.setPeekHeight(peekHeight);

        onBottomSheetInteractionListener = (OnBottomSheetInteractionListener) context;
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
         * description 레이아웃만 터치 리스너를 등록했기 때문에
         * 따로 id를 필터링할 필요가 없다.
         */
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN :

                behavior.setDraggable(false);

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
