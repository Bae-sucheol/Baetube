package com.example.baetube;

import android.graphics.Bitmap;

import com.example.baetube.dto.CommunityDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.dto.VoteDTO;

import java.io.File;
import java.util.List;

public interface OnUploadDataListener
{
    public void onResponseVideoFile(File file);
    public void onResponseVideoThumbnail(Bitmap bitmap);
    public void onResponseVideoInformation(VideoDTO video);
    public void onResponseVideoAge(Integer age);
    public void onResponseUploadVideoRequest();
    public void onResponseCommunityInformation(CommunityDTO community);
    public void onResponseCommunityImage(Bitmap bitmap);
    public void onResponseCommunityVote(VoteDTO voteData, List<VoteDTO> voteOptions);
    public void onResponseUploadCommunityRequest();
}
