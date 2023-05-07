package com.example.baetube.fragment.channel;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.baetube.Callback.ReturnableCallback;
import com.example.baetube.ChannelPagerAdapter;
import com.example.baetube.ChannelTabStrategy;
import com.example.baetube.FragmentTagUtil;
import com.example.baetube.OkHttpUtil;
import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.PreferenceManager;
import com.example.baetube.R;
import com.example.baetube.ViewPagerCallback;
import com.example.baetube.bottomsheetdialog.ChannelReportFragment;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.dto.ViewPagerFragmentData;
import com.example.baetube.fragment.CustomerServiceFragment;
import com.example.baetube.fragment.SearchFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ChannelBaseFragment extends Fragment
{
    private View view;

    private ViewPager2 viewPager;
    private ChannelPagerAdapter channelPagerAdapter;
    private TabLayout tabLayout;
    private TabLayoutMediator tabLayoutMediator;
    private ChannelTabStrategy tabStrategy;

    private OnCallbackResponseListener onCallbackResponseListener;

    private ViewPagerCallback onPageChangeCallback;

    private ChannelDTO channel;
    private ChannelDTO myChannel;

    private OkHttpUtil okHttpUtil;

    public ChannelBaseFragment(Context context, OnCallbackResponseListener onCallbackResponseListener, Integer channelId)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
        onPageChangeCallback = new ViewPagerCallback(context, onCallbackResponseListener, channelId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_channel_base, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        //toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 뷰페이저 요소를 찾는다.
        viewPager = view.findViewById(R.id.fragment_channel_base_viewpager);
        // 뷰페이저의 인디케이터 역할인 탭 레이아웃 요소를 찾는다.
        tabLayout = view.findViewById(R.id.fragment_channel_base_tab_layout);

        // 커스텀 뷰페이저 어댑터 객체를 만든다.
        channelPagerAdapter = new ChannelPagerAdapter(this, 5, onCallbackResponseListener);
        // 어댑터를 뷰페이저에 할당한다.
        viewPager.setAdapter(channelPagerAdapter);
        // 뷰페이저의 방향을 지정.(수평)
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        // 탭 설정 객체 생성
        tabStrategy = new ChannelTabStrategy(getContext().getResources().getStringArray(R.array.tab_layout_channel_title));

        // 탭 레이아웃 중계자 객체 생성
        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, tabStrategy);

        tabLayoutMediator.attach();

        viewPager.registerOnPageChangeCallback(onPageChangeCallback);
        viewPager.setOffscreenPageLimit(5);

        requestMyChannelData();

        // Inflate the layout for this fragment
        return view;
    }

    private void requestMyChannelData()
    {
        if(okHttpUtil == null)
        {
            okHttpUtil = new OkHttpUtil();
        }

        String url = getString(R.string.api_url_channel_data) + PreferenceManager.getChannelSequence(getContext().getApplicationContext());
        ReturnableCallback returnableCallback = new ReturnableCallback(onCallbackResponseListener, ReturnableCallback.CALLBACK_SELECT_CHANNEL_DATA);
        okHttpUtil.sendGetRequest(url, returnableCallback);
    }

    public ViewPagerFragmentData getCurrentFragmentData()
    {
        return channelPagerAdapter.getCurrentFragmentData(viewPager.getCurrentItem());
    }

    @Override
    public void onCreateOptionsMenu( Menu menu,  MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_sub, menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home :

                getActivity().onBackPressed();

                break;
            case R.id.menu_toolbar_search :

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new SearchFragment(onCallbackResponseListener), FragmentTagUtil.FRAGMENT_TAG_SEARCH);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.menu_toolbar_report :

                ChannelReportFragment channelReportFragment = new ChannelReportFragment(getContext());
                channelReportFragment.show(getParentFragmentManager(), channelReportFragment.getTag());

                break;
            case R.id.menu_toolbar_customer_service :

                fragmentManager = getParentFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new CustomerServiceFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            default :


                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setChannel(ChannelDTO channel)
    {
        this.channel = channel;
        channelPagerAdapter.setChannelInfomation(channel);
    }

    public ChannelDTO getChannel()
    {
        return channel;
    }

    public void setMyChannel(ChannelDTO channel)
    {
        this.myChannel = channel;
    }

    public ChannelDTO getMyChannel()
    {
        return myChannel;
    }
}