package com.example.baetube.bottomsheetdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.baetube.PublicState;
import com.example.baetube.R;
import com.example.baetube.UserDisplay;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddPlaylistFragment extends BottomSheetDialogFragment
{
    private View view;
    private Context context;

    private TextView title;
    private LinearLayout layoutButtonGroup;
    private CheckBox[] checkBoxes;

    private String reportTitle;
    private String reportContents[];

    public AddPlaylistFragment(Context context)
    {
        this.context = context;

        String reportTitle = context.getString(R.string.add_playlist_text_title);
        String reportContents[] = new String[] {"나중에 볼 동영상", "노래", "공부"};

        initDialog(reportTitle, reportContents);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_add_playlist, container, false);

        title = view.findViewById(R.id.fragment_add_playlist_text_title);
        layoutButtonGroup = view.findViewById(R.id.fragment_add_playlist_layout_button_group);

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
        bottomSheet.setY(bottomSheet.getY() - 50);

        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.width = (int)(UserDisplay.getWidth() * 0.9);
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

        checkBoxes = new CheckBox[length];

        for (int i = 0; i < length; i++)
        {
            CheckBox checkBox = new CheckBox(context);

            checkBox.setText(reportContents[i]);
            checkBoxes[i] = checkBox;
            layoutButtonGroup.addView(checkBoxes[i]);
        }

    }

    public void initDialog(String reportTitle, String reportContents[])
    {
        this.reportTitle = reportTitle;
        this.reportContents = reportContents;
    }
}