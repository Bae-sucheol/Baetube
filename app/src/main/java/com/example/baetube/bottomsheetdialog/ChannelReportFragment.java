package com.example.baetube.bottomsheetdialog;

import android.content.Context;
import android.widget.RadioGroup;

import com.example.baetube.R;

public class ChannelReportFragment extends BaseReportFragment
{
    private int selectId;

    public ChannelReportFragment(Context context)
    {
        super();

        String reportTitle = context.getString(R.string.report_channel_text_title);
        String reportContents[] = context.getResources().getStringArray(R.array.report_channel_texts);

        super.initDialog(reportTitle, reportContents);
    }

    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {
        selectId = i;
    }
}
