package com.example.baetube;

import android.view.MotionEvent;
import android.view.View;

public interface OnBottomSheetInteractionListener
{
    public void onSlide(View bottomSheet, float slideOffset);
}
