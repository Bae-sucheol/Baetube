package com.example.baetube.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

        logInButton.setOnClickListener(this);
        signInButton.setOnClickListener(this);

        ((MainActivity)getContext()).setBottomNavigationViewVisible(View.GONE);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
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
                okHttpUtil = new OkHttpUtil();

                UserDTO user = new UserDTO();
                user.setEmail("testtest@naver.com");
                user.setPassword("1234");

                String url = "http://192.168.0.4:9090/Baetube_backEnd/api/user/login";

                ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

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

    @Override
    public void onDestroy()
    {
        ((MainActivity)getContext()).setBottomNavigationViewVisible(View.VISIBLE);
        super.onDestroy();
    }
}