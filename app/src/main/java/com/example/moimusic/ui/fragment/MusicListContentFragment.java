package com.example.moimusic.ui.fragment;


import android.content.Intent;
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
import com.example.moimusic.mvp.model.biz.IncreaseBiz;
import com.example.moimusic.mvp.model.entity.EvenCall;
import com.example.moimusic.mvp.model.entity.EvenMusicListChangeCall;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.presenters.MusicListContentFragmentPresenter;
import com.example.moimusic.mvp.views.IMusicListContentFragmentView;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerMusicListContentFragmentComponent;
import com.example.moimusic.reject.models.MusicListContentFragmentModule;
import com.example.moimusic.ui.activity.ActivityPlayNow;

import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;


public class MusicListContentFragment extends BaseFragment implements IMusicListContentFragmentView {
    private List<Music> musicList;
    private String s;
@Inject
    MusicListContentFragmentPresenter presenter;
    private RecyclerView recyclerView ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.attach(this);
        s=getArguments().getString("Activity");
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
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
        if (musicList!=null){
            ShowList(new MusicListContentViewAdapter(musicList,getActivity()));
        }

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
        musicListContentViewAdapter.setOnItemClickLitener(new MusicListContentViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                PlayListSingleton.INSTANCE.setMusicList(musicList);
                PlayListSingleton.INSTANCE.setCurrentPosition(position);
                EvenCall evenCall = new EvenCall();
                evenCall.setCurrentOrder(EvenCall.PLAY);
                EventBus.getDefault().post(evenCall);
                musicListContentViewAdapter.notifyDataSetChanged();
                if (s.equals("ActivityMusicList")) {
                    startActivity(new Intent(getActivity(), ActivityPlayNow.class));
                }
                if (s.equals("ActivityPlayNow")){
                    EventBus.getDefault().post(new EvenMusicListChangeCall(musicList.get(position).getObjectId()));
                }

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    public void ShowSnackBar(String s) {
        Snackbar.make(recyclerView,s,Snackbar.LENGTH_SHORT).show();
    }
    public static MusicListContentFragment newInstance(List<Music> musicList,String s) {
        Bundle args = new Bundle();
        args.putString("Activity", s);
        MusicListContentFragment fragment = new MusicListContentFragment();
        fragment.setMusicList(musicList);
        fragment.setArguments(args);
        return fragment;
    }
}
