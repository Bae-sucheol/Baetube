package com.example.baetube.dto;

import java.io.File;

public class FileUploadDTO
{
    public static String TYPE_IMAGE = "image";
    public static String TYPE_VIDEO = "video";
    public static String PURPOSE_PROFILE = "profile";
    public static String PURPOSE_THUMBNAIL = "thumbnail";
    public static String PURPOSE_ARTS = "arts";
    public static String PURPOSE_COMMUNITY = "community";
    public static String PURPOSE_VIDEO = "video";

    private File file;
    private String type;
    private String purpose;
    private String id;

    public FileUploadDTO()
    {
    }

    public FileUploadDTO(File file, String type, String purpose, String id)
    {
        this.file = file;
        this.type = type;
        this.purpose = purpose;
        this.id = id;
    }

    public File getFile()
    {
        return file;
    }

    public String getType()
    {
        return type;
    }

    public String getPurpose()
    {
        return purpose;
    }

    public String getId()
    {
        return id;
    }

    public void setFile(File file)
    {
        this.file = file;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public void setPurpose(String purpose)
    {
        this.purpose = purpose;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
