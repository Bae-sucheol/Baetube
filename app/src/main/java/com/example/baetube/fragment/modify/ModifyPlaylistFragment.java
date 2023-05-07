package com.example.baetube.fragment.modify;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.OkHttpUtil;
import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.R;
import com.example.baetube.Spinner.SpinnerDropdownAdapter;
import com.example.baetube.Spinner.SpinnerItem;
import com.example.baetube.activity.MainActivity;
import com.example.baetube.dto.PlaylistDTO;

import java.util.ArrayList;

public class ModifyPlaylistFragment extends Fragment implements View.OnClickListener
{
    private View view;

    private ImageView thumbnail;
    private EditText playlistName;
    private Spinner spinner;
    private ArrayList<SpinnerItem> list = new ArrayList<>();
    private SpinnerDropdownAdapter adapter;
    private static Integer value[] = {1, null, 0};
    private OnCallbackResponseListener onCallbackResponseListener;
    private PlaylistDTO sourcePlaylistData;
    private PlaylistDTO currentPlaylistData;
    private OkHttpUtil okHttpUtil;

    public ModifyPlaylistFragment(OnCallbackResponseListener onCallbackResponseListener, Integer playlistId)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
        sourcePlaylistData = new PlaylistDTO();
        sourcePlaylistData.setPlaylistId(playlistId);
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

        initSpinner();

        thumbnail = view.findViewById(R.id.fragment_playlist_modify_image_thumbnail);
        playlistName = view.findViewById(R.id.fragment_playlist_modify_edit_title);

        spinner = view.findViewById(R.id.fragment_playlist_modify_spinner);
        adapter = new SpinnerDropdownAdapter(activity, R.layout.spinner_dropdown, list, false);
        spinner.setAdapter(adapter);

        thumbnail.setOnClickListener(this);

        requestPlaylistData();

        // Inflate the layout for this fragment
        return view;
    }

    private void requestPlaylistData()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_playlist_data) + sourcePlaylistData.getPlaylistId();
        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_PLAYLIST_DATA);
        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    public void setPlaylistData(PlaylistDTO data)
    {
        sourcePlaylistData = data;
        currentPlaylistData = new PlaylistDTO(data.getPlaylistId(), data.getChannelId(), data.getName(), data.getVisible(), data.getVideoCount(),
                data.getThumbnail());

        playlistName.setText(data.getName());

        // 아이템 선택..
        // spinner.setSelected();

        Glide.with(getContext())
                .asBitmap()
                .load(getContext().getString(R.string.api_url_image_thumbnail) + data.getThumbnail() + ".jpg") // or URI/path
                .error(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_account_circle_24))
                .override(thumbnail.getWidth(), thumbnail.getHeight())
                .centerCrop()
                .into(thumbnail);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,  MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_sub, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home :
                try
                {
                    getParentFragmentManager().popBackStack();
                    requestUpdatePlaylist();
                }
                catch (IllegalStateException e)
                {

                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initSpinner()
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

    private void requestUpdatePlaylist()
    {
        boolean isChanged = false;

        currentPlaylistData.setName(playlistName.getText().toString());

        // 채널 이름이 변경되었는지 확인
        if(!sourcePlaylistData.getName().equals(currentPlaylistData.getName()))
        {
            isChanged = true;
        }
        if(!sourcePlaylistData.getVisible().equals(currentPlaylistData.getVisible()))
        {
            isChanged = true;
        }
        if(!sourcePlaylistData.getThumbnail().equals(currentPlaylistData.getThumbnail()))
        {
            isChanged = true;
        }

        // 변경된 사항이 하나도 없다면 저장을 할 필요가 없으므로 return;
        if(!isChanged)
        {
            return;
        }

        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_playlist_update);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_UPDATE_PLAYLIST);

        okHttpUtil.sendPostRequest(currentPlaylistData, url, returnableCallback);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fragment_playlist_modify_image_thumbnail :

                // 썸네일 변경
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                MainActivity activity = (MainActivity)getActivity();
                activity.activityResultLauncher.launch(intent);

                break;
        }
    }

}