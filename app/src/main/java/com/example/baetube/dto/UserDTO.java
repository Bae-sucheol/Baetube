package com.example.baetube.dto;

public class UserDTO
{
    private int userId;
    private String password;
    private String name;
    private String email;
    private String birth;
    private String fcmToken;
    private String phone;
    private String address;
    private String regDate;
    private boolean gender;

    // constructor
    public UserDTO(int userId, String password, String name, String email, String birth, String fcmToken, String phone, String address, String regDate, boolean gender)
    {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.fcmToken = fcmToken;
        this.phone = phone;
        this.address = address;
        this.regDate = regDate;
        this.gender = gender;
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

    public String getBirth()
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

    public String getRegDate()
    {
        return regDate;
    }

    public boolean getGender()
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

    public void setBirth(String birth)
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

    public void setRegDate(String regDate)
    {
        this.regDate = regDate;
    }

    public void setGender(boolean gender)
    {
        this.gender = gender;
    }
}
