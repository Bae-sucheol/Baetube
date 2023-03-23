package com.example.baetube.fragment.modify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.baetube.OkHttpUtil;
import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.R;

public class ModifyVideoFragment extends Fragment
{
    private View view;
    private OnCallbackResponseListener onCallbackResponseListener;
    private OkHttpUtil okHttpUtil;

    public ModifyVideoFragment(OnCallbackResponseListener onCallbackResponseListener)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_modify_video, container, false);

        // Inflate the layout for this fragment
        return view;
    }
    
}