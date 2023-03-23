package com.example.baetube;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class KeyboardState extends PopupWindow implements ViewTreeObserver.OnGlobalLayoutListener
{
    private Activity activity;
    private View rootView;
    private HeightListener heightListener;
    private int heightMax;

    public KeyboardState setHeightListener(HeightListener listener)
    {
        this.heightListener = listener;
        return this;
    }

    public KeyboardState(Activity activity)
    {
        super(activity);
        this.activity = activity;

        rootView = new View(activity);
        setContentView(rootView);

        rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        setBackgroundDrawable(new ColorDrawable(0));

        setWidth(0);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);

        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
    }

    public KeyboardState init()
    {
        if (!isShowing()){
            final View view = activity.getWindow().getDecorView();
            view.post(new Runnable() {
                @Override
                public void run() {
                    showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
                }
            });
        }
        return this;
    }

    @Override
    public void onGlobalLayout()
    {
        Rect rect = new Rect();
        rootView.getWindowVisibleDisplayFrame(rect);
        if (rect.bottom>heightMax){
            heightMax = rect.bottom;
        }

        int keyboardHeight = heightMax - rect.bottom;
        if(heightListener != null){
            heightListener.onHeightChanged(keyboardHeight);
        }
    }

    public interface HeightListener
    {
        void onHeightChanged(int height);
    }
}
