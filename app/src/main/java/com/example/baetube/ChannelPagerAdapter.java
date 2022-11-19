package com.example.baetube;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.baetube.fragment.Channel.ChannelCommunityFragment;
import com.example.baetube.fragment.Channel.ChannelInfomationFragment;
import com.example.baetube.fragment.Channel.ChannelHomeFragment;
import com.example.baetube.fragment.Channel.ChannelPlaylistFragment;
import com.example.baetube.fragment.Channel.ChannelVideoFragment;

public class ChannelPagerAdapter extends FragmentStateAdapter
{

    private int count;

    public ChannelPagerAdapter(@NonNull Fragment fragment, int count)
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

                fragment = new ChannelHomeFragment();

                break;
            case 1 :

                fragment = new ChannelVideoFragment();

                break;
            case 2 :

                fragment = new ChannelPlaylistFragment();

                break;
            case 3 :

                fragment = new ChannelCommunityFragment();

                break;

            case 4 :

                fragment = new ChannelInfomationFragment();

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
