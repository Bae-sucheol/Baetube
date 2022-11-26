package com.example.baetube.bottomsheetdialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class PlaylistVideoOptionManageFragment extends BaseOptionFragment implements OnRecyclerViewClickListener
{
    private static TypedArray resources;
    private static String options[];

    public PlaylistVideoOptionManageFragment(Context context)
    {
        resources = context.getResources().obtainTypedArray(R.array.playlist_video_option_manage_resources);
        options = context.getResources().getStringArray(R.array.playlist_video_option_manage_texts);

        super.addItem(resources, options);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (position)
        {
            case 0 :
                //
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