package com.example.baetube.fragment.modify;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.OkHttpUtil;
import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.OnSetFragmentListener;
import com.example.baetube.PreferenceManager;
import com.example.baetube.R;
import com.example.baetube.activity.MainActivity;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.set.SetAgeFragment;
import com.example.baetube.fragment.set.SetDescriptionFragment;
import com.example.baetube.fragment.set.SetLocationFragment;
import com.example.baetube.fragment.set.SetPublicFragment;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

public class ModifyVideoFragment extends Fragment implements View.OnClickListener
{
    private View view;
    private OnCallbackResponseListener onCallbackResponseListener;
    private OkHttpUtil okHttpUtil;
    private OnSetFragmentListener onSetFragmentListener;

    private ImageView thumbnail;
    private ImageView changeThumbnail;
    private EditText editTitle;
    private TextView textCount;
    private TextView description;
    private TextView age;
    private ConstraintLayout layoutDescription;
    private ConstraintLayout layoutVisible;
    private ConstraintLayout layoutAge;
    private ConstraintLayout layoutLocation;
    private ConstraintLayout layoutAddPlaylist;

    private VideoDTO sourceVideoData;
    private VideoDTO currentVideoData;

    public ModifyVideoFragment(OnCallbackResponseListener onCallbackResponseListener, Integer videoId)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
        sourceVideoData = new VideoDTO();
        sourceVideoData.setVideoId(videoId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_modify_video, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        //toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        thumbnail = view.findViewById(R.id.fragment_modify_video_image_thumbnail);
        changeThumbnail = view.findViewById(R.id.fragment_modify_video_button_change_thumbnail);
        editTitle = view.findViewById(R.id.fragment_modify_video_edit_title);
        textCount = view.findViewById(R.id.fragment_modify_video_text_count_cur);
        description = view.findViewById(R.id.fragment_modify_video_text_description);
        age = view.findViewById(R.id.fragment_modify_video_text_age);
        layoutDescription = view.findViewById(R.id.fragment_modify_video_layout_description);
        layoutVisible = view.findViewById(R.id.fragment_modify_video_layout_visible);
        layoutAge = view.findViewById(R.id.fragment_modify_video_layout_age);
        layoutLocation = view.findViewById(R.id.fragment_modify_video_layout_location);
        layoutAddPlaylist = view.findViewById(R.id.fragment_modify_video_layout_add_playlist);

        changeThumbnail.setOnClickListener(this);
        layoutDescription.setOnClickListener(this);
        layoutVisible.setOnClickListener(this);
        layoutAge.setOnClickListener(this);
        layoutLocation.setOnClickListener(this);
        layoutAddPlaylist.setOnClickListener(this);

        setOnFragmentListener();
        requestVideoData();

        // Inflate the layout for this fragment
        return view;
    }

