package com.example.baetube.dto;

public class ContentsUUID
{
    private String VideoUUID;
    private String ImageUUID;

    // constructor
    public ContentsUUID()
    {
        super();
    }

    public ContentsUUID(String videoUUID, String imageUUID)
    {
        super();
        VideoUUID = videoUUID;
        ImageUUID = imageUUID;
    }

    // getter
    public String getVideoUUID()
    {
        return VideoUUID;
    }

    public String getImageUUID()
    {
        return ImageUUID;
    }

    // setter
    public void setVideoUUID(String videoUUID)
    {
        VideoUUID = videoUUID;
    }
    public void setImageUUID(String imageUUID)
    {
        ImageUUID = imageUUID;
    }

}
