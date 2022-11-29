package com.example.baetube.bottomsheetdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.baetube.R;
import com.example.baetube.UserDisplay;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BaseReportFragment extends BottomSheetDialogFragment implements RadioGroup.OnCheckedChangeListener
{
    private View view;

    private TextView title;
    private RadioGroup radioGroup;
    private RadioButton[] radioButtons;

    private String reportTitle;
    private String reportContents[];

    public BaseReportFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_base_report, container, false);

        title = view.findViewById(R.id.fragment_base_report_text_title);
        radioGroup = view.findViewById(R.id.fragment_base_report_radio_group);

        radioGroup.setOnCheckedChangeListener(this);

        setTitle(reportTitle);
        setRadioButtons(reportContents);

        // Inflate the layout for this fragment
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialogInterface)
            {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                setRatio(bottomSheetDialog);
            }
        });

        return dialog;
    }

    public void setRatio(BottomSheetDialog bottomSheetDialog)
    {
        FrameLayout bottomSheet = (FrameLayout)
                bottomSheetDialog.findViewById(R.id.design_bottom_sheet);

        bottomSheet.setBackgroundResource(R.drawable.bottomsheetdialog_border);
        bottomSheet.setY((int)UserDisplay.getHeight() / 2 - bottomSheet.getHeight() / 2);

        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.width = (int)(UserDisplay.getWidth() * 0.8);
        bottomSheet.setLayoutParams(layoutParams);

        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void setTitle(String reportTitle)
    {
        title.setText(reportTitle);
    }

    public void setRadioButtons(String reportContents[])
    {
        int length = reportContents.length;

        radioButtons = new RadioButton[length];

        for (int i = 0; i < length; i++)
        {
            RadioButton radioButton = new RadioButton(getContext());

            radioButton.setText(reportContents[i]);
            radioButtons[i] = radioButton;
            radioGroup.addView(radioButtons[i]);
        }

    }

    public void initDialog(String reportTitle, String reportContents[])
    {
        this.reportTitle = reportTitle;
        this.reportContents = reportContents;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {

    }
}