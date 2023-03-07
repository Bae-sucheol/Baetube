package com.example.baetube.dto;

public class ChangePasswordRequest
{
    private String email;
    private String password;
    private String newPassword;

    public ChangePasswordRequest(String email, String password, String newPassword)
    {
        super();
        this.email = email;
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }
}
