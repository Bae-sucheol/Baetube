package com.example.baetube.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.FragmentTagUtil;
import com.example.baetube.OkHttpUtil;
import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.R;
import com.example.baetube.dto.SearchHistoryDTO;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.adapter.RecyclerViewSearchHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements View.OnClickListener, OnRecyclerViewClickListener
{
    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewSearchHistoryAdapter adapter;
    private ArrayList<SearchHistoryDTO> list = new ArrayList<>();

    private OnCallbackResponseListener onCallbackResponseListener;
    private OkHttpUtil okHttpUtil;

    private TextView textSearch;
    private ImageView buttonBack;
    private ImageView buttonSTT;

    private boolean isRecording = false;
    private Intent intent;
    private SpeechRecognizer speechRecognizer;
    private RecognitionListener listener;

    public SearchFragment(OnCallbackResponseListener onCallbackResponseListener)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_search, container, false);

        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */
        recyclerView = view.findViewById(R.id.fragment_search_recyclerview);
        adapter = new RecyclerViewSearchHistoryAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnRecyclerViewClickListener(this);

        textSearch = view.findViewById(R.id.fragment_search_text_search);
        buttonBack = view.findViewById(R.id.fragment_search_image_button_back);
        buttonSTT = view.findViewById(R.id.fragment_search_image_button_stt);

        buttonBack.setOnClickListener(this);
        buttonSTT.setOnClickListener(this);

        textSearch.requestFocus();

        checkPermission();  //녹음 퍼미션 체크

        textSearch.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                switch (i)
                {
                    case EditorInfo.IME_ACTION_SEARCH :

                        String keywords = textView.getText().toString().trim();

                        if(keywords == null || keywords.isEmpty())
                        {
                            Toast.makeText(getContext(), getString(R.string.toast_warning_keywords), Toast.LENGTH_SHORT);
                            return true;
                        }

                        requestInsertSearchHistory(keywords);

                        openSearchResultFragment(keywords);

                        break;
                }

                return true;
            }
        });

        // 검색 기록을 요청한다.
        requsetSearchHistory();

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getContext().getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

        setOnRecognitionListener();

        // Inflate the layout for this fragment
        return view;
    }

    private void requestInsertSearchHistory(String keywords)
    {


        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_search_history_insert);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(keywords, url, returnableCallback);
    }

    private void requsetSearchHistory()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_search_history_select);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_SEARCH_HISTORY);

        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    public void setSearchList(List<SearchHistoryDTO> searchList)
    {
        if(!list.isEmpty())
        {
            list.clear();
        }

        list.addAll(searchList);

        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void openSearchResultFragment(String keywords)
    {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main_layout, new SearchResultFragment(onCallbackResponseListener, keywords),
                FragmentTagUtil.FRAGMENT_TAG_SEARCH_RESULT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fragment_search_image_button_back :

                getActivity().onBackPressed();

                break;
            case R.id.fragment_search_image_button_stt :

                if(isRecording)
                {
                    stopRecord();
                }
                else
                {
                    startRecord();
                }

                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext().getApplicationContext());
                speechRecognizer.setRecognitionListener(listener);
                speechRecognizer.startListening(intent);

                break;
        }
    }

    private void startRecord()
    {
        isRecording = true;

        speechRecognizer=SpeechRecognizer.createSpeechRecognizer(getContext().getApplicationContext());
        speechRecognizer.setRecognitionListener(listener);
        speechRecognizer.startListening(intent);
    }

    //녹음 중지
    void stopRecord()
    {
        isRecording = false;

        speechRecognizer.stopListening();
    }

    private void checkPermission()
    {
        if ( Build.VERSION.SDK_INT >= 23 )
        {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED )
            {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.INTERNET,
                                Manifest.permission.RECORD_AUDIO}, 1);
            }
        }
    }

    private void requestDeleteSearchHistory(String keywords)
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_search_history_delete);

        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_NONE);

        okHttpUtil.sendPostRequest(keywords, url, returnableCallback);
    }

    private void setOnRecognitionListener()
    {
        listener = new RecognitionListener()
        {
            @Override
            public void onReadyForSpeech(Bundle bundle)
            {

            }

            @Override
            public void onBeginningOfSpeech()
            {

            }

            @Override
            public void onRmsChanged(float v)
            {

            }

            @Override
            public void onBufferReceived(byte[] bytes)
            {

            }

            @Override
            public void onEndOfSpeech()
            {

            }

            @Override
            public void onError(int i)
            {
                String message;
                switch (i) {
                    case SpeechRecognizer.ERROR_AUDIO :
                        message = "ERROR_AUDIO";
                        break;
                    case SpeechRecognizer.ERROR_CLIENT :
                        //message = "클라이언트 에러";
                        //speechRecognizer.stopListening()을 호출하면 발생하는 에러
                        return;
                    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS :
                        message = "ERROR_INSUFFICIENT_PERMISSIONS";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK :
                        message = "ERROR_NETWORK";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT :
                        message = "ERROR_NETWORK_TIMEOUT";
                        break;
                    case SpeechRecognizer.ERROR_NO_MATCH :

                        if (isRecording)
                        {
                            startRecord();
                        }

                        return;
                    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY :

                        break;
                    case SpeechRecognizer.ERROR_SERVER :
                        message = "ERROR_SERVER";
                        break;
                    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT :
                        message = "ERROR_SPEECH_TIMEOUT";
                        break;
                    default:
                        message = "알 수 없는 오류";
                        break;
                }

            }

            @Override
            public void onResults(Bundle bundle)
            {
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);	//인식 결과를 담은 ArrayList
                String originText = textSearch.getText().toString();  //기존 text

                //인식 결과
                String newText="";
                for (int i = 0; i < matches.size() ; i++)
                {
                    newText += matches.get(i);
                }

                textSearch.setText(originText + newText + " ");
                speechRecognizer.startListening(intent);
            }

            @Override
            public void onPartialResults(Bundle bundle)
            {

            }

            @Override
            public void onEvent(int i, Bundle bundle)
            {

            }
        };
    }

    @Override
    public void onItemClick(View view, int position)
    {
        SearchHistoryDTO item = list.get(position);

        openSearchResultFragment(item.getKeywords());
    }

    @Override
    public void onItemLongClick(View view, int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("삭제 경고")
                .setMessage("정말로 검색 기록을 취소하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        // 삭제 처리
                        SearchHistoryDTO item = list.get(position);

                        // 삭제 처리 요청
                        requestDeleteSearchHistory(item.getKeywords());

                        // 리스트에서 제거
                        list.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        // 취소 처리
                    }
                });

        builder.show();
    }

    @Override
    public void onCastVoteOption(VoteDTO voteData, boolean isCancel)
    {

    }
}