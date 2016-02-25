package com.example.moimusic.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moimusic.R;
import com.example.moimusic.adapter.MusicListContentViewAdapter;
import com.example.moimusic.mvp.presenters.MusicListContentFragmentPresenter;
import com.example.moimusic.mvp.views.IMusicListContentFragmentView;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerMusicListContentFragmentComponent;
import com.example.moimusic.reject.models.MusicListContentFragmentModule;

import javax.inject.Inject;


public class MusicListContentFragment extends BaseFragment implements IMusicListContentFragmentView {
    public static final String EXTRA_LIST_ID ="com.example.moimusic.listid";
@Inject
    MusicListContentFragmentPresenter presenter;
    private RecyclerView recyclerView ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getArguments().getString(EXTRA_LIST_ID);
        presenter.attach(id,this);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerMusicListContentFragmentComponent.builder()
                .appComponent(appComponent)
                .musicListContentFragmentModule(new MusicListContentFragmentModule(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_music_list_content, container, false);
        initView(v);
        presenter.onCreate();
        return v;
    }
private void initView(View v){
    recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
}
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onDestroy();
        }
    }

    @Override
    public void ShowList(MusicListContentViewAdapter musicListContentViewAdapter) {
        recyclerView.setAdapter(musicListContentViewAdapter);
    }

    @Override
    public void ShowSnackBar(String s) {
        Snackbar.make(recyclerView,s,Snackbar.LENGTH_SHORT).show();
    }
    public static MusicListContentFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString(EXTRA_LIST_ID, id);
        MusicListContentFragment fragment = new MusicListContentFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
