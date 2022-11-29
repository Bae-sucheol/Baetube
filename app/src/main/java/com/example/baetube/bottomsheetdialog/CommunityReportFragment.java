package com.example.baetube.bottomsheetdialog;

import android.content.Context;
import android.widget.RadioGroup;

import com.example.baetube.R;

public class CommunityReportFragment extends BaseReportFragment
{
    private int selectId;

    public CommunityReportFragment(Context context)
    {
        super();

        String reportTitle = context.getString(R.string.report_community_text_title);
        String reportContents[] = context.getResources().getStringArray(R.array.report_community_texts);

        super.initDialog(reportTitle, reportContents);
    }

    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {
        selectId = i;
    }
}
