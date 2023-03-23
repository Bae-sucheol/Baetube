package com.example.baetube.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.OnSetFragmentListener;
import com.example.baetube.R;
import com.example.baetube.SignInValidation;
import com.example.baetube.activity.MainActivity;
import com.example.baetube.bottomsheetdialog.SetCalendarFragment;
import com.example.baetube.dto.UserDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.set.SetLocationFragment;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class SignInFragment extends Fragment implements View.OnFocusChangeListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener
{
    private View view;

    // 입력 editText
    private EditText editEmail;
    private EditText editPassword;
    private EditText editPasswordCheck;
    private EditText editName;
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

    private Button buttonSignIn;

    private OnCallbackResponseListener onCallbackResponseListener;

    // Set 프래그먼트들과 통신 하기위한 인터페이스
    private OnSetFragmentListener onSetFragmentListener;

    private Integer gender;

    public SignInFragment(OnCallbackResponseListener onCallbackResponseListener)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(false);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        toolbar.setTitle(getString(R.string.fragment_sign_in_toolbar_title));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // EditText, RadioGroup, Button 등의 요소 찾기
        editEmail = view.findViewById(R.id.fragment_sign_in_edit_email);
        editPassword = view.findViewById(R.id.fragment_sign_in_edit_password);
        editPasswordCheck = view.findViewById(R.id.fragment_sign_in_edit_password_check);
        editName = view.findViewById(R.id.fragment_sign_in_edit_name);
        editBirthYear = view.findViewById(R.id.fragment_sign_in_edit_birth_year);
        editBirthMonth = view.findViewById(R.id.fragment_sign_in_edit_birth_month);
        editBirthDay = view.findViewById(R.id.fragment_sign_in_edit_birth_day);
        editAddress = view.findViewById(R.id.fragment_sign_in_edit_address);
        editPhone = view.findViewById(R.id.fragment_sign_in_edit_phone);

        errorEmail = view.findViewById(R.id.fragment_sign_in_text_error_email);
        errorPassword = view.findViewById(R.id.fragment_sign_in_text_error_password);
        errorPasswordCheck = view.findViewById(R.id.fragment_sign_in_text_error_password_check);
        errorName = view.findViewById(R.id.fragment_sign_in_text_error_name);
        errorBirth = view.findViewById(R.id.fragment_sign_in_text_error_birth);
        errorGender = view.findViewById(R.id.fragment_sign_in_text_error_gender);
        errorAddress = view.findViewById(R.id.fragment_sign_in_text_error_address);
        errorPhone = view.findViewById(R.id.fragment_sign_in_text_error_phone);

        errorEmail.setFocusableInTouchMode(true);
        errorPassword.setFocusableInTouchMode(true);
        errorPasswordCheck.setFocusableInTouchMode(true);
        errorName.setFocusableInTouchMode(true);
        errorBirth.setFocusableInTouchMode(true);
        errorGender.setFocusableInTouchMode(true);
        errorAddress.setFocusableInTouchMode(true);
        errorPhone  .setFocusableInTouchMode(true);

        radioGroupGender = view.findViewById(R.id.fragment_sign_in_radio_group_gender);

        buttonSignIn = view.findViewById(R.id.fragment_sign_in_button_sign_in);

        editBirthYear.setOnFocusChangeListener(this);
        editBirthMonth.setOnFocusChangeListener(this);
        editBirthDay.setOnFocusChangeListener(this);
        editAddress.setOnFocusChangeListener(this);

        radioGroupGender.setOnCheckedChangeListener(this);

        buttonSignIn.setOnClickListener(this);

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

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_main, menu);
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
    public void onClick(View view)
    {
        if(!validationData())
        {
            return;
        }

        UserDTO user = new UserDTO();

        user.setEmail(editEmail.getText().toString());
        user.setPassword(editPassword.getText().toString());
        user.setName(editName.getText().toString());

        int year = Integer.parseInt(editBirthYear.getText().toString());
        int month = Integer.parseInt(editBirthMonth.getText().toString());
        int dayOfMonth = Integer.parseInt(editBirthDay.getText().toString());

        ZonedDateTime zonedDateTime = ZonedDateTime.of(year, month, dayOfMonth, 0, 0, 0 ,0, ZoneId.of("UTC"));
        Timestamp timestamp = new Timestamp(zonedDateTime.toInstant().toEpochMilli());

        user.setBirth(timestamp);
        user.setGender(gender);
        user.setAddress(editAddress.getText().toString());
        user.setPhone(editPhone.getText().toString());

        ((MainActivity)getContext()).requestSignIn(user);
    }

    private boolean validationData()
    {
        // 이메일 유효성 검사
        String email = editEmail.getText().toString();

        if(!SignInValidation.isEmail(email))
        {
            errorEmail.setVisibility(View.VISIBLE);
            errorEmail.requestFocus();
            return false;
        }

        // 비밀번호 유효성 검사
        String password = editPassword.getText().toString();

        if(!SignInValidation.isPassword(password))
        {
            errorPassword.setVisibility(View.VISIBLE);
            errorPassword.requestFocus();
            return false;
        }

        // 비밀번호 체크 유효성 검사
        String passwordCheck = editPasswordCheck.getText().toString();

        if(!password.equals(passwordCheck))
        {
            errorPasswordCheck.setVisibility(View.VISIBLE);
            errorPasswordCheck.requestFocus();
            return false;
        }

        // 이름 유효성 검사
        String name = editName.getText().toString();

        if(!SignInValidation.isName(name))
        {
            errorName.setVisibility(View.VISIBLE);
            errorName.requestFocus();
            return false;
        }

        // 생년월일 유효성 검사
        if(!editBirthYear.getText().toString().isEmpty() && !editBirthMonth.getText().toString().isEmpty() && !editBirthDay.getText().toString().isEmpty())
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
            case R.id.fragment_sign_in_radio_gender_male :

                gender = 1;

                break;
            case R.id.fragment_sign_in_radio_gender_female :

                gender = 0;

                break;
        }
    }
}