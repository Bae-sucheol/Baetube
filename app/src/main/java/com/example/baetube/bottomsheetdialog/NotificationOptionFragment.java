package com.example.baetube.bottomsheetdialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;

import com.example.baetube.OnDialogInteractionListener;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class NotificationOptionFragment extends BaseOptionFragment implements OnRecyclerViewClickListener
{
    private static TypedArray resources;
    private static String options[];
    private Context context;
    private OnDialogInteractionListener onDialogInteractionListener;

    public NotificationOptionFragment(Context context, OnDialogInteractionListener onDialogInteractionListener)
    {
        this.context = context;
        this.onDialogInteractionListener = onDialogInteractionListener;
        resources = context.getResources().obtainTypedArray(R.array.notification_option_resources);
        options = context.getResources().getStringArray(R.array.notification_option_texts);

        super.addItem(resources, options);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (position)
        {
            case 0 :
                // 삭제
                onDialogInteractionListener.onDeleteNotification();
                break;
        }

        dismiss();
    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }
}
