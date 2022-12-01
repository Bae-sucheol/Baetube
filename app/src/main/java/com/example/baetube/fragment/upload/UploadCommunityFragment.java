package com.example.baetube.fragment.upload;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baetube.R;
import com.example.baetube.fragment.set.SetDescriptionFragment;
import com.example.baetube.fragment.set.SetVoteFragment;

public class UploadCommunityFragment extends Fragment implements View.OnClickListener
{
    private View view;

    private ImageView thumbnail;
    private ImageView buttonChangeThumbnail;
    private ImageView profile;
    private TextView channelName;
    private TextView ButtonUpload;
    private EditText title;
    private ConstraintLayout layoutDescription;
    private ConstraintLayout layoutPublic;

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
        ButtonUpload = view.findViewById(R.id.fragment_upload_community_text_button_upload);
        title = view.findViewById(R.id.fragment_upload_video_edit_title);
        layoutDescription = view.findViewById(R.id.fragment_upload_community_layout_description);
        layoutPublic = view.findViewById(R.id.fragment_upload_community_layout_public);

        // 클릭리스너 등록
        layoutDescription.setOnClickListener(this);
        layoutPublic.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
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
            case R.id.fragment_upload_community_layout_description :

                fragmentTransaction.replace(R.id.activity_upload_layout_main, new SetDescriptionFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.fragment_upload_community_layout_public :

                fragmentTransaction.replace(R.id.activity_upload_layout_main, new SetVoteFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

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

            default :
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}