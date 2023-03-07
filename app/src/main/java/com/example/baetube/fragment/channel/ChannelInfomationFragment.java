package com.example.baetube.fragment.channel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.baetube.OnCallbackResponseListener;
import com.example.baetube.R;
import com.example.baetube.bottomsheetdialog.ChannelReportFragment;
import com.example.baetube.dto.ChannelDTO;
import com.example.baetube.fragment.CustomerServiceFragment;
import com.example.baetube.fragment.SearchFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChannelInfomationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChannelInfomationFragment extends Fragment
{
    private View view;
    private TextView channelDescription;
    private TextView regDate;
    private OnCallbackResponseListener onCallbackResponseListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "toolbar";

    // TODO: Rename and change types of parameters
    private boolean hasToolbar;

    public ChannelInfomationFragment()
    {
        // Required empty public constructor
    }

    public ChannelInfomationFragment(OnCallbackResponseListener onCallbackResponseListener)
    {
        this.onCallbackResponseListener = onCallbackResponseListener;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param hasToolbar 툴바 사용여부.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChannelInfomationFragment newInstance(boolean hasToolbar, OnCallbackResponseListener onCallbackResponseListener)
    {
        ChannelInfomationFragment fragment = new ChannelInfomationFragment(onCallbackResponseListener);
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM, hasToolbar);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            hasToolbar = getArguments().getBoolean(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_channel_information, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바가 있어야 할 때
        if(hasToolbar)
        {
            // 툴바 메뉴 옵션
            setHasOptionsMenu(true);

            // 액티비티를 구하고 툴바를 적용시킨다.
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            //toolbar.setTitle("");
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        else // 툴바가 없어야 할 때
        {
            toolbar.setVisibility(View.GONE);
        }

        channelDescription = view.findViewById(R.id.fragment_channel_information_text_description);
        regDate = view.findViewById(R.id.fragment_channel_information_text_reg_date);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_sub, menu);
    }

    // 채널 정보를 설정하는 메소드, visitChannel을 요청하고 데이터가 돌아오면 실행된다.
    public void setChannelInfomation(ChannelDTO channel)
    {
        channelDescription.setText(channel.getDescription());
        regDate.setText(channel.getRegDate().toString());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home :

                getActivity().onBackPressed();

                break;
            case R.id.menu_toolbar_search :

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_layout, new SearchFragment());
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
}