package com.example.moimusic.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.moimusic.R;
import com.example.moimusic.adapter.HomeMusicAdapter;
import com.example.moimusic.adapter.TestLoopAdapter;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.Recommend;
import com.example.moimusic.mvp.presenters.FragmentAniMusicPresenter;
import com.example.moimusic.mvp.views.FragmentAniMusicView;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerFragmentAniMusicComponent;
import com.example.moimusic.reject.components.DaggerFragmentGroomComponent;
import com.example.moimusic.reject.models.FragmentAniMusicModule;
import com.example.moimusic.reject.models.FragmentGroomModule;
import com.gc.materialdesign.views.ButtonFlat;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by qqq34 on 2016/1/30.
 */
public class FragmentAniMusic extends BaseFragment implements FragmentAniMusicView {
    @Inject
    FragmentAniMusicPresenter presenter;
    private RollPagerView pager;
    private TestLoopAdapter testLoopAdapter;
    private RecyclerView hotList;
    private View header,Title1,Title2;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ButtonFlat buttonhot,buttonlistplace,buttonnew,buttonFlat;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragmentAniMusicComponent.builder()
                .appComponent(appComponent)
                .fragmentAniMusicModule(new FragmentAniMusicModule(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ani_music, container, false);
        initView(v);
        initClick();
        presenter.attach(getContext(), this);
        presenter.onCreate();
        return v;
    }
    private void initClick(){
        buttonhot.setOnClickListener(v -> ShowSnackBar("点击了新"));
        buttonlistplace.setOnClickListener(v -> ShowSnackBar("点击了时间线"));
        buttonnew.setOnClickListener(v -> ShowSnackBar("点击了热门"));
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.getMusicData());
    }
    private void initView(View v) {
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);


        hotList = (RecyclerView) v.findViewById(R.id.Hotmusic);
        header = LayoutInflater.from(getContext()).inflate(R.layout.home_recom_view, null);
        Title1 =  LayoutInflater.from(getContext()).inflate(R.layout.home_title_view, null);
        buttonhot = (ButtonFlat)Title1.findViewById(R.id.text1) ;
        buttonlistplace =(ButtonFlat)Title1.findViewById(R.id.text2) ;
        buttonhot.setText(getContext().getResources().getString(R.string.NewMusic));
        buttonlistplace.setText(getContext().getResources().getString(R.string.TimeLine));
        Title2 =  LayoutInflater.from(getContext()).inflate(R.layout.home_title_view, null);
        buttonnew = (ButtonFlat)Title2.findViewById(R.id.text1) ;
        buttonnew.setText(getContext().getResources().getString(R.string.HotMusic));
        buttonFlat =(ButtonFlat)Title2.findViewById(R.id.text2) ;
        buttonFlat.setVisibility(View.INVISIBLE);
        pager =  (RollPagerView) header.findViewById(R.id.titleImage);
        pager.setAnimationDurtion(500);
        pager.setHintView(new ColorPointHintView(getContext(), getContext().getResources().getColor(R.color.colorPrimaryTR), Color.WHITE));
        testLoopAdapter = new TestLoopAdapter(pager);
        pager.setAdapter(testLoopAdapter);
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                swipeRefreshLayout.setRefreshing(true);
            }
        });

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onDestroy();
        }
    }
    @Override
    public void initPage(List<Recommend> recommends) {
        testLoopAdapter.attach(recommends);
        testLoopAdapter.notifyDataSetChanged();
    }


    @Override
    public void ShowSnackBar(String s) {
        ShowSwipe(false);
        Snackbar.make(hotList, s, Snackbar.LENGTH_SHORT).show();
    }


    private void ShowSwipe(boolean isShow) {
        swipeRefreshLayout.setRefreshing(isShow);
    }

    @Override
    public void onDatafetched(List<Music> musics) {
        HomeMusicAdapter homeMusicAdapter = new HomeMusicAdapter(musics);
        homeMusicAdapter.setHeaderView(header);
        homeMusicAdapter.setTitle1view(Title1);
        homeMusicAdapter.setTitle2View(Title2);
        hotList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        hotList.setAdapter(homeMusicAdapter);
        ShowSwipe(false);
    }
}