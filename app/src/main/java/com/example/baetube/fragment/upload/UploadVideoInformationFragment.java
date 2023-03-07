package com.example.baetube.fragment.upload;

import android.content.Intent;
import android.content.res.Resources;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.baetube.OnSetFragmentListener;
import com.example.baetube.OnUploadDataListener;
import com.example.baetube.R;
import com.example.baetube.activity.UploadActivity;
import com.example.baetube.bottomsheetdialog.AddPlaylistFragment;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.set.SetAgeFragment;
import com.example.baetube.fragment.set.SetDescriptionFragment;
import com.example.baetube.fragment.set.SetLocationFragment;
import com.example.baetube.fragment.set.SetPublicFragment;

import java.io.File;
import java.util.List;

public class UploadVideoInformationFragment extends Fragment implements View.OnClickListener
{
    private View view;

    // 업로드할 동영상의 썸네일
    private ImageView thumbnail;
    // 동영상 썸네일을 변경하기 위한 버튼
    private ImageView buttonChangeThumbnail;
    // 채널 프로필 이미지를 출력하는 뷰
    private ImageView profile;
    // 채널 이름을 출력하는 뷰
    private TextView channelName;
    // 입력된 동영상 제목의 길이를 출력하는 뷰
    private TextView countCur;
    // 작성된 설명을 간략하게 출력하는 뷰
    private TextView description;
    // 설정된 공개 상태를 출력하는 뷰
    private TextView visible;
    // 동영상 제목을 입력하는 인풋
    private EditText title;
    /*
     * 설명, 공개, 위치, 재생목록에 추가 레이아웃으로 단순히
     * 클릭하여 해당 프래그먼트로 전환하기 위한 용도로 사용한다.
     */
    private ConstraintLayout layoutDescription;
    private ConstraintLayout layoutVisible;
    private ConstraintLayout layoutLocation;
    private ConstraintLayout layoutPlaylist;

    // 액티비티와 통신하기 위한 인터페이스
    private OnUploadDataListener onUploadDataListener;

    // Set 프래그먼트들과 통신 하기위한 인터페이스
    private OnSetFragmentListener onSetFragmentListener;

    // 위치 정보를 기록하기 위한 객체
    private String address;

    // 선택한 공개 여부를 저장하기 위한 객체
    private Integer selectedVisible;

    private File selectedFile;

    private Bitmap selectedThumbnail;

    public UploadVideoInformationFragment(File file, OnUploadDataListener onUploadDataListener)
    {
        this.selectedFile = file;
        this.onUploadDataListener = onUploadDataListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_upload_video_information, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        toolbar.setTitle(getString(R.string.fragment_upload_video_information_toolbar_title));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 요소 찾기
        thumbnail = view.findViewById(R.id.fragment_upload_video_information_image_thumbnail);
        buttonChangeThumbnail = view.findViewById(R.id.fragment_upload_video_information_image_button_change_thumbnail);
        profile = view.findViewById(R.id.fragment_upload_video_information_image_profile);
        channelName = view.findViewById(R.id.fragment_upload_video_information_text_channel_name);
        countCur = view.findViewById(R.id.fragment_upload_video_information_text_count_cur);
        description = view.findViewById(R.id.fragment_upload_video_information_text_description);
        visible = view.findViewById(R.id.fragment_upload_video_information_text_visible);
        title = view.findViewById(R.id.fragment_upload_video_information_edit_title);

        layoutDescription = view.findViewById(R.id.fragment_upload_video_information_layout_description);
        layoutVisible = view.findViewById(R.id.fragment_upload_video_information_layout_visible);
        layoutLocation = view.findViewById(R.id.fragment_upload_video_information_layout_location);
        layoutPlaylist = view.findViewById(R.id.fragment_upload_video_information_layout_playlist);

        // 클릭 리스너 등록
        buttonChangeThumbnail.setOnClickListener(this);
        layoutDescription.setOnClickListener(this);
        layoutVisible.setOnClickListener(this);
        layoutLocation.setOnClickListener(this);
        layoutPlaylist.setOnClickListener(this);

        setThumbnail(selectedFile);

        setOnSetFragmentListener();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_only_next_button, menu);
    }

    @Override
    public void onClick(View view)
    {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (view.getId())
        {
            case R.id.fragment_upload_video_information_image_button_change_thumbnail :

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                UploadActivity activity = (UploadActivity)getActivity();
                activity.activityResultLauncher.launch(intent);

                break;

            case R.id.fragment_upload_video_information_layout_description :

                fragmentTransaction.replace(R.id.activity_upload_layout_main, new SetDescriptionFragment(onSetFragmentListener));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.fragment_upload_video_information_layout_visible :

                fragmentTransaction.replace(R.id.activity_upload_layout_main, new SetPublicFragment(onSetFragmentListener));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.fragment_upload_video_information_layout_location :

                fragmentTransaction.replace(R.id.activity_upload_layout_main, new SetLocationFragment(onSetFragmentListener));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.fragment_upload_video_information_layout_playlist :

                AddPlaylistFragment addPlaylistFragment = new AddPlaylistFragment(getContext());
                addPlaylistFragment.show(fragmentManager, addPlaylistFragment.getTag());

                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home :

                getActivity().onBackPressed();

                break;

            case R.id.menu_toolbar_next :

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.activity_upload_layout_main, new SetAgeFragment(onSetFragmentListener));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                VideoDTO video = new VideoDTO();
                video.setTitle(title.getText().toString());
                video.setInfo(description.getText().toString());
                video.setVisible(selectedVisible);
                video.setLocation(address);

                onUploadDataListener.onResponseVideoInformation(video);
                onUploadDataListener.onResponseVideoThumbnail(selectedThumbnail);

                break;

            default :

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setThumbnail(File file)
    {
        Glide.with(getContext()).asBitmap().load(file.getAbsolutePath()).override(thumbnail.getWidth(), thumbnail.getHeight()).centerCrop()
                .into(new CustomTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition)
                    {
                        thumbnail.setImageBitmap(resource);
                        selectedThumbnail = resource;
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder)
                    {

                    }
                });
    }

    public void setOnSetFragmentListener()
    {
        onSetFragmentListener = new OnSetFragmentListener()
        {
            @Override
            public void onResponseDescription(String str)
            {
                description.setText(str);
            }

            @Override
            public void onResponsePublic(Integer value)
            {
                Resources resources = getContext().getResources();

                selectedVisible = value;

                if(value == null)
                {
                    visible.setText(resources.getString(R.string.visible_text_lock));
                    return;
                }

                switch (value)
                {
                    case 1 :
                        visible.setText(resources.getString(R.string.visible_text_public));
                        break;
                    case 0 :
                        visible.setText(resources.getString(R.string.visible_text_link));
                        break;
                    default :
                        break;
                }

            }

            @Override
            public void onResponseLocation(String str)
            {
                address = str;
            }

            @Override
            public void onResponseAge(Integer value)
            {
                onUploadDataListener.onResponseVideoAge(value);
                onUploadDataListener.onResponseUploadVideoRequest();
            }

            @Override
            public void onResponseVote(VoteDTO voteData, List<VoteDTO> voteOptions)
            {

            }
        };
    }
}