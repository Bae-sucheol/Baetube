package com.example.baetube.bottomsheetdialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.view.View;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.activity.MainActivity;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

import java.util.ArrayList;

public class VideoOptionManageFragment extends BaseOptionFragment implements OnRecyclerViewClickListener
{
    private static TypedArray resources;
    private static String options[];
    private Context context;
    private RecyclerViewVideoAdapter recyclerViewVideoAdapter;
    private ArrayList<RecyclerViewVideoItem> list;
    private int manageItemPosition;

    public VideoOptionManageFragment(Context context, RecyclerViewVideoAdapter recyclerViewVideoAdapter, ArrayList<RecyclerViewVideoItem> list, int position)
    {
        this.context = context;
        this.recyclerViewVideoAdapter = recyclerViewVideoAdapter;
        this.list = list;
        this.manageItemPosition = position;

        resources = context.getResources().obtainTypedArray(R.array.video_option_manage_resources);
        options = context.getResources().getStringArray(R.array.video_option_manage_texts);

        super.addItem(resources, options);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        switch (position)
        {
            case 0 :
                // 재생목록에 저장
                // 추후 메소드의 파라미터로 해당 본인의 채널id를 넘겨야 한다.
                ((MainActivity)context).requestSelectPlaylist();
                dismiss();
                break;
            case 1 :
                // 수정
                ((MainActivity)context).commitModifyVideoFragment(list.get(manageItemPosition).getVideoDTO().getVideoId());
                dismiss();
                break;
            case 2 :
                // 삭제

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("삭제 경고")
                        .setMessage("정말로 이 동영상을 삭제하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                // 삭제 처리
                                ((MainActivity)context).requestDeleteVideo();
                                // 여기 문제있으니까 수정해야한다.
                                list.remove(manageItemPosition);
                                recyclerViewVideoAdapter.notifyItemRemoved(manageItemPosition);
                                dismiss();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                // 취소 처리
                                dismiss();
                            }
                        });

                builder.show();

                break;
            case 3 :
                // 취소
                dismiss();
                break;
        }
    }

    @Override
    public void onDismiss( DialogInterface dialog)
    {
        ((MainActivity)context).setManagedVideoItem(null);
        super.onDismiss(dialog);
    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }

}
