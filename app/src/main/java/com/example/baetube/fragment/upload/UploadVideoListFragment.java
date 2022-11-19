package com.example.baetube.fragment.upload;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.baetube.GridViewAdapter;
import com.example.baetube.R;
import com.example.baetube.dto.VideoDTO;

import java.util.ArrayList;

public class UploadVideoListFragment extends Fragment
{
    private View view;

    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private ArrayList<VideoDTO> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_upload_video_list, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(false);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        toolbar.setTitle(getString(R.string.fragment_upload_video_list_toolbar_title));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        test();

        gridView = view.findViewById(R.id.fragment_upload_video_gridview);
        gridViewAdapter = new GridViewAdapter(list);
        gridView.setAdapter(gridViewAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    private void test()
    {
        for (int i = 0; i < 30; i++)
        {
            VideoDTO videoDTO = new VideoDTO();

            videoDTO.setVideoId(i);

            list.add(videoDTO);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_none, menu);
    }
}