package com.example.baetube.bottomsheetdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnDialogInteractionListener;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.UserDisplay;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewOptionAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewOptionItem;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class PlayerOptionFragment extends BottomSheetDialogFragment implements OnRecyclerViewClickListener
{
    private View view;

    // 제목을 출력하는 뷰
    private TextView textTitle;
    // 옵션을 표현해주는 리사이클러뷰, 어뎁터, 리스트
    private RecyclerView recyclerView;
    private ArrayList<RecyclerViewOptionItem> list;
    private RecyclerViewOptionAdapter adapter;
    // 제목을 저장할 문자열 변수
    private String title;

    private static TypedArray resources;
    private static String options[];

    // 선택한 화질을 전달하기 위한 인터페이스
    private OnDialogInteractionListener onDialogInteractionListener;

    public PlayerOptionFragment(Context context, OnDialogInteractionListener onDialogInteractionListener, String resolution)
    {
        list = new ArrayList<>();
        resources = context.getResources().obtainTypedArray(R.array.player_option_resources);
        options = context.getResources().getStringArray(R.array.player_option_texts);
        this.onDialogInteractionListener = onDialogInteractionListener;
        setCurrentResolution(resources, options, resolution);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_base_option, container, false);

        textTitle = view.findViewById(R.id.fragment_base_option_text_title);

        recyclerView = view.findViewById(R.id.fragment_option_recyclerview);
        adapter = new RecyclerViewOptionAdapter(list);
        adapter.setOnRecyclerViewClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if(title == null)
        {
            textTitle.setVisibility(View.GONE);
        }
        textTitle.setText(title);

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

    private void setCurrentResolution(TypedArray resources, String options[], String resolution)
    {
        String resolutions[] = {"1080", "720", "480"};

        for (int i = 0; i < resolutions.length; i++)
        {
            if(resolutions[i].equals(resolution))
            {
                addItem(resources, options, i);
                break;
            }
        }
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

    public void addItem(TypedArray resources, String options[], int index)
    {

        for (int i = 0; i < resources.length(); i++)
        {
            RecyclerViewOptionItem item = new RecyclerViewOptionItem();

            if(i == index)
            {
                item.setResource(resources.getResourceId(i, 0));
            }
            else
            {
                item.setResource(0);
            }

            item.setOption(options[i]);

            list.add(item);
        }

    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Override
    public void onItemClick(View view, int position)
    {
        onDialogInteractionListener.onSetVideoResolution(position);
        dismiss();
    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }

    @Override
    public void onCastVoteOption(VoteDTO voteData, boolean isCancel)
    {

    }

}
