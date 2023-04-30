package com.example.baetube.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.FragmentTagUtil;
import com.example.baetube.OkHttpUtil;
import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.R;
import com.example.baetube.activity.MainActivity;
import com.example.baetube.dto.UserDTO;

public class LoginFragment extends Fragment implements View.OnClickListener
{
    private View view;

    private Button logInButton;
    private TextView signInButton;

    private EditText editEmail;
    private EditText editPassword;
    private TextView errorLogin;

    private OnCallbackResponseListener onCallbackResponseListener;
    private OkHttpUtil okHttpUtil;

    public LoginFragment(OnCallbackResponseListener onCallbackResponseListener)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        toolbar.setTitle(getString(R.string.fragment_login_toolbar_title));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        logInButton = view.findViewById(R.id.fragment_login_button_login);
        signInButton = view.findViewById(R.id.fragment_login_button_sign_in);
        editEmail = view.findViewById(R.id.fragment_login_edit_email);
        editPassword = view.findViewById(R.id.fragment_login_edit_password);
        errorLogin = view.findViewById(R.id.fragment_login_text_error);

        logInButton.setOnClickListener(this);
        signInButton.setOnClickListener(this);

        ((MainActivity)getContext()).setBottomNavigationViewVisible(View.GONE);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home :
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
            case R.id.fragment_login_button_login :

                // 로그인 버튼을 누르면 로그인 요청.
                if(okHttpUtil == null)
                {
                    okHttpUtil = new OkHttpUtil();
                }

                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();

                UserDTO user = new UserDTO();
                user.setEmail(email);
                user.setPassword(password);

                System.out.println("로그인 요청");

                String url = getString(R.string.api_url_user_login);

                ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_LOGIN_USER);

                okHttpUtil.sendPostRequest(user, url, returnableCallback);

                break;

            case R.id.fragment_login_button_sign_in :

                // 회원 가입 버튼을 누르면 회원가입 프래그먼트로 전환.

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.activity_main_layout, new SignInFragment(onCallbackResponseListener), FragmentTagUtil.FRAGMENT_TAG_SIGN_IN);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
        }
    }

    public void printError()
    {
        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                errorLogin.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onDestroy()
    {
        ((MainActivity)getContext()).setBottomNavigationViewVisible(View.VISIBLE);
        super.onDestroy();
    }
}