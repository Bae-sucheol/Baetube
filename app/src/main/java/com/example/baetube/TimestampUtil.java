package com.example.baetube;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
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
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

            return timestamp;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
