package com.example.baetube;

import java.sql.Timestamp;

public class DateToStringUtil
{
    private static long second = 1000;
    private static long minute = second * 60;
    private static long hour = minute * 60;
    private static long day = hour * 24;
    private static long week = day * 7;
    private static long month = week * 4;
    private static long year = day * 365;

    public static String dateToString(Timestamp date)
    {
        long now = System.currentTimeMillis() + hour * 9;

        long uploaded = date.getTime();

        long sub = now - uploaded;

        long timeUnit;
        String suffix;

        if(sub >= year)
        {
            timeUnit = sub / year;
            suffix = "년 전";
        }
        else if(sub >= month)
        {
            timeUnit = sub / month;
            suffix = "개월 전";
        }
        else if(sub >= week)
        {
            timeUnit = sub / week;
            suffix = "주 전";
        }
        else if(sub >= day)
        {
            timeUnit = sub / day;
            suffix = "일 전";
        }
        else if(sub >= hour)
        {
            timeUnit = sub / hour;
            suffix = "시간 전";
        }
        else if(sub >= minute)
        {
            timeUnit = sub / minute;
            suffix = "분 전";
        }
        else
        {
            timeUnit = sub / second;
            suffix = "초 전";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(timeUnit);
        stringBuilder.append(suffix);

        return  stringBuilder.toString();
    }
}
