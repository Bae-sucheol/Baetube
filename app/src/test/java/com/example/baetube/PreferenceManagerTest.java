package com.example.baetube;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.mock.MockContext;

import com.example.baetube.activity.MainActivity;
import com.google.common.base.Verify;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Verifier;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mockStatic;

public class PreferenceManagerTest
{
    @Mock
    private static MockedStatic<PreferenceManager> preferenceManager;
    @Mock
    private Context context;
    @Mock
    private SharedPreferences sharedPreferences;
    @Mock
    private SharedPreferences.Editor editor;

    private String testKey;
    private String testValue;
    @Before
    public void before()
    {
        MockitoAnnotations.openMocks(this);
        //preferenceManager = mockStatic(PreferenceManager.class);

        testKey = "testKey";
        testValue = "testValue";

        Mockito.when(PreferenceManager.getPreferences(context)).thenReturn(sharedPreferences);
        Mockito.when(PreferenceManager.getString(context, testKey)).thenReturn(testValue);
        Mockito.when(sharedPreferences.edit()).thenReturn(editor);
        Mockito.when(sharedPreferences.getString(testKey, "")).thenReturn(testValue);
    }

    @Test
    public void setStringTest()
    {
        PreferenceManager.setString(context, testKey, testValue);
        String value = PreferenceManager.getString(context, testKey);

        // putString이 적어도 한번 실행 되었는지 확인
        Mockito.verify(editor, Mockito.atLeastOnce()).putString(testKey, testValue);
        // 반환받은 값이 동일한지 확인
        Assert.assertEquals(testValue, value);
    }

    @Test
    public void removeKeyTest()
    {
        // 값을 추가하고
        PreferenceManager.setString(context, testKey, testValue);
        // 추가한 키를 삭제한다.
        PreferenceManager.removekey(context, testKey);

        // putString 메소드가 적어도 한번 실행 되었는지 확인
        Mockito.verify(editor, Mockito.atLeastOnce()).putString(testKey, testValue);
        // remove 메소드가 적어도 한번 실행 되었는지 확인
        Mockito.verify(editor, atLeastOnce()).remove(testKey);
    }

    @Test
    public void clearTest()
    {
        // 값을 추가하고
        PreferenceManager.setString(context, testKey, testValue);
        // 추가한 키를 삭제한다.
        PreferenceManager.clear(context);

        // putString 메소드가 적어도 한번 실행 되었는지 확인
        Mockito.verify(editor, Mockito.atLeastOnce()).putString(testKey, testValue);
        // remove 메소드가 적어도 한번 실행 되었는지 확인
        Mockito.verify(editor, atLeastOnce()).clear();
    }

    @After
    public void after()
    {
        preferenceManager.close();
    }
}
