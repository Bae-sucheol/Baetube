package com.example.baetube.fragment.upload;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.RoundedDrawable;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewVideoListAdapter;

import java.util.ArrayList;

public class UploadVideoListFragment extends Fragment implements OnRecyclerViewClickListener
{
    private View view;

    private RecyclerView recyclerView;
    private RecyclerViewVideoListAdapter adapter;
    private ArrayList<Drawable> list = new ArrayList<>();

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

        test();

        recyclerView = view.findViewById(R.id.fragment_upload_video_list_recyclerview);
        adapter = new RecyclerViewVideoListAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter.setOnRecyclerViewClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    private void test()
    {
        Drawable drawable = getContext().getDrawable(R.drawable.ic_baseline_image_24);

        for (int i = 0; i < 30; i++)
        {
            list.add(drawable);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_none, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        getActivity().onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_upload_layout_main, new UploadVideoSelectFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }
}