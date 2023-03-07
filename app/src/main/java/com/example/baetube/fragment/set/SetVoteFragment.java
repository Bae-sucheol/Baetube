package com.example.baetube.fragment.set;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baetube.OnDialogInteractionListener;
import com.example.baetube.OnRecyclerViewClickListener;
import com.example.baetube.OnSetFragmentListener;
import com.example.baetube.R;
import com.example.baetube.bottomsheetdialog.AddVoteFragment;
import com.example.baetube.dto.VoteDTO;
import com.example.baetube.recyclerview.adapter.ReclyclerViewVoteAdapter;

import java.util.ArrayList;
import java.util.List;

public class SetVoteFragment extends Fragment implements OnRecyclerViewClickListener, View.OnClickListener
{
    private View view;

    // 리사이클러뷰
    private RecyclerView recyclerView;
    // 리사이클러뷰 어뎁터
    private ReclyclerViewVoteAdapter adapter;
    // 리사이클러뷰 리스트.
    private ArrayList<String> list = new ArrayList<>();
    // 현재 추가된 투표 개수
    private TextView countCur;
    // 투표를 추가하기 위한 버튼
    private TextView buttonAddVote;
    // 투표의 제목을 작성하기 위한 에딧텍스트
    private EditText title;
    // 투표의 기본적인 정보를 담기 위한 객체
    private VoteDTO vote;
    // 투표 옵션을 담기 위한 객체
    private List<VoteDTO> voteOptions;

    private OnSetFragmentListener onSetFragmentListener;

    private OnDialogInteractionListener onDialogInteractionListener;

    public SetVoteFragment(OnSetFragmentListener onSetFragmentListener)
    {
        this.onSetFragmentListener = onSetFragmentListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_set_vote, container, false);

        // 툴바(액션바)를 설정
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // 툴바 메뉴 옵션
        setHasOptionsMenu(true);

        // 액티비티를 구하고 툴바를 적용시킨다.
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar.setTitle(getString(R.string.fragment_set_vote_toolbar_title));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
         * 1. 리사이클러뷰 요소 찾기
         * 2. 리사이클러뷰 어댑터 객체 생성
         * 3. 리사이클러뷰 어댑터 설정
         * 4. 리사이클러뷰 레이아웃 매니저 설정
         */
        recyclerView = view.findViewById(R.id.fragment_set_vote_recyclerview);
        adapter = new ReclyclerViewVoteAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnRecyclerViewClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        countCur = view.findViewById(R.id.fragment_set_vote_text_count_cur);
        buttonAddVote = view.findViewById(R.id.fragment_set_vote_text_button_add_vote);
        title = view.findViewById(R.id.fragment_set_vote_edit_title);
        title.setText("");

        buttonAddVote.setOnClickListener(this);

        setOnDialogInteractionListener();

        vote = new VoteDTO();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar_none, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
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

                String titleName = title.getText().toString();

                if(titleName != null && !titleName.isEmpty())
                {
                    vote.setTitle(titleName);
                }

                voteOptions = new ArrayList<>();

                for (String option : list)
                {
                    VoteDTO vote = new VoteDTO();
                    vote.setOption(option);
                    voteOptions.add(vote);
                }

                onSetFragmentListener.onResponseVote(vote, voteOptions);

            default :
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position)
    {
    }

    @Override
    public void onItemLongClick(View view, int position)
    {
        list.remove(position);
        adapter.notifyItemRemoved(position);
        countCur.setText(String.valueOf(list.size()));
    }

    @Override
    public void onCastVoteOption(VoteDTO voteData, boolean isCancel)
    {

    }

    @Override
    public void onClick(View view)
    {
        // 클릭할 버튼이 딱 하나 뿐이므로 딱히 조건문으로 나눌 필요가 없다.
        AddVoteFragment addVoteFragment = new AddVoteFragment(onDialogInteractionListener);
        addVoteFragment.show(getParentFragmentManager(), addVoteFragment.getTag());
    }

    private void setOnDialogInteractionListener()
    {
        onDialogInteractionListener = new OnDialogInteractionListener()
        {
            @Override
            public void onAddVoteResponse(String voteItem)
            {
                list.add(voteItem);
                adapter.notifyItemInserted(list.size() - 1);
                countCur.setText(String.valueOf(list.size()));
            }

            @Override
            public void onDeletePlaylistItem(int position)
            {

            }
        };
    }
}