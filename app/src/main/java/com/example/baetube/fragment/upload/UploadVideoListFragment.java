package com.example.baetube.fragment.upload;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.OnUploadDataListener;
import com.example.baetube.R;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoListAdapter;

import java.io.File;
import java.util.ArrayList;

public class UploadVideoListFragment extends Fragment implements OnRecyclerViewClickListener
{
    private View view;

    private RecyclerView recyclerView;
    private RecyclerViewVideoListAdapter adapter;
    private ArrayList<File> list = new ArrayList<>();

    // 액티비티와 통신하기 위한 인터페이스
    private OnUploadDataListener onUploadDataListener;

    public UploadVideoListFragment(OnUploadDataListener onUploadDataListener)
    {
        this.onUploadDataListener = onUploadDataListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_upload_video_list, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        toolbar.setTitle(getString(R.string.fragment_upload_video_list_toolbar_title));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //test();
        //list = getVideoList();

        recyclerView = view.findViewById(R.id.fragment_upload_video_list_recyclerview);
        adapter = new RecyclerViewVideoListAdapter(list);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter.setOnRecyclerViewClickListener(this);

        //getVideo();
        //adapter.setUp(getVideo());

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu( Menu menu,  MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_none, menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item)
    {
        getActivity().onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) ((ImageView)view).getDrawable();
        Bitmap thumbnail = bitmapDrawable.getBitmap();

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_upload_layout_main, new UploadVideoSelectFragment(list.get(position), onUploadDataListener));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }

    @Override
    public void onCastVoteOption(VoteDTO voteData, boolean isCancel)
    {

    }

    public void setVideoList(ArrayList<File> videoList)
    {
        list = videoList;
    }
}