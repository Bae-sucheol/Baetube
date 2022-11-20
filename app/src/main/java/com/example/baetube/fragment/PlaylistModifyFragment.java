package com.example.baetube.fragment;

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
import android.widget.Spinner;

import com.example.baetube.R;
import com.example.baetube.Spinner.SpinnerDropdownAdapter;
import com.example.baetube.Spinner.SpinnerItem;

import java.util.ArrayList;

public class PlaylistModifyFragment extends Fragment
{
    private View view;

    private Spinner spinner;
    private ArrayList<SpinnerItem> list = new ArrayList<>();
    private SpinnerDropdownAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_playlist_modify, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        toolbar.setTitle(getString(R.string.fragment_playlist_modify_toolbar_title));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        test();

        spinner = view.findViewById(R.id.fragment_playlist_modify_spinner);
        adapter = new SpinnerDropdownAdapter(activity, R.layout.spinner_dropdown, list);
        spinner.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_sub, menu);
    }

    private void test()
    {
        int images[] = {R.drawable.ic_baseline_public_24, R.drawable.ic_baseline_link_24, R.drawable.ic_baseline_lock_24};
        String titles[] =
                {
                    getString(R.string.visible_text_public),
                    getString(R.string.visible_text_link),
                    getString(R.string.visible_text_lock)
                };
        String descriptions[] =
                {
                        getString(R.string.visible_text_public_description),
                        getString(R.string.visible_text_link_description),
                        getString(R.string.visible_text_lock_description)
                };

        for (int i = 0; i < 3; i++)
        {
            SpinnerItem item = new SpinnerItem();
            item.setImage(images[i]);
            item.setTitle(titles[i]);
            item.setDescription(descriptions[i]);

            list.add(item);
        }
    }

}