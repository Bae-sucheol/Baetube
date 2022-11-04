package com.example.baetube.dto;

public class UserDTO
{
    private int user_id;
    private String password;
    private String name;
    private String email;
    private String birth;
    private String fcm_token;
    private String phone;
    private String address;
    private String reg_date;
    private boolean gender;

    // constructor
    public UserDTO(int user_id, String password, String name, String email, String birth, String fcm_token, String phone, String address, String reg_date, boolean gender)
    {
        this.user_id = user_id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.fcm_token = fcm_token;
        this.phone = phone;
        this.address = address;
        this.reg_date = reg_date;
        this.gender = gender;
    }

    // getter
    public int getUser_id()
    {
        return user_id;
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

    public String getFcm_token()
    {
        return fcm_token;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getAddress()
    {
        return address;
    }

    public String getReg_date()
    {
        return reg_date;
    }

    public boolean getGender()
    {
        return gender;
    }

    // setter
    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
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

    public void setFcm_token(String fcm_token)
    {
        this.fcm_token = fcm_token;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setReg_date(String reg_date)
    {
        this.reg_date = reg_date;
    }

    public void setGender(boolean gender)
    {
        this.gender = gender;
    }
}
