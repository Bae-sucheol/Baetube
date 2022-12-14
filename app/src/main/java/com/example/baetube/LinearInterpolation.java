package com.example.baetube;

public class LinearInterpolation
{
    /**
     * 선형 보간
     */
    public static float Lerp(float start, float end, float interval)
    {
        return start * (1 - interval) + end * interval;
    }
}
