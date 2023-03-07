package com.example.baetube.fragment.set;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.baetube.OnSetFragmentListener;
import com.example.baetube.R;

public class SetAgeFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener
{
    private View view;

    private TextView textButtonAdult;

    private LinearLayout layoutAdult;

    private OnSetFragmentListener onSetFragmentListener;

    private boolean child;
    private boolean adult;

    private Integer age;

    private RadioGroup radioGroupChild;

    private RadioGroup radioGroupAdult;

    private TextView buttonUpload;

    public SetAgeFragment(OnSetFragmentListener onSetFragmentListener)
    {
        this.onSetFragmentListener = onSetFragmentListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_set_age, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar.setTitle(getString(R.string.fragment_set_age_toolbar_title));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textButtonAdult = view.findViewById(R.id.fragment_set_age_text_button_adult);
        layoutAdult = view.findViewById(R.id.fragment_set_age_layout_adult);

        textButtonAdult.setOnClickListener(this);

        radioGroupChild = view.findViewById(R.id.fragment_set_age_radio_group_child);
        radioGroupAdult = view.findViewById(R.id.fragment_set_age_radio_group_adult);

        radioGroupChild.setOnCheckedChangeListener(this);
        radioGroupAdult.setOnCheckedChangeListener(this);

        buttonUpload = view.findViewById(R.id.fragment_set_age_text_button_upload);
        buttonUpload.setOnClickListener(this);

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            /*
             * 뒤로가기 버튼을 눌렀을 때
             * 액티비티의 onBackPressed() 메소드를 실행.
             * onBackPressed() 메소드에서는 fragmentManager를 통해
             * popBackStack() 메소드를 사용하여 뒤로가기 기능을 구현.
             */
            case android.R.id.home :

                getActivity().onBackPressed();

            default :
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fragment_set_age_text_button_adult :

                if(!child)
                {
                    return;
                }

                if(layoutAdult.getVisibility() == View.VISIBLE)
                {
                    layoutAdult.setVisibility(View.INVISIBLE);
                }
                else
                {
                    layoutAdult.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.fragment_set_age_text_button_upload :

                // 업로드 작업을 요청하는 코드를 삽입해야 한다.

                // 체크한 항목을 검사하여 적용한다.
                applyToPolicy();
                onSetFragmentListener.onResponseAge(age);

                break;

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
    {
        switch (checkedId)
        {
            case R.id.fragment_set_age_radio_button_child :

                child = true;

                break;
                
            case R.id.fragment_set_age_radio_button_not_child :

                child = false;

                break;
                
            case R.id.fragment_set_age_radio_button_not_adult :

                adult = false;

                break;
                
            case R.id.fragment_set_age_radio_button_adult :

                adult = true;

                break;
        }
    }

    private void applyToPolicy()
    {
        if(child && !adult)
        {
            age = 0;
        }
        else if(!child && !adult)
        {
            age = null;
        }
        else if(!child && adult)
        {
            age = 1;
        }
    }


}