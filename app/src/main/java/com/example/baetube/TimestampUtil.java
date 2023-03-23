package com.example.baetube;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampUtil
{
    public static Timestamp StringToTimestamp(String string)
    {
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(string);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());

            return timestamp;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
