package com.example.baetube;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TabStrategy implements TabLayoutMediator.TabConfigurationStrategy
{
    private String titles[];

    public TabStrategy(String titles[])
    {
        this.titles = titles;
    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position)
    {
        tab.setText(titles[position]);
    }
}
