package com.example.baetube.bottomsheetdialog;

import android.content.Context;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.baetube.R;

public class VideoReportFragment extends BaseReportFragment
{
    private int selectId;

    public VideoReportFragment(Context context)
    {
        super();

        String reportTitle = context.getString(R.string.report_video_text_title);
        String reportContents[] = context.getResources().getStringArray(R.array.report_video_texts);

        super.initDialog(reportTitle, reportContents);
    }

    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {
        selectId = i;
    }
}
