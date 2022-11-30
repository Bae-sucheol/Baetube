package com.example.baetube.fragment.set;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.baetube.R;

public class SetLocationFragment extends Fragment
{
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_set_location, container, false);

        // Inflate the layout for this fragment
        return view;
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
}