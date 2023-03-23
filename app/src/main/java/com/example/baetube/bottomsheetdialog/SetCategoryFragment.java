package com.example.baetube.bottomsheetdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baetube.OnSetFragmentListener;
import com.example.baetube.R;
import com.example.baetube.Spinner.SpinnerDropdownAdapter;
import com.example.baetube.Spinner.SpinnerItem;
import com.example.baetube.UserDisplay;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class SetCategoryFragment extends BottomSheetDialogFragment implements View.OnClickListener
{
    private View view;

    private TextView title;
    private TextView buttonAdd;
    private TextView buttonClose;

    private Context context;
    private Spinner spinner;
    private ArrayList<SpinnerItem> list;
    private SpinnerDropdownAdapter adapter;
    private String dialogTitle;
    private String options[];

    private OnSetFragmentListener onSetFragmentListener;

    public SetCategoryFragment(Context context, OnSetFragmentListener onSetFragmentListener)
    {
        this.context = context;
        this.onSetFragmentListener = onSetFragmentListener;

        dialogTitle = context.getString(R.string.set_category_text_title);
        options = context.getResources().getStringArray(R.array.category_option_texts);

        list = new ArrayList<>();

        for (int i = 0; i < options.length; i++)
        {
            SpinnerItem item = new SpinnerItem();
            item.setImage(0);
            item.setTitle(options[i]);
            item.setDescription("");

            list.add(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_set_category, container, false);

        title = view.findViewById(R.id.fragment_set_category_text_title);
        spinner = view.findViewById(R.id.fragment_set_category_spinner);
        buttonAdd = view.findViewById(R.id.fragment_set_category_text_button_add);
        buttonClose = view.findViewById(R.id.fragment_set_category_text_button_close);

        spinner = view.findViewById(R.id.fragment_set_category_spinner);
        adapter = new SpinnerDropdownAdapter(context, R.layout.spinner_dropdown, list, true);
        spinner.setAdapter(adapter);

        buttonAdd.setOnClickListener(this);
        buttonClose.setOnClickListener(this);

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
        bottomSheet.setY((int)UserDisplay.getHeight() / 2 - bottomSheet.getHeight() / 2);

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
            case R.id.fragment_set_category_text_button_add :

                onSetFragmentListener.onResponseCategory(options[spinner.getSelectedItemPosition()], spinner.getSelectedItemPosition() + 1);
                dismiss();

                break;
            case R.id.fragment_set_category_text_button_close :

                dismiss();

                break;
        }
    }

}
