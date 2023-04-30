package com.example.baetube;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ChannelTabStrategy implements TabLayoutMediator.TabConfigurationStrategy
{
    private String titles[];

    public ChannelTabStrategy(String titles[])
    {
        this.titles = titles;
    }

    @Override
    public void onConfigureTab( TabLayout.Tab tab, int position)
    {
        tab.setText(titles[position]);
    }


}
