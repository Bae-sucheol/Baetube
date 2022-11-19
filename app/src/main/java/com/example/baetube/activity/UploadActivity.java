package com.example.baetube.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.baetube.R;

public class UploadActivity extends AppCompatActivity
{
    // 프래그먼트 매니저
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        fragmentManager = getSupportFragmentManager();
    }
}