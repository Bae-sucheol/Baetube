package com.example.baetube.recyclerview.item;

import android.graphics.Bitmap;
import android.net.Uri;

public class RecyclerViewStorageVideoItem
{
    private String displayName;
    private Uri uri;
    private Bitmap bitmap;

    public RecyclerViewStorageVideoItem()
    {
    }

    public RecyclerViewStorageVideoItem(String displayName, Uri uri, Bitmap bitmap)
    {
        this.displayName = displayName;
        this.uri = uri;
        this.bitmap = bitmap;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public Uri getUri()
    {
        return uri;
    }

    public Bitmap getBitmap()
    {
        return bitmap;
    }

    public void setUri(Uri uri)
    {
        this.uri = uri;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }
}
