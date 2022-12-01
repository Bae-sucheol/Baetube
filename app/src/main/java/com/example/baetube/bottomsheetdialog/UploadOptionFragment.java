package com.example.baetube.bottomsheetdialog;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.View;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.UploadType;
import com.example.baetube.activity.UploadActivity;

public class UploadOptionFragment extends BaseOptionFragment implements OnRecyclerViewClickListener
{

    private static TypedArray resources;
    private static String options[];

    public UploadOptionFragment(Context context)
    {
        resources = context.getResources().obtainTypedArray(R.array.upload_option_resources);
        options = context.getResources().getStringArray(R.array.upload_option_texts);

        String title = context.getString(R.string.upload_text_title);

        super.addItem(resources, options);
        super.setTitle(title);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        // Intent를 생성
        Intent intent = new Intent(getContext(), UploadActivity.class);

        // 업로드 타입에 맞게 인텐트에 정수형 데이터를 삽입
        switch (position)
        {
            case 0 :
                intent.putExtra("uploadType", UploadType.VIDEO);
                break;

            case 1 :
                intent.putExtra("uploadType", UploadType.COMMUNITY);
                break;

            default :
                // 의도하지 않은 다른 무언가를 클릭한 경우 에러를 날려줘야한다.
                break;
        }

        // 인텐트를 삽입하고 액티비티 실행.
        startActivity(intent);
        // 해당 옵션 다이얼로그 프래그먼트를 종료.
        this.dismiss();

    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }
}
