package com.example.baetube.bottomsheetdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baetube.OnSetFragmentListener;
import com.example.baetube.R;
import com.example.baetube.UserDisplay;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SetCalendarFragment extends BottomSheetDialogFragment implements View.OnClickListener, DatePicker.OnDateChangedListener
{
    private View view;
    private TextView title;
    private TextView buttonAdd;
    private TextView buttonClose;
    private DatePicker datePicker;

    private Context context;

    private String dialogTitle;
    private OnSetFragmentListener onSetFragmentListener;
    private int year;
    private int month;
    private int dayOfMonth;

    public SetCalendarFragment(Context context, OnSetFragmentListener onSetFragmentListener)
    {
        this.context = context;
        this.onSetFragmentListener = onSetFragmentListener;

        dialogTitle = context.getString(R.string.set_calendar_text_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_set_calendar, container, false);

        title = view.findViewById(R.id.fragment_set_calendar_text_title);
        buttonAdd = view.findViewById(R.id.fragment_set_calendar_text_button_add);
        buttonClose = view.findViewById(R.id.fragment_set_calendar_text_button_close);
        datePicker = view.findViewById(R.id.fragment_set_date_picker);

        buttonAdd.setOnClickListener(this);
        buttonClose.setOnClickListener(this);
        datePicker.setOnDateChangedListener(this);


        setTitle(dialogTitle);

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
        bottomSheet.setY((int) UserDisplay.getHeight() / 2 - bottomSheet.getHeight() / 2);

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

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fragment_set_calendar_text_button_add :

                onSetFragmentListener.onResponseCalendar(year, month, dayOfMonth);

                dismiss();

                break;
            case R.id.fragment_set_calendar_text_button_close :

                dismiss();

                break;
        }
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth)
    {
        this.year = year;
        this.month = month + 1;
        this.dayOfMonth = dayOfMonth;
    }
}
