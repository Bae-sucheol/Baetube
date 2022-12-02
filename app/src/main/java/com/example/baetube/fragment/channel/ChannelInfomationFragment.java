package com.example.baetube.fragment.channel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.baetube.CustomerServiceFragment;
import com.example.baetube.R;
import com.example.baetube.bottomsheetdialog.ChannelReportFragment;
import com.example.baetube.fragment.SearchFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChannelInfomationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChannelInfomationFragment extends Fragment
{
    private View view;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "toolbar";

    // TODO: Rename and change types of parameters
    private boolean hasToolbar;

    public ChannelInfomationFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param hasToolbar 툴바 사용여부.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChannelInfomationFragment newInstance(boolean hasToolbar)
    {
        ChannelInfomationFragment fragment = new ChannelInfomationFragment();
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

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_sub, menu);
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