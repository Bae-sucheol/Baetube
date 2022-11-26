package com.example.baetube.bottomsheetdialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class CommunityOptionFragment extends BaseOptionFragment implements OnRecyclerViewClickListener
{
    private static TypedArray resources;
    private static String options[];

    public CommunityOptionFragment(Context context)
    {
        resources = context.getResources().obtainTypedArray(R.array.community_option_resources);
        options = context.getResources().getStringArray(R.array.community_option_texts);

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
