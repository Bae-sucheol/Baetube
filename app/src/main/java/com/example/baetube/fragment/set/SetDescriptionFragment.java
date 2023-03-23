package com.example.baetube.fragment.set;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.baetube.OnSetFragmentListener;
import com.example.baetube.R;

public class SetDescriptionFragment extends Fragment
{
    private static final int MAX_LENGTH = 1000;
    private View view;
    private TextView description;
    private TextView currentCount;

    private OnSetFragmentListener onSetFragmentListener;

    public SetDescriptionFragment(OnSetFragmentListener onSetFragmentListener)
    {
        this.onSetFragmentListener = onSetFragmentListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_set_description, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar.setTitle(getString(R.string.fragment_set_description_toolbar_title));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        description = view.findViewById(R.id.fragment_set_description_edit);
        currentCount = view.findViewById(R.id.fragment_set_description_text_count_cur);

        description.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after)
            {
                // 현재 입력된 글자 수를 표시.
                currentCount.setText(String.valueOf(charSequence.length()));
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                // 만약 글자 수가 최대치인 1000을 넘어갔다면 잘라낸다.
                if(editable.length() > MAX_LENGTH)
                {
                    editable.delete(MAX_LENGTH - 1, editable.length() - 1);
                }

            }
        });

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

                //getActivity().onBackPressed();

                // 현재의 텍스트 내용을 넘겨준다.
                String str = description.getText().toString();
                onSetFragmentListener.onResponseDescription(str);

            default :
                return super.onOptionsItemSelected(item);
        }
    }


}