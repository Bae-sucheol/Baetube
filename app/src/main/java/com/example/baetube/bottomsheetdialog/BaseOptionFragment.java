package com.example.baetube.bottomsheetdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.R;
import com.example.baetube.UserDisplay;
import com.example.baetube.recyclerview.adapter.RecyclerViewOptionAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewOptionItem;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BaseOptionFragment extends BottomSheetDialogFragment
{
    private View view;

    private RecyclerView recyclerView;
    private ArrayList<RecyclerViewOptionItem> list = new ArrayList<>();
    private RecyclerViewOptionAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_baseoption, container, false);

        test();

        recyclerView = view.findViewById(R.id.fragment_option_recyclerview);
        adapter = new RecyclerViewOptionAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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

    private void test()
    {
        int resources[] = {R.drawable.ic_outline_watch_later_24, R.drawable.ic_baseline_library_add_24,
                R.drawable.ic_outline_error_outline_24};

        String options[] = {"나중에 볼 동영상에 저장", "재생목록에 저장", "신고"};

        for (int i = 0; i < 3; i++)
        {
            RecyclerViewOptionItem item = new RecyclerViewOptionItem();

            item.setResource(resources[i]);
            item.setOption(options[i]);

            list.add(item);
        }

    }

}