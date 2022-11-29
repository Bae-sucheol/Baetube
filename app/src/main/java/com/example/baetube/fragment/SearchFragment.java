package com.example.baetube.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.baetube.R;
import com.example.baetube.Spinner.SpinnerItem;
import com.example.baetube.recyclerview.adapter.RecyclerViewSearchHistoryAdapter;
import com.example.baetube.recyclerview.item.RecyclerViewSearchHistoryItem;

import java.util.ArrayList;

public class SearchFragment extends Fragment
{
    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewSearchHistoryAdapter adapter;
    private ArrayList<RecyclerViewSearchHistoryItem> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_search, container, false);

        test();
        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */
        recyclerView = view.findViewById(R.id.fragment_search_recyclerview);
        adapter = new RecyclerViewSearchHistoryAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return view;
    }

    public void test()
    {
        String keywords[] = {"두부조림", "스팀 가을할인", "안드로이드 레이아웃", "자바 스프링", "삼겹살"};

        for (int i = 0; i < 5; i++)
        {
            RecyclerViewSearchHistoryItem item = new RecyclerViewSearchHistoryItem();

            item.setKeywords(keywords[i]);

            list.add(item);
        }
    }
}