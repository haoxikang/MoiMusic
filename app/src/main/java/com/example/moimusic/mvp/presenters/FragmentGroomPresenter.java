package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.moimusic.adapter.HomeMusicAdapter;
import com.example.moimusic.adapter.TestLoopAdapter;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.MusicBiz;
import com.example.moimusic.mvp.model.biz.RecommendBiz;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.mvp.model.entity.Recommend;
import com.example.moimusic.mvp.views.FragmentGroomView;


import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/3/17.
 */
public class FragmentGroomPresenter extends BasePresenterImpl {
    private Context context;
    private FragmentGroomView mView;
    private Factory factory;

    public FragmentGroomPresenter(ApiService apiService) {

    }

    public void attach(Context context, FragmentGroomView mView) {
        this.context = context;
        this.mView = mView;
        factory = new DataBizFactory();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        RecommendBiz biz = factory.createBiz(RecommendBiz.class);
        mSubscriptions.add(biz.getRecommend("1").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recommends -> {
                    mView.initPage(recommends);
                    getMusicData();
                }));
    }
    public void getMusicData(){
        MusicBiz musicBiz = factory.createBiz(MusicBiz.class);
        mSubscriptions.add(musicBiz.getHomePageMusic(1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(musics -> {
            mSubscriptions.add(musicBiz.getHomePageMusic(2).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(musics1 -> {
                        List<Music> musics2 = new ArrayList<>();
                        musics2.addAll(musics);
                        musics2.addAll(musics1);
                        mView.onDatafetched(musics2);
                    },throwable -> {
                        mView.ShowSnackBar(throwable.getMessage());
                    }));
        },throwable -> {
            mView.ShowSnackBar(throwable.getMessage());
        }));

    }
}
