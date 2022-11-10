package com.example.baetube.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.baetube.R;

public class SignInFragment extends Fragment
{
    private View view;

    private EditText editEmail;
    private EditText editPassword;
    private EditText editPasswordCheck;
    private EditText editName;
    private EditText editBirthYear;
    private EditText editBirthMonth;
    private EditText editBirthDay;
    private EditText editAddress;
    private EditText editPhone;

    private RadioGroup radioGroupGender;

    private Button buttonSignIn;

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

        radioGroupGender = view.findViewById(R.id.fragment_sign_in_radio_group_gender);

        buttonSignIn = view.findViewById(R.id.fragment_sign_in_button_sign_in);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_main, menu);
    }

}