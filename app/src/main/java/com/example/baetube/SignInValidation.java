package com.example.baetube;

import java.util.regex.Pattern;

public class SignInValidation
{
    // 이메일 검사
    public static boolean isEmail(String str)
    {
        return Pattern.matches("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$", str);
    }

    // 폰번호 검사
    public static boolean isPhone(String str)
    {
        return Pattern.matches("^\\d{2,3}\\d{3,4}\\d{4}$", str);
    }

    // 비밀번호 검사 (문자1개 이상, 숫자 1개이상의 8글자 이상)
    public static boolean isPassword(String str)
    {
        return Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", str);
    }

    // 이름 검사 한글로 2 ~ 5
    public static boolean isName(String str)
    {
        return Pattern.matches("^[가-힣]{2,5}$", str);
    }
}
