package com.example.baetube.bottomsheetdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnDialogInteractionListener;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.UserDisplay;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewChannelAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ChannelSelectOptionFragment extends BottomSheetDialogFragment implements OnRecyclerViewClickListener
{
    private View view;

    // 제목을 출력하는 뷰
    private TextView textTitle;
    // 옵션을 표현해주는 리사이클러뷰, 어뎁터, 리스트
    private RecyclerView recyclerView;
    private ArrayList<ChannelDTO> list;
    private RecyclerViewChannelAdapter adapter;
    // 제목을 저장할 문자열 변수
    private String title;

    private OnDialogInteractionListener onDialogInteractionListener;

    public ChannelSelectOptionFragment(OnDialogInteractionListener onDialogInteractionListener, ArrayList<ChannelDTO> list)
    {
        this.onDialogInteractionListener = onDialogInteractionListener;
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_base_option, container, false);

        textTitle = view.findViewById(R.id.fragment_base_option_text_title);

        recyclerView = view.findViewById(R.id.fragment_option_recyclerview);
        adapter = new RecyclerViewChannelAdapter(list);
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

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch(view.getId())
        {
            case R.id.recyclerview_channel_image_option :

            case R.id.recyclerview_channel_image_profile :

            case R.id.recyclerview_channel_layout_information :

            case R.id.recyclerview_channel_text_button_sub :

                onDialogInteractionListener.onSelectChannel(position, list.get(position).getChannelId());

                break;
        }

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
