package com.example.baetube.fragment.set;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.PublicState;
import com.example.baetube.R;
import com.example.baetube.bottomsheetdialog.AddVoteFragment;
import com.example.baetube.recyclerview.adapter.ReclyclerViewVoteAdapter;

import java.util.ArrayList;

public class SetVoteFragment extends Fragment implements OnRecyclerViewClickListener, View.OnClickListener
{
    private View view;

    // 리사이클러뷰
    private RecyclerView recyclerView;
    // 리사이클러뷰 어뎁터
    private ReclyclerViewVoteAdapter adapter;
    // 리사이클러뷰 리스트.
    private ArrayList<String> list = new ArrayList<>();
    // 현재 추가된 투표 개수
    private TextView countCur;
    // 투표를 추가하기 위한 버튼
    private TextView buttonAddVote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_set_vote, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar.setTitle(getString(R.string.fragment_set_vote_toolbar_title));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        test();
        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */
        recyclerView = view.findViewById(R.id.fragment_set_vote_recyclerview);
        adapter = new ReclyclerViewVoteAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnRecyclerViewClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        countCur = view.findViewById(R.id.fragment_set_vote_text_count_cur);
        buttonAddVote = view.findViewById(R.id.fragment_set_vote_text_button_add_vote);

        buttonAddVote.setOnClickListener(this);

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
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void test()
    {
        list.add("감사합니다.");
        list.add("안감사합니다.");
    }

    @Override
    public void onItemClick(View view, int position)
    {

    }

    @Override
    public void onItemLongClick(View view, int position)
    {

    }

    @Override
    public void onClick(View view)
    {
        // 클릭할 버튼이 딱 하나 뿐이므로 딱히 조건문으로 나눌 필요가 없다.
        AddVoteFragment addVoteFragment = new AddVoteFragment();
        addVoteFragment.show(getParentFragmentManager(), addVoteFragment.getTag());
    }
}