package com.example.baetube.bottomsheetdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baetube.R;
import com.example.baetube.UserDisplay;
import com.example.baetube.activity.MainActivity;
import com.example.baetube.dto.PlaylistDTO;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AddPlaylistFragment extends BottomSheetDialogFragment implements View.OnClickListener
{
    private View view;
    private Context context;

    private TextView title;
    private LinearLayout layoutButtonGroup;
    private CheckBox[] checkBoxes;
    private TextView buttonAdd;
    private TextView buttonClose;

    private String reportTitle;
    private String reportContents[];
    private List<PlaylistDTO> playlistList;
    private List<PlaylistDTO> checkedList;

    public AddPlaylistFragment(Context context, List<PlaylistDTO> playlistList)
    {
        this.context = context;
        this.playlistList = playlistList;

        String reportTitle = context.getString(R.string.add_playlist_text_title);
        // 좋아요를 누른 동영상을 제외해야 하기 때문에 -1;
        String reportContents[] = new String[playlistList.size() - 1];

        int index = 0;

        // 좋아요를 누른 동영상 재생목록을 삭제한다.
        for (int i = 0; i < playlistList.size(); i++)
        {
            if(playlistList.get(i).getVisible() == 3 && playlistList.get(i).getName().equals("좋아요를 누른 동영상"))
            {
                index = -1;
                continue;
            }

            reportContents[i + index] = playlistList.get(i).getName();
        }

        initDialog(reportTitle, reportContents);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_add_playlist, container, false);

        title = view.findViewById(R.id.fragment_add_playlist_text_title);
        layoutButtonGroup = view.findViewById(R.id.fragment_add_playlist_layout_button_group);
        buttonAdd = view.findViewById(R.id.fragment_add_playlist_text_button_add);
        buttonClose = view.findViewById(R.id.fragment_add_playlist_text_button_close);

        buttonAdd.setOnClickListener(this);
        buttonClose.setOnClickListener(this);

        setTitle(reportTitle);
        setRadioButtons(reportContents);

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

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fragment_add_playlist_text_button_add :

                checkedList = new ArrayList<>();

                for (int i = 0; i < checkBoxes.length; i++)
                {
                    if(checkBoxes[i].isChecked())
                    {
                        checkedList.add(playlistList.get(i));
                    }
                }

                if(!checkedList.isEmpty())
                {
                    ((MainActivity)context).requestAddPlaylist(checkedList);
                }

                dismiss();

                break;
            case R.id.fragment_add_playlist_text_button_close :

                dismiss();

                break;
        }
    }
}