package com.example.baetube;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class VideoListCallable implements Callable<ArrayList<File>>
{

    @Override
    public ArrayList<File> call() throws Exception
    {
        return getVideoList();
    }

    private ArrayList<File> getVideoList()
    {
        ArrayList<File> fileList;
        File downloadFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File videoFiles[] = downloadFile.listFiles();

        fileList = new ArrayList<>(Arrays.asList(videoFiles));

        return fileList;
    }

}
