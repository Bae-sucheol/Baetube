package com.example.baetube.bottomsheetdialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;

public class VideoOptionFragment extends BaseOptionFragment implements OnRecyclerViewClickListener
{
    private static TypedArray resources;
    private static String options[];

    public VideoOptionFragment(Context context)
    {
        resources = context.getResources().obtainTypedArray(R.array.video_option_resources);
        options = context.getResources().getStringArray(R.array.video_option_texts);

        super.addItem(resources, options);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (position)
        {
            case 0 :
                System.out.println("나중에 볼 동영상에 저장!");
                break;

            case 1 :
                System.out.println("재생목록에 저장!");
                break;

            case 2 :
                System.out.println("신고!");
                break;

        }
    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }

}
