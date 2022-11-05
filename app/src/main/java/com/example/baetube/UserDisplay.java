package com.example.baetube;

public class UserDisplay
{
    private static double width;
    private static double height;
    private static double ratio = 0.5625;

    public static double getWidth()
    {
        return width;
    }

    public static double getHeight()
    {
        return height;
    }

    public static double getRatio()
    {
        return ratio;
    }

    public static void setWidth(double width)
    {
        UserDisplay.width = width;
    }

    public static void setHeight(double height)
    {
        UserDisplay.height = height;
    }

    public static void setRatio(double ratio)
    {
        UserDisplay.ratio = ratio;
    }
}
