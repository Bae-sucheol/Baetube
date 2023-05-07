package com.example.baetube;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.ViewPagerFragmentData;
import com.example.baetube.fragment.channel.ChannelCommunityFragment;
import com.example.baetube.fragment.channel.ChannelHomeFragment;
import com.example.baetube.fragment.channel.ChannelInfomationFragment;
import com.example.baetube.fragment.channel.ChannelPlaylistFragment;
import com.example.baetube.fragment.channel.ChannelVideoFragment;

public class ChannelPagerAdapter extends FragmentStateAdapter
{
    private int count;
    private OnCallbackResponseListener onCallbackResponseListener;

    private ChannelHomeFragment channelHomeFragment;
    private ChannelCommunityFragment channelCommunityFragment;
    private ChannelInfomationFragment channelInfomationFragment;
    private ChannelPlaylistFragment channelPlaylistFragment;
    private ChannelVideoFragment channelVideoFragment;

    public ChannelPagerAdapter( Fragment fragment, int count, OnCallbackResponseListener onCallbackResponseListener)
    {
        super(fragment);
        this.count = count;
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    public ViewPagerFragmentData getCurrentFragmentData(int position)
    {
        Fragment fragment;
        String tag;

        switch (position)
        {
            case 0 :

                fragment = channelHomeFragment;
                tag = FragmentTagUtil.FRAGMENT_TAG_CHANNEL_HOME;

                break;
            case 1 :

                fragment = channelVideoFragment;
                tag = FragmentTagUtil.FRAGMENT_TAG_CHANNEL_VIDEO;

                break;
            case 2 :

                fragment = channelPlaylistFragment;
                tag = FragmentTagUtil.FRAGMENT_TAG_CHANNEL_PLAYLIST;

                break;
            case 3 :

                fragment = channelCommunityFragment;
                tag = FragmentTagUtil.FRAGMENT_TAG_CHANNEL_COMMUNITY;

                break;

            case 4 :

                fragment = channelInfomationFragment;
                tag = FragmentTagUtil.FRAGMENT_TAG_CHANNEL_INFORMATION;

                break;
            default :

                fragment = null;
                tag = null;

                break;
        }

        return new ViewPagerFragmentData(fragment, tag);
    }


    @Override
    public Fragment createFragment(int position)
    {
        Fragment fragment;

        switch (position)
        {
            case 0 :

                channelHomeFragment = new ChannelHomeFragment(onCallbackResponseListener);
                fragment = channelHomeFragment;

                break;
            case 1 :

                channelVideoFragment = new ChannelVideoFragment(onCallbackResponseListener);
                fragment = channelVideoFragment;

                break;
            case 2 :

                channelPlaylistFragment = new ChannelPlaylistFragment(onCallbackResponseListener);
                fragment = channelPlaylistFragment;

                break;
            case 3 :

                channelCommunityFragment = new ChannelCommunityFragment(onCallbackResponseListener);
                fragment = channelCommunityFragment;

                break;

            case 4 :

                channelInfomationFragment = new ChannelInfomationFragment(onCallbackResponseListener);
                fragment = channelInfomationFragment;

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

    public void setChannelInfomation(ChannelDTO channel)
    {
        channelInfomationFragment.setChannelInfomation(channel);
    }


}
