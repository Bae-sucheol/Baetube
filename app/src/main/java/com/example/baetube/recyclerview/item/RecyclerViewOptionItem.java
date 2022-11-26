package com.example.baetube.recyclerview.item;

import android.graphics.drawable.Drawable;

public class RecyclerViewOptionItem
{
    private int resource;
    private String option;

    // getter
    public int getResource()
    {
        return resource;
    }

    public String getOption()
    {
        return option;
    }

    // setter
    public void setResource(int resource)
    {
        this.resource = resource;
    }

    public void setOption(String option)
    {
        this.option = option;
    }
}
