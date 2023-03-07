package com.example.baetube.dto;

import androidx.fragment.app.Fragment;

public class ViewPagerFragmentData
{
    private Fragment fragment;
    private String tag;

    public ViewPagerFragmentData()
    {
    }

    public ViewPagerFragmentData(Fragment fragment, String tag)
    {
        this.fragment = fragment;
        this.tag = tag;
    }

    public Fragment getFragment()
    {
        return fragment;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public void setFragment(Fragment fragment)
    {
        this.fragment = fragment;
    }
}
