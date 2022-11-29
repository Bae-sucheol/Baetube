package com.example.baetube.bottomsheetdialog;

import android.content.Context;
import android.widget.RadioGroup;

import com.example.baetube.R;

public class ReplyReportFragment extends BaseReportFragment
{
    private int selectId;

    public ReplyReportFragment(Context context)
    {
        super();

        String reportTitle = context.getString(R.string.report_reply_text_title);
        String reportContents[] = context.getResources().getStringArray(R.array.report_reply_texts);

        super.initDialog(reportTitle, reportContents);
    }

    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {
        selectId = i;
    }
}
