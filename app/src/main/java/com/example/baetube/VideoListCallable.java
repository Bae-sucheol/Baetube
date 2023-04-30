package com.example.baetube;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
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
        ArrayList<File> fileList = new ArrayList<>();
        File downloadFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File videoFiles[] = downloadFile.listFiles();

        for (int i = 0; i < videoFiles.length; i++)
        {
            String pathSplit[] = videoFiles[i].getPath().split("[.]");

            int length = pathSplit.length;

            if(!pathSplit[length - 1].equals("jpg") && !pathSplit[length - 1].equals("png"))
            {
                fileList.add(videoFiles[i]);
            }
        }

        return fileList;
    }

}
