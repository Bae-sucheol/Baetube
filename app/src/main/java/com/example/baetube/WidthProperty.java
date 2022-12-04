package com.example.baetube;

import android.util.Property;
import android.view.View;

public class WidthProperty extends Property<View, Integer>
{
    public WidthProperty()
    {
        super(Integer.class, "width");
    }

    @Override
    public Integer get(View view)
    {
        return view.getWidth();
    }

    @Override
    public void set(View object, Integer value)
    {
        object.getLayoutParams().width = value;
        object.setLayoutParams(object.getLayoutParams());
    }
}
