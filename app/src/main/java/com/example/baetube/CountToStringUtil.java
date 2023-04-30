package com.example.baetube;

public class CountToStringUtil
{
    private static Integer unit = 10;

    public static String countToString(Integer count)
    {
        String value = String.valueOf(count);

        if(count >= Math.pow(unit, 4))
        {
            int length = value.length();
            String quot = value.substring(0, length - 4);
            String remain = value.substring(length - 4, length);

            return quot + "." + remain + " 만";
        }
        else
        {
            return value;
        }
    }
}
