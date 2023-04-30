package com.example.baetube.bottomsheetdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.view.View;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.activity.MainActivity;

public class VideoOptionFragment extends BaseOptionFragment implements OnRecyclerViewClickListener
{
    private static TypedArray resources;
    private static String options[];
    private Context context;

    public VideoOptionFragment(Context context)
    {
        this.context = context;
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
                ((MainActivity)context).requestSelectPlaylist();
                break;

            case 2 :
                VideoReportFragment videoReportFragment = new VideoReportFragment(context);
                videoReportFragment.show(getParentFragmentManager(), videoReportFragment.getTag());
                break;

        }

        dismiss();
    }

    @Override
    public void onDismiss( DialogInterface dialog)
    {
        ((MainActivity)context).setManagedVideoItem(null);
        super.onDismiss(dialog);
    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }

}
