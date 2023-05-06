package com.example.baetube.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.FragmentTagUtil;
import com.example.baetube.OkHttpUtil;
import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.OnUploadDataListener;
import com.example.baetube.PreferenceManager;
import com.example.baetube.R;
import com.example.baetube.VideoListCallable;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.CommunityDTO;
import com.example.baetube.dto.FileUploadDTO;
import com.example.baetube.dto.VideoDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.fragment.upload.UploadCommunityFragment;
import com.example.baetube.fragment.upload.UploadVideoInformationFragment;
import com.example.baetube.fragment.upload.UploadVideoListFragment;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Response;

public class UploadActivity extends AppCompatActivity
{
    // 프래그먼트 매니저
    private FragmentManager fragmentManager;
    // 프래그먼트 트랜잭션
    private FragmentTransaction fragmentTransaction;
    // 동영상 목록을 불러오거나, 업로드 시 사용할 쓰레드가 필요하다.
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    // 프래그먼트와 통신하기 위한 인터페이스
    private OnUploadDataListener onUploadDataListener;
    // 선택한 동영상 파일
    private File selectedFile;
    // 선택한 썸네일 or 커뮤니티 게시글 이미지
    private Bitmap selectedImage;
    // 입력받은 동영상 정보를 저장하기 위한 객체
    private VideoDTO videoInformation;
    // 입력받은 게시글 정보를 저장하기 위한 객체
    private ChannelDTO channel;
    // 서버와 통신하기 위한
    private OkHttpUtil okHttpUtil;
    // 서버에 요청한 응답 결과를 처리하기 위한 인터페이스
    private OnCallbackResponseListener onCallbackResponseListener;

    // 커뮤니티 게시글의 전반적인 내용을 담고있는 객체
    private CommunityDTO communityInformation;

    // 커뮤니티 게시글에 사용될 투표.
    private VoteDTO vote;
    private List<VoteDTO> voteOptionList = new ArrayList<>();

    // 액티비티 리절트 런처 = 예전에 사용하던 방식은 권장되지 않아 현재 방식을 적용
    public ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if(result.getResultCode() == RESULT_OK)
                    {
                        Intent intent = result.getData();
                        Uri uri = intent.getData();

                        //System.out.println("Uri : " + uri);
                        //System.out.println("path : " + uri.getPath());

                        // /document/raw:경로 로 시작하기 때문에 그냥 넘기면 에러를 발생시켜 :를 구분자로 하여 2개로 나누어 뒷 부분을 경로로 사용한다.
                        //String filePaths[] = uri.getPath().split(":", 2);

                        //File file = new File(filePaths[1]);
                        File file = new File(getRealPathFromURI(uri));

                        UploadCommunityFragment uploadCommunityFragment = (UploadCommunityFragment) fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_UPLOAD_COMMUNITY);

                        if(uploadCommunityFragment != null)
                        {
                            uploadCommunityFragment.setThumbnail(file);
                            return;
                        }

