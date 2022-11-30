package com.example.baetube.fragment.set;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.baetube.PublicState;
import com.example.baetube.R;

public class SetPublicFragment extends Fragment implements View.OnClickListener
{
    private View view;

    /*
     * 단순히 클릭하여 해당 프래그먼트로 전환하기 위한 용도로 사용한다.
     */
    private ConstraintLayout layoutPublic;
    private ConstraintLayout layoutLink;
    private ConstraintLayout layoutLock;

    // 선택된 항목의 번호를 저장하는 정수형 변수.
    private int selectNumber;
    private View selectedView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_set_public, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar.setTitle(getString(R.string.fragment_set_public_toolbar_title));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutPublic = view.findViewById(R.id.fragment_set_public_layout_public);
        layoutLink = view.findViewById(R.id.fragment_set_public_layout_link);
        layoutLock = view.findViewById(R.id.fragment_set_public_layout_lock);

        layoutPublic.setOnClickListener(this);
        layoutLink.setOnClickListener(this);
        layoutLock.setOnClickListener(this);

        selectedView = layoutPublic;
        selectedView.setBackgroundColor(getContext().getColor(R.color.light_gray));

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
            case R.id.fragment_set_public_layout_public :

                selectNumber = PublicState.PUBLIC;
                selectedView.setBackgroundColor(getContext().getColor(R.color.white));
                selectedView = layoutPublic;
                selectedView.setBackgroundColor(getContext().getColor(R.color.light_gray));

                break;
            case R.id.fragment_set_public_layout_link :

                selectNumber = PublicState.LINK;
                selectedView.setBackgroundColor(getContext().getColor(R.color.white));
                selectedView = layoutLink;
                selectedView.setBackgroundColor(getContext().getColor(R.color.light_gray));

                break;
            case R.id.fragment_set_public_layout_lock :

                selectNumber = PublicState.LOCK;
                selectedView.setBackgroundColor(getContext().getColor(R.color.white));
                selectedView = layoutLock;
                selectedView.setBackgroundColor(getContext().getColor(R.color.light_gray));

                break;
            default :



                break;
        }
    }

}