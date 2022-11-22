package com.example.baetube.fragment.bottomsheetdialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baetube.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ReplyBottomSheetDialogFragment extends BottomSheetDialogFragment
{
    private Context context;

    public ReplyBottomSheetDialogFragment(Context context)
    {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.bottomsheetdialogfragment_reply, container, false);

        return view;
    }

}
