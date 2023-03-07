package com.example.baetube.dto;

import java.sql.Date;
import java.sql.Timestamp;

public class UserDTO
{
    private Integer userId;
    private String email;
    private String password;
    private String name;
    private Integer gender;
    private Timestamp birth;
    private String fcmToken;
    private String phone;
    private String address;
    private Timestamp regDate;

    // constructor
    public UserDTO()
    {
        super();
    }

    public UserDTO(Integer userId, String email, String password, String name, Integer gender, Timestamp birth, String fcmToken, String phone, String address, Timestamp regDate)
    {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.fcmToken = fcmToken;
        this.phone = phone;
        this.address = address;
        this.regDate = regDate;
    }

    public UserDTO(String email, String password, String name, Integer gender, Timestamp birth, String fcmToken, String phone, String address, Timestamp regDate)
    {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.fcmToken = fcmToken;
        this.phone = phone;
        this.address = address;
        this.regDate = regDate;
    }

    public UserDTO(String email, String password, String name, Integer gender, String fcmToken, String phone, String address)
    {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.fcmToken = fcmToken;
        this.phone = phone;
        this.address = address;
    }

    public UserDTO(String email)
    {
        this.email = email;
    }

    // getter
    public int getUserId()
    {
        return userId;
    }

    public String getPassword()
    {
        return password;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public Timestamp getBirth()
    {
        return birth;
    }

    public String getFcmToken()
    {
        return fcmToken;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getAddress()
    {
        return address;
    }

    public Timestamp getRegDate()
    {
        return regDate;
    }

    public Integer getGender()
    {
        return gender;
    }

    // setter
    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setBirth(Timestamp birth)
    {
        this.birth = birth;
    }

    public void setFcmToken(String fcmToken)
    {
        this.fcmToken = fcmToken;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setRegDate(Timestamp regDate)
    {
        this.regDate = regDate;
    }

    public void setGender(Integer gender)
    {
        this.gender = gender;
    }
}
