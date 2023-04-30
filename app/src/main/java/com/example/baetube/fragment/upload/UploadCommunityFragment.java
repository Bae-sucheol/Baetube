package com.example.baetube.fragment.upload;

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
import com.example.baetube.dto.CommunityDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.set.SetDescriptionFragment;
import com.example.baetube.fragment.set.SetVoteFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UploadCommunityFragment extends Fragment implements View.OnClickListener
{
    private View view;

    private ImageView thumbnail;
    private ImageView buttonChangeThumbnail;
    private ImageView profile;
    private TextView channelName;
    private TextView buttonUpload;
    private EditText title;
    private ConstraintLayout layoutDescription;
    private ConstraintLayout layoutPublic;
    private TextView category;

    // 액티비티와 통신하기 위한 인터페이스
    private OnUploadDataListener onUploadDataListener;

    // Set 프래그먼트들과 통신 하기위한 인터페이스
    private OnSetFragmentListener onSetFragmentListener;

    // 커뮤니티 게시글의 전반적인 내용을 담고있는 객체
    private CommunityDTO community = new CommunityDTO();

    // 커뮤니티 게시글에 사용될 이미지
    private Bitmap selectedImage;

    // 커뮤니티 게시글에 사용될 투표.
    private VoteDTO vote;
    private List<VoteDTO> voteOptionList = new ArrayList<>();

    public UploadCommunityFragment(OnUploadDataListener onUploadDataListener)
    {
        this.onUploadDataListener = onUploadDataListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_upload_community, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar.setTitle(getString(R.string.fragment_upload_community_toolbar_title));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 요소 찾기
        thumbnail = view.findViewById(R.id.fragment_upload_community_image_thumbnail);
        buttonChangeThumbnail = view.findViewById(R.id.fragment_upload_community_image_button_change_thumbnail);
        profile = view.findViewById(R.id.fragment_upload_community_image_profile);
        channelName = view.findViewById(R.id.fragment_upload_community_text_channel_name);
        buttonUpload = view.findViewById(R.id.fragment_upload_community_text_button_upload);
        layoutDescription = view.findViewById(R.id.fragment_upload_community_layout_description);
        layoutPublic = view.findViewById(R.id.fragment_upload_community_layout_public);

        // 클릭리스너 등록
        buttonChangeThumbnail.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        layoutDescription.setOnClickListener(this);
        layoutPublic.setOnClickListener(this);

        setOnSetFragmentListener();

        if(selectedImage != null)
        {
            thumbnail.setImageBitmap(selectedImage);
        }

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
    public void onClick(View view)
    {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (view.getId())
        {
            case R.id.fragment_upload_community_image_button_change_thumbnail :

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                UploadActivity activity = (UploadActivity)getActivity();
                activity.activityResultLauncher.launch(intent);

                break;

            case R.id.fragment_upload_community_layout_description :

                fragmentTransaction.add(R.id.activity_upload_layout_main, new SetDescriptionFragment(onSetFragmentListener));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.fragment_upload_community_layout_public :

                fragmentTransaction.add(R.id.activity_upload_layout_main, new SetVoteFragment(onSetFragmentListener));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;

            case R.id.fragment_upload_community_text_button_upload :

                // 임의로 설정했다.
                community.setChannelId(4);
                community.setName("test");
                onUploadDataListener.onResponseCommunityImage(selectedImage);
                onUploadDataListener.onResponseCommunityInformation(community);
                onUploadDataListener.onResponseCommunityVote(vote, voteOptionList);

                onUploadDataListener.onResponseUploadCommunityRequest();

                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home :

                getActivity().onBackPressed();

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
                    public void onResourceReady( Bitmap resource, Transition<? super Bitmap> transition)
                    {
                        thumbnail.setImageBitmap(resource);
                        selectedImage = resource;
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder)
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
                community.setComment(str);
                System.out.println(str);
            }

            @Override
            public void onResponsePublic(Integer value)
            {

            }

            @Override
            public void onResponseLocation(String str)
            {

            }

            @Override
            public void onResponseAge(Integer value)
            {

            }

            @Override
            public void onResponseVote(VoteDTO voteData, List<VoteDTO> voteOptions)
            {
                vote = voteData;
                voteOptionList = voteOptions;
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
}