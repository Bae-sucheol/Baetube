package com.example.baetube.fragment.modify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.baetube.OnModifyListener;
import com.example.baetube.R;
import com.example.baetube.Spinner.SpinnerDropdownAdapter;
import com.example.baetube.Spinner.SpinnerItem;
import com.example.baetube.recyclerview.item.RecyclerViewPlaylistItem;

import java.util.ArrayList;

public class ModifyPlaylistFragment extends Fragment
{
    private View view;

    private ImageView thumbnail;
    private EditText playlistName;
    private Spinner spinner;
    private ArrayList<SpinnerItem> list = new ArrayList<>();
    private SpinnerDropdownAdapter adapter;
    private RecyclerViewPlaylistItem recyclerViewPlaylistItem;
    private static Integer value[] = {1, null, 0};

    private OnModifyListener onModifyListener;

    public ModifyPlaylistFragment(RecyclerViewPlaylistItem recyclerViewPlaylistItem, OnModifyListener onModifyListener)
    {
        this.recyclerViewPlaylistItem = recyclerViewPlaylistItem;
        this.onModifyListener = onModifyListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_modify_playlist, container, false);

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

        thumbnail = view.findViewById(R.id.fragment_playlist_modify_image_thumbnail);
        playlistName = view.findViewById(R.id.fragment_playlist_modify_edit_title);
        spinner = view.findViewById(R.id.fragment_playlist_modify_spinner);
        adapter = new SpinnerDropdownAdapter(activity, R.layout.spinner_dropdown, list);
        spinner.setAdapter(adapter);

        playlistName.setText(recyclerViewPlaylistItem.getPlaylistDTO().getName());

        Glide.with(getContext())
                .asBitmap()
                .load("http://192.168.0.4:9090/Baetube_backEnd/api/image/thumbnail/" + recyclerViewPlaylistItem.getPlaylistDTO().getThumbnail() + ".jpg") // or URI/path
                .error(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_account_circle_24))
                .override(thumbnail.getWidth(), thumbnail.getHeight())
                .centerCrop()
                .into(thumbnail);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_sub, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home :
                try
                {
                    getParentFragmentManager().popBackStack();

                    recyclerViewPlaylistItem.getPlaylistDTO().setVisible(value[spinner.getSelectedItemPosition()]);
                    recyclerViewPlaylistItem.getPlaylistDTO().setName(playlistName.getText().toString());

                    onModifyListener.OnModifyPlaylist(recyclerViewPlaylistItem);
                }
                catch (IllegalStateException e)
                {

                }

                break;
        }
        return super.onOptionsItemSelected(item);
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