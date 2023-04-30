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
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.OkHttpUtil;
import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.OnSetFragmentListener;
import com.example.baetube.PreferenceManager;
import com.example.baetube.R;
import com.example.baetube.activity.MainActivity;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.set.SetDescriptionFragment;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

public class ModifyChannelInformationFragment extends Fragment implements View.OnClickListener
{
    private View view;

    private ImageView arts;
    private ImageView changeArts;
    private ImageView profile;
    private EditText editName;
    private TextView description;
    private ConstraintLayout layoutDescription;

    private ChannelDTO sourceChannelData;
    private ChannelDTO currentChannelData;

    private OnSetFragmentListener onSetFragmentListener;
    private OnCallbackResponseListener onCallbackResponseListener;

    private OkHttpUtil okHttpUtil;

    private boolean isSelectedArts = false;

    public ModifyChannelInformationFragment(OnCallbackResponseListener onCallbackResponseListener)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_modify_channel_information, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        //toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arts = view.findViewById(R.id.fragment_modify_channel_information_image_arts);
        changeArts = view.findViewById(R.id.fragment_modify_channel_information_button_change_arts);
        profile = view.findViewById(R.id.fragment_modify_channel_information_image_profile);
        editName = view.findViewById(R.id.fragment_modify_channel_information_edit_name);
        description = view.findViewById(R.id.fragment_modify_channel_information_text_description);
        layoutDescription = view.findViewById(R.id.fragment_modify_channel_information_layout_description);

        changeArts.setOnClickListener(this);
        profile.setOnClickListener(this);
        layoutDescription.setOnClickListener(this);

        setOnSetFragmentListener();
        ((MainActivity)getActivity()).requestChannelData();

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
        switch (item.getItemId())
        {
            case android.R.id.home :
                requestUpdateChannel();
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fragment_modify_channel_information_button_change_arts :

                isSelectedArts = true;

                // 채널 아트 이미지 변경.
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                MainActivity activity = (MainActivity)getActivity();
                activity.activityResultLauncher.launch(intent);

                break;

            case R.id.fragment_modify_channel_information_image_profile :

                isSelectedArts = false;

                // 프로필 이미지 변경
                intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activity = (MainActivity)getActivity();
                activity.activityResultLauncher.launch(intent);

                break;

            case R.id.fragment_modify_channel_information_layout_description :

                // 채널 정보 변경.
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.activity_main_layout, new SetDescriptionFragment(onSetFragmentListener, currentChannelData.getDescription()));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;

        }
    }

    /*
    private void requestChannelData()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_channel_data) + PreferenceManager.getChannelSequence(getContext());
        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_CHANNEL_DATA);
        okHttpUtil.sendGetRequest(url, returnableCallback);
    }
     */

    private void requestUpdateChannel()
    {
        boolean isChanged = false;

        currentChannelData.setName(editName.getText().toString());

        // 채널 이름이 변경되었는지 확인
        if(!sourceChannelData.getName().equals(currentChannelData.getName()))
        {
            System.out.println("이름이 변경되었습니다.");
            isChanged = true;
        }
        if(!sourceChannelData.getDescription().equals(currentChannelData.getDescription()))
        {
            System.out.println("정보가 변경되었습니다.");
            isChanged = true;
        }
        if(!sourceChannelData.getArts().equals(currentChannelData.getArts()))
        {
            System.out.println("채널 아트가 변경되었습니다.");
            isChanged = true;
        }
        if(!sourceChannelData.getProfile().equals(currentChannelData.getProfile()))
        {
            System.out.println("프로필이 변경되었습니다.");
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

        String url = getString(R.string.api_url_channel_update);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_UPDATE_CHANNEL);

        okHttpUtil.sendPostRequest(currentChannelData, url, returnableCallback);
    }

    public void setImage(File file)
    {
        if(isSelectedArts)
        {
            Glide.with(getContext()).asBitmap().load(file.getAbsolutePath()).override(arts.getWidth(), arts.getHeight()).centerCrop()
                    .into(new CustomTarget<Bitmap>() {

                        @Override
                        public void onResourceReady( Bitmap resource, @Nullable Transition<? super Bitmap> transition)
                        {
                            arts.setImageBitmap(resource);
                            currentChannelData.setArts(file.getPath());
                            PreferenceManager.setString(getContext().getApplicationContext(), PreferenceManager.PREFERENCES_ARTS, file.getPath());
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder)
                        {

                        }
                    });
        }
        else
        {
            Glide.with(getContext()).asBitmap().load(file.getAbsolutePath()).override(profile.getWidth(), profile.getHeight()).centerCrop()
                    .into(new CustomTarget<Bitmap>() {

                        @Override
                        public void onResourceReady( Bitmap resource, @Nullable Transition<? super Bitmap> transition)
                        {
                            profile.setImageBitmap(resource);
                            currentChannelData.setProfile(file.getPath());
                            PreferenceManager.setString(getContext().getApplicationContext(), PreferenceManager.PREFERENCES_PROFILE, file.getPath());
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder)
                        {

                        }
                    });
        }

    }

    public void setChannelData(ChannelDTO data)
    {
        System.out.println("채널 데이터 적용");

        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                // currentChannelData = data를 할 경우 같은 객체를 참조하게 되므로 source, current 둘 중 하나는 데이터를 복사해서 적용한다.
                sourceChannelData = data;
                currentChannelData = new ChannelDTO(data.getChannelId(), data.getUserId(), data.getSubs(), data.getVideoCount(), data.getName(),
                        data.getDescription(), data.getArts(), data.getRegDate(), data.getProfile());

                editName.setText(data.getName());
                description.setText(data.getDescription());

                Glide.with(getContext())
                        .asBitmap()
                        .load(getContext().getString(R.string.api_url_image_arts) + data.getArts() + ".jpg") // or URI/path
                        .error(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_account_circle_24))
                        .override(arts.getWidth(), arts.getHeight())
                        .centerCrop()
                        .into(arts);

                Glide.with(getContext())
                        .asBitmap()
                        .load(getContext().getString(R.string.api_url_image_profile) + data.getProfile() + ".jpg")
                        .error(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_account_circle_24))
                        .override(profile.getWidth(), profile.getHeight())
                        .centerCrop()
                        .apply(new RequestOptions().circleCrop())
                        .into(profile);
            }
        });
    }

    private void setOnSetFragmentListener()
    {
        onSetFragmentListener = new OnSetFragmentListener()
        {
            @Override
            public void onResponseDescription(String str)
            {
                currentChannelData.setDescription(str);
                description.setText(str);
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