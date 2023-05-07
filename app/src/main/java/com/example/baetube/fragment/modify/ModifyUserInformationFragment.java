package com.example.baetube.fragment.modify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.OkHttpUtil;
import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.OnSetFragmentListener;
import com.example.baetube.R;
import com.example.baetube.SignInValidation;
import com.example.baetube.bottomsheetdialog.SetCalendarFragment;
import com.example.baetube.dto.ChangePasswordRequest;
import com.example.baetube.dto.UserDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.set.SetLocationFragment;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class ModifyUserInformationFragment extends Fragment implements View.OnFocusChangeListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener
{
    private View view;
    private OnCallbackResponseListener onCallbackResponseListener;
    private OkHttpUtil okHttpUtil;

    // 텍스트 뷰
    private TextView textEmail;
    private TextView textName;

    // 입력 editText
    private EditText editPassword;
    private EditText editPasswordNew;
    private EditText editBirthYear;
    private EditText editBirthMonth;
    private EditText editBirthDay;
    private EditText editAddress;
    private EditText editPhone;

    // 에러 출력용 TextView
    private TextView errorEmail;
    private TextView errorPassword;
    private TextView errorPasswordCheck;
    private TextView errorName;
    private TextView errorBirth;
    private TextView errorGender;
    private TextView errorAddress;
    private TextView errorPhone;

    private RadioGroup radioGroupGender;

    private Button buttonModify;
    private Button buttonChangePassword;

    // Set 프래그먼트들과 통신 하기위한 인터페이스
    private OnSetFragmentListener onSetFragmentListener;

    private Integer gender;

    private UserDTO currentUserData;

    public ModifyUserInformationFragment(OnCallbackResponseListener onCallbackResponseListener)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_modify_user_information, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        toolbar.setTitle(getString(R.string.fragment_modify_user_toolbar_title));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // EditText, RadioGroup, Button 등의 요소 찾기
        textEmail = view.findViewById(R.id.fragment_modify_user_text_email);
        textName = view.findViewById(R.id.fragment_modify_user_text_name);

        editPassword = view.findViewById(R.id.fragment_modify_user_edit_password);
        editPasswordNew = view.findViewById(R.id.fragment_modify_user_edit_password_new);
        editBirthYear = view.findViewById(R.id.fragment_modify_user_edit_birth_year);
        editBirthMonth = view.findViewById(R.id.fragment_modify_user_edit_birth_month);
        editBirthDay = view.findViewById(R.id.fragment_modify_user_edit_birth_day);
        editAddress = view.findViewById(R.id.fragment_modify_user_edit_address);
        editPhone = view.findViewById(R.id.fragment_modify_user_edit_phone);

        errorEmail = view.findViewById(R.id.fragment_modify_user_text_error_email);
        errorPassword = view.findViewById(R.id.fragment_modify_user_text_error_password);
        errorPasswordCheck = view.findViewById(R.id.fragment_modify_user_text_error_password_check);
        errorName = view.findViewById(R.id.fragment_modify_user_text_error_name);
        errorBirth = view.findViewById(R.id.fragment_modify_user_text_error_birth);
        errorGender = view.findViewById(R.id.fragment_modify_user_text_error_gender);
        errorAddress = view.findViewById(R.id.fragment_modify_user_text_error_address);
        errorPhone = view.findViewById(R.id.fragment_modify_user_text_error_phone);

        errorEmail.setFocusableInTouchMode(true);
        errorPassword.setFocusableInTouchMode(true);
        errorPasswordCheck.setFocusableInTouchMode(true);
        errorName.setFocusableInTouchMode(true);
        errorBirth.setFocusableInTouchMode(true);
        errorGender.setFocusableInTouchMode(true);
        errorAddress.setFocusableInTouchMode(true);
        errorPhone  .setFocusableInTouchMode(true);

        radioGroupGender = view.findViewById(R.id.fragment_modify_user_radio_group_gender);

        buttonModify = view.findViewById(R.id.fragment_modify_user_button_modify_user);
        buttonChangePassword = view.findViewById(R.id.fragment_modify_user_button_change_password);

        editBirthYear.setOnFocusChangeListener(this);
        editBirthMonth.setOnFocusChangeListener(this);
        editBirthDay.setOnFocusChangeListener(this);
        editAddress.setOnFocusChangeListener(this);

        radioGroupGender.setOnCheckedChangeListener(this);

        buttonModify.setOnClickListener(this);
        buttonChangePassword.setOnClickListener(this);

        onSetFragmentListener = new OnSetFragmentListener()
        {
            @Override
            public void onResponseDescription(String str)
            {

            }

            @Override
            public void onResponsePublic(Integer value)
            {

            }

            @Override
            public void onResponseLocation(String str)
            {
                editAddress.setText(str);
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
                editBirthYear.setText(String.valueOf(year));
                editBirthMonth.setText(String.valueOf(month));
                editBirthDay.setText(String.valueOf(dayOfMonth));
            }
        };

        requestUserData();

        // Inflate the layout for this fragment
        return view;
    }

    private void requestUserData()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_select_user_data);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_USER_DATA);

        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    private void requestChangePassword()
    {
        String password = editPassword.getText().toString().trim();
        String newPassword = editPasswordNew.getText().toString().trim();

        // 비밀번호 유효성 검사
        if(!SignInValidation.isPassword(password))
        {
            errorPassword.setVisibility(View.VISIBLE);
            errorPassword.requestFocus();
            return;
        }

        if(!SignInValidation.isPassword(newPassword))
        {
            errorPasswordCheck.setVisibility(View.VISIBLE);
            errorPasswordCheck.requestFocus();
            return;
        }

        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(currentUserData.getEmail(), password, newPassword);

        String url = getString(R.string.api_url_change_password);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(changePasswordRequest, url, returnableCallback);

        editPassword.setText("");
        editPasswordNew.setText("");
    }

    private void requestUpdateUserData()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_user_update);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_USER_DATA);

        okHttpUtil.sendPostRequest(currentUserData, url, returnableCallback);

        getParentFragmentManager().popBackStack();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fragment_modify_user_button_change_password :

                requestChangePassword();

                break;
            case R.id.fragment_modify_user_button_modify_user :

                if(!validationData())
                {
                    return;
                }

                int year = Integer.parseInt(editBirthYear.getText().toString());
                int month = Integer.parseInt(editBirthMonth.getText().toString());
                int dayOfMonth = Integer.parseInt(editBirthDay.getText().toString());

                ZonedDateTime zonedDateTime = ZonedDateTime.of(year, month, dayOfMonth, 0, 0, 0 ,0, ZoneId.of("UTC"));
                Timestamp timestamp = new Timestamp(zonedDateTime.toInstant().toEpochMilli());

                currentUserData.setBirth(timestamp);
                currentUserData.setGender(gender);
                currentUserData.setAddress(editAddress.getText().toString());
                currentUserData.setPhone(editPhone.getText().toString());

                requestUpdateUserData();

                break;
        }

    }

    @Override
    public void onFocusChange(View view, boolean b)
    {
        if(b)
        {
            if(view == editAddress)
            {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.activity_main_layout, new SetLocationFragment(onSetFragmentListener));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            else
            {
                SetCalendarFragment setCalendarFragment = new SetCalendarFragment(getContext(), onSetFragmentListener);
                setCalendarFragment.show(getParentFragmentManager(), setCalendarFragment.getTag());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home :

                getParentFragmentManager().popBackStack();

        }

        return true;
    }

    private boolean validationData()
    {
        // 생년월일 유효성 검사
        if(editBirthYear.getText().toString().isEmpty() || editBirthMonth.getText().toString().isEmpty() || editBirthDay.getText().toString().isEmpty())
        {
            errorBirth.setVisibility(View.VISIBLE);
            errorBirth.requestFocus();
            return false;
        }

        // 성별 유효성 검사
        if(gender == null)
        {
            errorGender.setVisibility(View.VISIBLE);
            errorGender.requestFocus();
            return false;
        }

        // 주소 유효성 검사
        String address = editAddress.getText().toString();

        if(address.isEmpty() || address.trim().length() == 0)
        {
            errorAddress.setVisibility(View.VISIBLE);
            errorAddress.requestFocus();
            return false;
        }

        // 전화번호 유효성 검사
        String phone = editPhone.getText().toString();

        if(!SignInValidation.isPhone(phone))
        {
            errorPhone.setVisibility(View.VISIBLE);
            errorPhone.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
    {
        switch (checkedId)
        {
            case R.id.fragment_modify_user_radio_gender_male :

                gender = 1;

                break;
            case R.id.fragment_modify_user_radio_gender_female :

                gender = 0;

                break;
        }
    }

    public void setUserData(UserDTO user)
    {
        currentUserData = user;

        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                textEmail.setText(user.getEmail());
                textName.setText(user.getName());

                String timeStamp = user.getBirth().toString();
                timeStamp = timeStamp.split(" ")[0];

                String birthData[] = timeStamp.split("-");

                editBirthYear.setText(birthData[0]);
                editBirthMonth.setText(birthData[1]);
                editBirthDay.setText(birthData[2]);
                editAddress.setText(user.getAddress());
                editPhone.setText(user.getPhone());
            }
        });
    }
}