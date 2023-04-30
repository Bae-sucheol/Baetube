package com.example.baetube.fragment.set;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.example.baetube.OnSetFragmentListener;
import com.example.baetube.R;

public class SetLocationFragment extends Fragment
{
    private View view;
    private WebView webView;

    private OnSetFragmentListener onSetFragmentListener;

    public SetLocationFragment(OnSetFragmentListener onSetFragmentListener)
    {
        this.onSetFragmentListener = onSetFragmentListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_set_location, container, false);

        webView = view.findViewById(R.id.fragment_set_location_webView);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new BridgeInterface(), "Android");
        webView.getSettings().setDomStorageEnabled(true);

        //ssl 인증이 없는 경우 해결을 위한 부분
        webView.setWebChromeClient(new WebChromeClient() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                request.grant(request.getResources());
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                // js 함수 호출
                webView.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });

        // html 파일 호출
        webView.loadUrl(getString(R.string.api_url_address));

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item)
    {
        switch (item.getItemId())
        {
            /*
             * 뒤로가기 버튼을 눌렀을 때
             * 액티비티의 onBackPressed() 메소드를 실행.
             * onBackPressed() 메소드에서는 fragmentManager를 통해
             * popBackStack() 메소드를 사용하여 뒤로가기 기능을 구현.
             */
            case android.R.id.home :

                getActivity().onBackPressed();

            default :
                return super.onOptionsItemSelected(item);
        }
    }

    private class BridgeInterface
    {
        @JavascriptInterface
        public void processDATA(String data)
        {
            // 주소 검색 결과 값이 인터페이스를 통해 전달된다.
            // callback 을통해 activity로 데이터를 넘겨줘야 한다.
            getParentFragmentManager().popBackStack();

            onSetFragmentListener.onResponseLocation(data);
        }
    }
}