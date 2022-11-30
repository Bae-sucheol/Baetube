package com.example.baetube.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.baetube.R;
import com.example.baetube.fragment.upload.UploadVideoListFragment;

public class UploadActivity extends AppCompatActivity
{
    // 프래그먼트 매니저
    private FragmentManager fragmentManager;
    // 프래그먼트 트랜잭션
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        fragmentTransaction.add(R.id.activity_upload_layout_main, new UploadVideoListFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        System.out.println(fragmentManager.getBackStackEntryCount() + " 카운트 ");

        if(fragmentManager.getBackStackEntryCount() == 0)
        {
            finish();
        }

    }

}