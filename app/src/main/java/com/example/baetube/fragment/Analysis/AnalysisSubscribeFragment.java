package com.example.baetube.fragment.Analysis;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baetube.R;

public class AnalysisSubscribeFragment extends Fragment
{
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_analysis_subscribe, container, false);

        // Inflate the layout for this fragment
        return view;
    }
}