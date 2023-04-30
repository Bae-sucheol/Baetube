package com.example.baetube.bottomsheetdialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;

import com.example.baetube.OnDialogInteractionListener;
import com.example.baetube.R;

public class CommunityOptionManageFragment extends BaseOptionFragment
{
    private static TypedArray resources;
    private static String options[];
    private Context context;
    private OnDialogInteractionListener onDialogInteractionListener;

    public CommunityOptionManageFragment(Context context, OnDialogInteractionListener onDialogInteractionListener)
    {
        this.context = context;
        this.onDialogInteractionListener = onDialogInteractionListener;
        resources = context.getResources().obtainTypedArray(R.array.community_option_manage_resources);
        options = context.getResources().getStringArray(R.array.community_option_manage_texts);

        super.addItem(resources, options);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (position)
        {
            case 0 :
                // 수정

                onDialogInteractionListener.onModifyCommunity();

                break;

            case 1 :
                // 삭제

                onDialogInteractionListener.onDeleteCommunity();

                break;

            case 2 :
                // 취소

                break;
        }

        dismiss();
    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }
}
