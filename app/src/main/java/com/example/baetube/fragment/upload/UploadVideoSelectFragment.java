package com.example.baetube.fragment.upload;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.baetube.FragmentTagUtil;
import com.example.baetube.OnUploadDataListener;
import com.example.baetube.R;

import java.io.File;

public class UploadVideoSelectFragment extends Fragment
{
    private View view;
    private File file;
    private Bitmap thumbnail;
    private VideoView videoView;

    // 액티비티와 통신하기 위한 인터페이스
    private OnUploadDataListener onUploadDataListener;

    public UploadVideoSelectFragment(File file, OnUploadDataListener onUploadDataListener)
    {
        this.file = file;
        this.onUploadDataListener = onUploadDataListener;
    }

    public UploadVideoSelectFragment(File file)
    {
        this.file = file;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_upload_video_select, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        toolbar.setTitle(getString(R.string.fragment_upload_video_list_toolbar_title));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        videoView = view.findViewById(R.id.fragment_upload_video_select_video);
        videoView.setVideoPath(file.getAbsolutePath());
        videoView.start();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu( Menu menu,  MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_only_next_button, menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home :

                getActivity().onBackPressed();

                break;

            case R.id.menu_toolbar_next :

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.activity_upload_layout_main, new UploadVideoInformationFragment(file, onUploadDataListener),
                        FragmentTagUtil.FRAGMENT_TAG_UPLOAD_VIDEO);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                onUploadDataListener.onResponseVideoFile(file);

                break;

            default :
                break;
        }


        return super.onOptionsItemSelected(item);
    }

}