                        UploadVideoInformationFragment uploadVideoInformationFragment = (UploadVideoInformationFragment) fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_UPLOAD_VIDEO);

                        if(uploadVideoInformationFragment != null)
                        {
                            uploadVideoInformationFragment.setThumbnail(file);
                        }

                        //FileUploadUtils.send2Server(file, player, getApplicationContext());
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Intent intent = getIntent();
        int uploadType = intent.getIntExtra("uploadType", 0);

        setOnUploadDataListener();

        setOnCallbackResponseListener();

        init(uploadType);
    }

    private void init(int uploadType)
    {
        switch (uploadType)
        {
            case 1 :
                // 비디오 업로드 타입일 때
                fragmentTransaction.add(R.id.activity_upload_layout_main, new UploadVideoListFragment(onUploadDataListener), FragmentTagUtil.FRAGMENT_TAG_UPLOAD_VIDEO_LIST);
                fragmentTransaction.commitNow();

                // 커밋 후 동영상 리스트를 불러오는 작업을 요청한다.
                Future<ArrayList<File>> future = executorService.submit(new VideoListCallable());

                try
                {
                    // get으로 작업 결과를 기다린다.
                    ArrayList<File> videoList = future.get();

                    // 받은 결과를 onLoadVideoListener를 사용하여 프래그먼트로 전송.
                    UploadVideoListFragment fragment = (UploadVideoListFragment)fragmentManager.findFragmentByTag(FragmentTagUtil.FRAGMENT_TAG_UPLOAD_VIDEO_LIST);

                    if(fragment != null)
                    {
                        fragment.setVideoList(videoList);
                    }

                }
                catch (Exception e) // 별다른 익셉션은 사용하지 않으므로 문제가 생기면 에러만 출력한다.
                {
                    e.printStackTrace();
                }

                break;
            case 2 :
                // 게시글 업로드 타입일 때
                fragmentTransaction.add(R.id.activity_upload_layout_main, new UploadCommunityFragment(onUploadDataListener),
                        FragmentTagUtil.FRAGMENT_TAG_UPLOAD_COMMUNITY);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            default :
                // 예외 처리.
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        if(fragmentManager.getBackStackEntryCount() == 0)
        {
            finish();
        }

    }

    private void setOnUploadDataListener()
    {
        onUploadDataListener = new OnUploadDataListener()
        {
            @Override
            public void onResponseVideoFile(File file)
            {
                selectedFile = file;
            }

            @Override
            public void onResponseVideoThumbnail(Bitmap bitmap)
            {
                selectedImage = bitmap;
            }

            @Override
            public void onResponseVideoInformation(VideoDTO video)
            {
                videoInformation = video;
            }

            @Override
            public void onResponseVideoAge(Integer age)
            {
                videoInformation.setAge(age);
            }

            @Override
            public void onResponseUploadVideoRequest()
            {
                /*
                 * 서버에 업로드를 요청.
                 * 1. 썸네일 이미지, 동영상 업로드. 업로드 후 관련 데이터를 받는다.
                 * 2. 받은 데이터를 받아 video 데이터에 추가하고 db에 삽입을 요청한다.
                 */
                okHttpUtil = new OkHttpUtil();

                videoInformation.setChannelId(4);

                String url = getString(R.string.api_url_video_insert) + PreferenceManager.getChannelSequence(getApplicationContext());

                ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_INSERT);

                okHttpUtil.sendPostRequest(videoInformation, url, returnableCallback);
            }

            @Override
            public void onResponseCommunityInformation(CommunityDTO communityData)
            {
                System.out.println("여기서 적용이 되나요?");
                System.out.println("communityData : " + communityData.getComment());
                communityInformation = communityData;

                System.out.println("communityInformation : " + communityInformation.getComment());
            }

            @Override
            public void onResponseCommunityImage(Bitmap bitmap)
            {
                selectedImage = bitmap;
            }

            @Override
            public void onResponseCommunityVote(VoteDTO voteData, List<VoteDTO> voteOptions)
            {
                vote = voteData;
                voteOptionList = voteOptions;
            }

            @Override
            public void onResponseUploadCommunityRequest()
            {
                /*
                 * 서버에 업로드를 요청.
                 * 1. 커뮤니티 게시글에 이미지가 없다면 noneImage를 추가하여 서버단에서 처리할 수 있도록 한다.
                 */
                okHttpUtil = new OkHttpUtil();

                if(selectedImage == null)
                {
                    communityInformation.setImageUrl("noneImage");
                }

                String url = getString(R.string.api_url_community_insert) + PreferenceManager.getChannelSequence(getApplicationContext());

                ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_INSERT);

                okHttpUtil.sendPostRequest(communityInformation, url, returnableCallback);
            }
        };
    }

    // 비트맵을 파일로 변환
    private String saveBitmapConvertFile(Bitmap bitmap)
    {
        File storage = getApplicationContext().getCacheDir(); // 이 부분이 임시파일 저장 경로

        String fileName = "cache" + ".jpg";  // 파일이름은 마음대로!

        File tempFile = new File(storage, fileName);

        try
        {
            // 자동으로 빈 파일을 생성합니다.
            tempFile.createNewFile();

            // 파일을 쓸 수 있는 스트림을 준비합니다.
            FileOutputStream out = new FileOutputStream(tempFile);

            // compress 함수를 사용해 스트림에 비트맵을 저장합니다.
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            // 스트림 사용후 닫아줍니다.
            out.close();

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return tempFile.getAbsolutePath();
    }

    // 임시 파일을 삭제하는 메소드
    private void DeleteCacheFile(String cacheFilePath)
    {
        File cacheFile = new File(cacheFilePath);

        try
        {
            if(cacheFile.exists())
            {
                cacheFile.delete();
            }
        }
        catch (Exception e)
        {

        }
    }

    private String getRealPathFromURI(Uri contentUri)
    {
        if (contentUri.getPath().startsWith("/storage"))
        {
            return contentUri.getPath();
        }

        String id = DocumentsContract.getDocumentId(contentUri).split(":")[1];
        String[] columns = { MediaStore.Files.FileColumns.DATA };
        String selection = MediaStore.Files.FileColumns._ID + " = " + id;
        Cursor cursor = getContentResolver().query(MediaStore.Files.getContentUri("external"), columns, selection, null, null);
        try
        {
            int columnIndex = cursor.getColumnIndex(columns[0]);
            if (cursor.moveToFirst())
            {
                return cursor.getString(columnIndex);
            }
        }
        finally
        {
            cursor.close();
        }
        return null;
    }

    private void setOnCallbackResponseListener()
    {
        onCallbackResponseListener = new OnCallbackResponseListener()
        {
            @Override
            public void onResponse(Response response)
            {
                // 투표 삽입까지 완벽하게 진행되었다면.
                if(response.isSuccessful())
                {
                    System.out.println("삽입 완료.");
                }
            }

            @Override
            public void onExpiredAccessTokenResponse()
            {

            }

            @Override
            public void onExpiredRefreshTokenResponse()
            {

            }

            @Override
            public void onGeneratedAccessTokenResponse(String object)
            {

            }

            @Override
            public void onLoginUserResponse(String object)
            {

            }

            @Override
            public void onVisitChannelResponse(String object)
            {

            }

            @Override
            public void onSubscribersChannelResponse(String object)
            {

            }

            @Override
            public void onVisitCommunityResponse(String object)
            {

            }

            @Override
            public void onSelectReplyResponse(String object)
            {

            }

            @Override
            public void onSelectNestedReplyResponse(String object)
            {

            }

            @Override
            public void onSelectPlaylistResponse(String object)
            {

            }

            @Override
            public void onSelectSearchHistoryResponse(String object)
            {

            }

            @Override
            public void onSelectChannelVideoResponse(String object)
            {

            }

            @Override
            public void onSelectHistoryVideoResponse(String object)
            {

            }

            @Override
            public void onSelectMainVideoResponse(String object)
            {

            }

            @Override
            public void onSelectPlaylistVideoResponse(String object)
            {

            }

            @Override
            public void onSelectSubscribeVideoResponse(String object)
            {

            }

            @Override
            public void onSelectViewVideoResponse(String object)
            {

            }

            @Override
            public void onSelectVoteOptionResponse(String object)
            {

            }

            @Override
            public void onSelectSubscribeResponse(String object)
            {

            }

            @Override
            public void onInsertResponse(String object)
            {
                System.out.println("response : " + object);

                okHttpUtil = new OkHttpUtil();

                // 받아온 json 배열을 List로 변환하여 핸들링한다.
                //String array[] = new GsonBuilder().create().fromJson(object, String[].class);
                //List<String> uuidList = Arrays.asList(array);

                //System.out.println("uuid : " + uuidList.get(1));
                //System.out.println("파일 경로 : " + thumbnailFile.getAbsolutePath());
                //System.out.println("파일 크기 : " + thumbnailFile.getTotalSpace());

                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(object);

                String insertType = element.getAsJsonObject().get("insertType").getAsString();
                System.out.println("insert type : " + insertType);

                // 동영상 삽입 요청 후 받는 반환값 처리
                if(insertType.equals("video"))
                {
                    // videoUUID, thumbnailUUID을 가져온다.
                    String videoUUID = element.getAsJsonObject().get("videoUUID").getAsString();
                    String thumbnailUUID = element.getAsJsonObject().get("thumbnailUUID").getAsString();
                    File thumbnailFile = new File(saveBitmapConvertFile(selectedImage));

                    FileUploadDTO fileUpload = new FileUploadDTO(thumbnailFile, FileUploadDTO.TYPE_IMAGE, FileUploadDTO.PURPOSE_THUMBNAIL, thumbnailUUID);

                    ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

                    okHttpUtil.sendFileRequest(fileUpload, returnableCallback);


                    fileUpload = new FileUploadDTO(selectedFile, FileUploadDTO.TYPE_VIDEO, FileUploadDTO.PURPOSE_VIDEO, videoUUID);

                    returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

                    okHttpUtil.sendFileRequest(fileUpload, returnableCallback);
                }
                else if(insertType.equals("community")) // 커뮤니티 게시글 삽입 후 받는 반환값 처리
                {
                    // imageUUID, communityId을 가져온다.
                    String imageUUID = element.getAsJsonObject().get("imageUUID").getAsString();
                    int communityId = element.getAsJsonObject().get("communityId").getAsInt();

                    // 업로드할 이미지가 존재한다면 업로드를 요청한다.
                    if(imageUUID != null)
                    {
                        File imageFile = new File(saveBitmapConvertFile(selectedImage));

                        FileUploadDTO fileUpload = new FileUploadDTO(imageFile, FileUploadDTO.TYPE_IMAGE, FileUploadDTO.PURPOSE_COMMUNITY, imageUUID);

                        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

                        okHttpUtil.sendFileRequest(fileUpload, returnableCallback);
                    }

                    // 삽입할 투표 내용이 존재한다면 삽입 요청
                    if(vote != null)
                    {
                        // 요청 전 삽입 요청할 객체에 받아온 게시글의 id를 삽입하여 보낸다.
                        vote.setCommunityId(communityId);

                        String url = getString(R.string.api_url_vote_insert);

                        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_INSERT);

                        okHttpUtil.sendPostRequest(vote, url, returnableCallback);
                    }
                }
                else if(insertType.equals("vote")) // 투표 삽입 후 받는 반환값 처리
                {
                    // voteId을 가져온다.
                    int voteId = element.getAsJsonObject().get("voteId").getAsInt();

                    // 보내기 전 전달받은 voteId를 적용하여 보내준다.
                    // 첫번째 데이터에만 적용하면 되지만 전송 후 전달 받을 시 어떤 순서로 전달받을지 알 수 없어
                    // 모든 객체에 적용하기로 했다(어차피 단위가 작아 작업에 부하가 적다)
                    for(VoteDTO vote : voteOptionList)
                    {
                        vote.setVoteId(voteId);
                    }

                    String url = getString(R.string.api_url_vote_insert_option_multi);

                    ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

                    okHttpUtil.sendPostRequest(voteOptionList, url, returnableCallback);
                }

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        finish();
                    }
                });

            }

            @Override
            public void onRateResponse(String object)
            {

            }

            @Override
            public void onSubscribeResponse(String object)
            {

            }

            @Override
            public void onUnSubscribeResponse(String object)
            {

            }

            @Override
            public void onReplyResponse(String object)
            {

            }

            @Override
            public void onNestedReplyResponse(String object)
            {

            }

            @Override
            public void onSignInResponse(String object)
            {

            }

            @Override
            public void onSelectRelatedVideoResponse(String object)
            {

            }

            @Override
            public void onSaveFCMTokenResponse(boolean result)
            {

            }

            @Override
            public void onSelectChannelDataResponse(String object)
            {

            }

            @Override
            public void onUpdateChannelResponse(String object)
            {

            }

            @Override
            public void onSelectCommunityDataResponse(String object)
            {

            }

            @Override
            public void onUpdateCommunityResponse(String object)
            {

            }

            @Override
            public void onSelectVideoDataResponse(String object)
            {

            }

            @Override
            public void onUpdateVideoResponse(String object)
            {

            }

            @Override
            public void onSelectPlaylistDataResponse(String object)
            {

            }

            @Override
            public void onUpdatePlaylistResponse(String object)
            {

            }

            @Override
            public void onSelectCategoryResponse(String object)
            {

            }

            @Override
            public void onSelectSubscribersCommunityResponse(String object)
            {

            }

            @Override
            public void onSelectVideoNotificationResponse(String object)
            {

            }

            @Override
            public void onSelectCommunityNotificationResponse(String object)
            {

            }

            @Override
            public void onSelectSearchVideoResponse(String object)
            {

            }

            @Override
            public void onSelectSearchChannelResponse(String object)
            {

            }

            @Override
            public void onSelectChannelDataAllResponse(String object)
            {

            }

            @Override
            public void onSelectNewNotifications(String object)
            {

            }

            @Override
            public void onSelectUserDataResponse(String object)
            {

            }

        };
    }

}