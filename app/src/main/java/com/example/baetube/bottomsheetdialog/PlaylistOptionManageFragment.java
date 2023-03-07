package com.example.baetube.bottomsheetdialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;

import com.example.baetube.OnDialogInteractionListener;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class PlaylistOptionManageFragment extends BaseOptionFragment implements OnRecyclerViewClickListener
{
    private static TypedArray resources;
    private static String options[];
    private int position;

    private OnDialogInteractionListener onDialogInteractionListener;

    public PlaylistOptionManageFragment(Context context, int position, OnDialogInteractionListener onDialogInteractionListener)
    {
        this.position = position;
        this.onDialogInteractionListener = onDialogInteractionListener;
        resources = context.getResources().obtainTypedArray(R.array.playlist_option_manage_resources);
        options = context.getResources().getStringArray(R.array.playlist_option_manage_texts);

        super.addItem(resources, options);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (position)
        {
            case 0 :
                //

                onDialogInteractionListener.onDeletePlaylistItem(position);
                dismiss();

                break;

            case 1 :
                //
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }

}