    private void requestVideoData()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_video_data) + sourceVideoData.getVideoId();
        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_VIDEO_DATA);
        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    public void setImage(File file)
    {
        Glide.with(getContext()).asBitmap().load(file.getAbsolutePath()).override(thumbnail.getWidth(), thumbnail.getHeight()).centerCrop()
                .into(new CustomTarget<Bitmap>()
                {

                    @Override
                    public void onResourceReady(Bitmap resource, @Nullable Transition<? super Bitmap> transition)
                    {
                        thumbnail.setImageBitmap(resource);
                        currentVideoData.setThumbnail(file.getPath());
                        PreferenceManager.setString(getContext().getApplicationContext(), PreferenceManager.PREFERENCES_THUMBNAIL, file.getPath());
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder)
                    {

                    }
                });
    }

    public void setVideoData(VideoDTO data)
    {
        sourceVideoData = data;
        currentVideoData = new VideoDTO();
        currentVideoData.setVideoId(data.getVideoId());
        currentVideoData.setContentsId(data.getContentsId());
        currentVideoData.setChannelId(data.getChannelId());
        currentVideoData.setThumbnail(data.getThumbnail());
        currentVideoData.setInfo(data.getInfo());
        currentVideoData.setLocation(data.getLocation());
        currentVideoData.setAge(data.getAge());
        currentVideoData.setVisible(data.getVisible());
        currentVideoData.setTitle(data.getTitle());

        editTitle.setText(data.getTitle());

        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Glide.with(getContext())
                        .asBitmap()
                        .load(getContext().getString(R.string.api_url_image_thumbnail) + data.getThumbnail() + ".jpg") // or URI/path
                        .error(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_account_circle_24))
                        .override(thumbnail.getWidth(), thumbnail.getHeight())
                        .centerCrop()
                        .into(thumbnail);

                description.setText(data.getInfo());
            }
        });

    }

    private void requestUpdateVideo()
    {
        boolean isChanged = false;

        currentVideoData.setTitle(editTitle.getText().toString());

        // 채널 이름이 변경되었는지 확인
        if(!sourceVideoData.getTitle().equals(currentVideoData.getTitle()))
        {
            isChanged = true;
        }
        if(!sourceVideoData.getInfo().equals(currentVideoData.getInfo()))
        {
            isChanged = true;
        }
        if(!sourceVideoData.getThumbnail().equals(currentVideoData.getThumbnail()))
        {
            isChanged = true;
        }
        if(!sourceVideoData.getVisible().equals(currentVideoData.getVisible()))
        {
            isChanged = true;
        }
        if(!sourceVideoData.getAge().equals(currentVideoData.getAge()))
        {
            isChanged = true;
        }
        if(!sourceVideoData.getLocation().equals(currentVideoData.getLocation()))
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

        String url = getString(R.string.api_url_video_update);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_UPDATE_VIDEO);

        okHttpUtil.sendPostRequest(currentVideoData, url, returnableCallback);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fragment_modify_video_button_change_thumbnail :

                // 비디오 썸네일 선택
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                MainActivity activity = (MainActivity)getActivity();
                activity.activityResultLauncher.launch(intent);

                break;
            case R.id.fragment_modify_video_layout_description :

                // 동영상 설명 변경
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.activity_main_layout, new SetDescriptionFragment(onSetFragmentListener, currentVideoData.getInfo()));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.fragment_modify_video_layout_visible :

                // 동영상 공개상태 변경.
                fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.activity_main_layout, new SetPublicFragment(onSetFragmentListener));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.fragment_modify_video_layout_age :

                // 동영상 나이 변경.
                fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.activity_main_layout, new SetAgeFragment(onSetFragmentListener));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.fragment_modify_video_layout_location :

                // 동영상 위치 변경.
                fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.activity_main_layout, new SetLocationFragment(onSetFragmentListener));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.fragment_modify_video_layout_add_playlist :

                //

                break;
        }
    }

    private void setOnFragmentListener()
    {
        onSetFragmentListener = new OnSetFragmentListener()
        {
            @Override
            public void onResponseDescription(String str)
            {
                currentVideoData.setInfo(str);
                description.setText(str);
            }

            @Override
            public void onResponsePublic(Integer value)
            {
                currentVideoData.setVisible(value);
            }

            @Override
            public void onResponseLocation(String str)
            {
                currentVideoData.setLocation(str);
            }

            @Override
            public void onResponseAge(Integer value)
            {
                currentVideoData.setAge(value);
            }

            @Override
            public void onResponseVote(VoteDTO vote, List<VoteDTO> voteOptions)
            {

            }

            @Override
            public void onResponseCategory(String str, int position)
            {

            }

            @Override
            public void onResponseCalendar(int year, int month, int dayOfMonth)
            {

            }
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_none, menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home :

                if(this.isVisible())
                {
                    requestUpdateVideo();
                }

                getActivity().onBackPressed();

                break;
        }
        return super.onOptionsItemSelected(item);
    }


}