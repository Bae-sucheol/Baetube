package com.example.baetube;

import android.widget.FrameLayout;

import com.example.baetube.recyclerview.item.RecyclerViewVideoItem;

public interface OnFragmentInteractionListener
{
    public void onVideoItemClick(RecyclerViewVideoItem videoItem);
    public void onCompletelyVisible(FrameLayout layout, String uuid);
}
