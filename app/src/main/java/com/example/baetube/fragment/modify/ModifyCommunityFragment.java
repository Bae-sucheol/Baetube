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
import com.example.baetube.dto.CommunityDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.set.SetDescriptionFragment;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

public class ModifyCommunityFragment extends Fragment implements View.OnClickListener
{
    private View view;

    private ImageView community;
    private ImageView changeCommunity;
    private ImageView profile;
    private EditText editTitle;
    private TextView channelName;
    private TextView textCount;
    private ConstraintLayout description;
    private ConstraintLayout vote;
    private CommunityDTO sourceCommunityData;
    private CommunityDTO currentCommunityData;
    private OkHttpUtil okHttpUtil;
    private OnCallbackResponseListener onCallbackResponseListener;
    private OnSetFragmentListener onSetFragmentListener;

    public ModifyCommunityFragment(OnCallbackResponseListener onCallbackResponseListener, Integer communityId)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
        sourceCommunityData = new CommunityDTO();
        sourceCommunityData.setCommunityId(communityId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_modify_community, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        //toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        community = view.findViewById(R.id.fragment_modify_community_image_community);
        changeCommunity = view.findViewById(R.id.fragment_modify_community_button_change_community);
        profile = view.findViewById(R.id.fragment_modify_community_image_profile);
        editTitle = view.findViewById(R.id.fragment_modify_video_edit_title);
        channelName = view.findViewById(R.id.fragment_modify_community_text_channel_name);
        textCount = view.findViewById(R.id.fragment_modify_video_text_count_cur);
        description = view.findViewById(R.id.fragment_modify_community_layout_description);
        vote = view.findViewById(R.id.fragment_modify_community_layout_vote);

        changeCommunity.setOnClickListener(this);
        description.setOnClickListener(this);
        vote.setOnClickListener(this);

        requestCommunityData();
        setOnFragmentListener();

        // Inflate the layout for this fragment
        return view;
    }

    private void requestCommunityData()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        System.out.println("정보를 요청합니다.");

        String url = getString(R.string.api_url_community_data) + sourceCommunityData.getCommunityId();
        System.out.println("url : " + url);
        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_COMMUNITY_DATA);
        okHttpUtil.sendGetRequest(url, returnableCallback);
    }


    public void setImage(File file)
    {
        Glide.with(getContext()).asBitmap().load(file.getAbsolutePath()).override(community.getWidth(), community.getHeight()).centerCrop()
                .into(new CustomTarget<Bitmap>()
                {

                    @Override
                    public void onResourceReady(Bitmap resource, @Nullable Transition<? super Bitmap> transition)
                    {
                        community.setImageBitmap(resource);
                        currentCommunityData.setImageUrl(file.getPath());
                        PreferenceManager.setString(getContext().getApplicationContext(), PreferenceManager.PREFERENCES_COMMUNITY, file.getPath());
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder)
                    {

                    }
                });
    }

    public void setCommunityData(CommunityDTO data)
    {
        sourceCommunityData = data;
        currentCommunityData = new CommunityDTO(sourceCommunityData.getCommunityId(), sourceCommunityData.getContentsId(), sourceCommunityData.getChannelId(),
                sourceCommunityData.getLikeCount(), sourceCommunityData.getHateCount(), sourceCommunityData.getReplyCount(), sourceCommunityData.getImageUrl(),
                sourceCommunityData.getComment(), sourceCommunityData.getDate());

        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                editTitle.setText(data.getComment());

                Glide.with(getContext())
                        .asBitmap()
                        .load(getContext().getString(R.string.api_url_image_community) + data.getImageUrl() + ".jpg") // or URI/path
                        .error(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_account_circle_24))
                        .override(community.getWidth(), community.getHeight())
                        .centerCrop()
                        .into(community);
            }
        });

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fragment_modify_community_button_change_community :

                // 커뮤니티 이미지 선택
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                MainActivity activity = (MainActivity)getActivity();
                activity.activityResultLauncher.launch(intent);

                break;

            case R.id.fragment_modify_community_layout_description :

                // 채널 정보 변경.
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.activity_main_layout, new SetDescriptionFragment(onSetFragmentListener, currentCommunityData.getComment()));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;

            case R.id.fragment_modify_community_layout_vote :



                break;

        }
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
                requestUpdateCommunity();
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestUpdateCommunity()
    {
        System.out.println("변경 체크");
        boolean isChanged = false;

        currentCommunityData.setTitle(editTitle.getText().toString());

        // 채널 이름이 변경되었는지 확인
        if(!sourceCommunityData.getTitle().equals(currentCommunityData.getTitle()))
        {
            isChanged = true;
        }
        if(!sourceCommunityData.getComment().equals(currentCommunityData.getComment()))
        {
            isChanged = true;
        }
        if(!sourceCommunityData.getImageUrl().equals(currentCommunityData.getImageUrl()))
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

        System.out.println("변경된 사항이 있으므로 업데이트를 요청합니다.");

        String url = getString(R.string.api_url_community_update);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_UPDATE_CHANNEL);

        okHttpUtil.sendPostRequest(currentCommunityData, url, returnableCallback);
    }

    private void setOnFragmentListener()
    {
        onSetFragmentListener = new OnSetFragmentListener()
        {
            @Override
            public void onResponseDescription(String str)
            {
                currentCommunityData.setComment(str);
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
}