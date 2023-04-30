package com.example.baetube.dto;

public class TokenInfoDTO
{
    private String grantType;
    private String accessToken;
    private String refreshToken;

    // constructor
    public TokenInfoDTO()
    {
        super();
    }

    public TokenInfoDTO(String grantType, String accessToken, String refreshToken)
    {
        super();
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // getter
    public String getGrantType()
    {
        return grantType;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }

    // setter
    public void setGrantType(String grantType)
    {
        this.grantType = grantType;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken)
    {
        this.refreshToken = refreshToken;
    }
}
