package com.example.baetube.bottomsheetdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.baetube.OnDialogInteractionListener;
import com.example.baetube.R;
import com.example.baetube.UserDisplay;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.Nullable;

public class AddVoteFragment extends BottomSheetDialogFragment implements View.OnClickListener
{
    private View view;

    // 추가, 취소 버튼 뷰(clickable textView)
    private TextView buttonAdd;
    private TextView buttonClose;
    // 투표 내용을 작성할 뷰
    private EditText comment;

    // 추가할 투표 내용을 전달하기 위한 인터페이스
    private OnDialogInteractionListener onDialogInteractionListener;

    public AddVoteFragment(OnDialogInteractionListener onDialogInteractionListener)
    {
        this.onDialogInteractionListener = onDialogInteractionListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_add_vote, container, false);

        buttonAdd = view.findViewById(R.id.fragment_add_vote_text_button_add);
        buttonClose = view.findViewById(R.id.fragment_add_vote_text_button_close);
        comment = view.findViewById(R.id.fragment_add_vote_edit_comment);

        buttonAdd.setOnClickListener(this);
        buttonClose.setOnClickListener(this);

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
        bottomSheet.setY((int) UserDisplay.getHeight() / 2 - bottomSheet.getHeight() / 2);

        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.width = (int)(UserDisplay.getWidth() * 0.8);
        bottomSheet.setLayoutParams(layoutParams);

        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onClick(View view)
    {
        // 신고를 하던 취소를 하던 결국 마지막에는 dismiss를 해야하므로
        // switch - case로 나누지 않고 추가 버튼을 눌렀을 경우 추가 기능만
        // 작성해주면 된다. 따라서 if 문으로 처리
        if(view.getId() == R.id.fragment_add_vote_text_button_add)
        {
            // 추가 기능 작성
            onDialogInteractionListener.onAddVoteResponse(comment.getText().toString());
        }

        this.dismiss();
    }
}