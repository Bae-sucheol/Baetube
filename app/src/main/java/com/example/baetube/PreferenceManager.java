package com.example.baetube;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager
{
    public static final String PREFERENCES_NAME = "preference";
    public static final String PREFERENCES_ACCESSKEY = "accessToken";
    public static final String PREFERENCES_REFRESHKEY = "refreshToken";
    private static final String DEFAULT_VALUE_STRING = "";

    public static SharedPreferences getPreferences(Context context)
    {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    /**
     * String 값 저장
     * @param context
     * @param key - 키
     * @param value - 값
     */
    public static void setString(Context context, String key, String value)
    {
        SharedPreferences sharedPreferences = getPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 값 리턴
     * @param context
     * @param key
     * @return value
     */
    public static String getString(Context context, String key)
    {
        SharedPreferences sharedPreferences = getPreferences(context);
        String value = sharedPreferences.getString(key, DEFAULT_VALUE_STRING);
        return value;
    }

    /**
     * 키-값 삭제
     * @param context
     * @param key
     */
    public static void removekey(Context context, String key)
    {
        SharedPreferences sharedPreferences = getPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 전부 일괄 삭제
     * @param context
     */
    public static void clear(Context context)
    {
        SharedPreferences sharedPreferences = getPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

}
