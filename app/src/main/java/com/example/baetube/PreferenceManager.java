package com.example.baetube;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager
{
    public static final String PREFERENCES_NAME = "preference";
    public static final String PREFERENCES_ACCESSKEY = "accessToken";
    public static final String PREFERENCES_REFRESHKEY = "refreshToken";
    public static final String PREFERENCES_FCM = "fcmToken";
    public static final String PREFERENCES_ARTS = "arts";
    public static final String PREFERENCES_PROFILE = "profile";
    public static final String PREFERENCES_THUMBNAIL = "thumbnail";
    public static final String PREFERENCES_COMMUNITY = "community";
    public static final String PREFERENCES_CHANNEL_SEQUENCE = "channelSequence";
    public static final String DEFAULT_VALUE_STRING = "";

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
    public static void removeKey(Context context, String key)
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

    public static String getChannelSequence(Context context)
    {
        String value = getString(context, PreferenceManager.PREFERENCES_CHANNEL_SEQUENCE);

        // 기존에 설정된 값이 존재하지 않는다면 값을 0으로 설정하고 0을 반환
        if(value == PreferenceManager.DEFAULT_VALUE_STRING)
        {
            setString(context, PREFERENCES_CHANNEL_SEQUENCE, "0");
            return "0";
        }

        // 기존에 설정된 값이 존재한다면 해당 값을 리턴한다.
        return value;
    }

}
