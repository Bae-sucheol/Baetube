package com.example.baetube;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.baetube.fragment.analysis.AnalysisSubscribeFragment;
import com.example.baetube.fragment.analysis.AnalysisTimeFragment;
import com.example.baetube.fragment.analysis.AnalysisViewFragment;

public class AnalysisPagerAdapter extends FragmentStateAdapter
{
    private int count;

    public AnalysisPagerAdapter(@NonNull Fragment fragment, int count)
    {
        super(fragment);
        this.count = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        Fragment fragment;

        switch (position)
        {
            case 0 :

                fragment = new AnalysisViewFragment();

                break;

            case 1 :

                fragment = new AnalysisTimeFragment();

                break;

            case 2 :

                fragment = new AnalysisSubscribeFragment();

                break;

            default :

                fragment = null;

                break;

        }

        return fragment;
    }

    @Override
    public int getItemCount()
    {
        return count;
    }


}
