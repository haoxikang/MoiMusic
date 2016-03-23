package com.example.moimusic.mvp.presenters;

import android.content.Context;

import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.MusicBiz;
import com.example.moimusic.mvp.model.biz.RecommendBiz;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.Recommend;
import com.example.moimusic.mvp.views.FragmentAniMusicView;
import com.example.moimusic.mvp.views.FragmentGroomView;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/23.
 */
public class FragmentAniMusicPresenter extends BasePresenterImpl {
    private Context context;
    private FragmentAniMusicView mView;
    private Factory factory;

    public FragmentAniMusicPresenter(ApiService apiService) {
    }

    public void attach(Context context, FragmentAniMusicView mView) {
        this.context = context;
        this.mView = mView;
        factory = new DataBizFactory();

    }
    @Override
    public void onCreate() {
        super.onCreate();
        RecommendBiz biz = factory.createBiz(RecommendBiz.class);
        mSubscriptions.add(biz.getRecommend().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recommends -> {
                    List<Recommend> list = new ArrayList<>();
                    for (Recommend recommend:recommends){
                        if (recommend.getWhere().equals("2")){
                            list.add(recommend);
                        }
                    }
                    mView.initPage(list);
                    getMusicData();
                },throwable -> {
                    mView.ShowSnackBar(throwable.getMessage());

                }));
    }
    public void getMusicData(){
        MusicBiz musicBiz = factory.createBiz(MusicBiz.class);
        mSubscriptions.add(musicBiz.getHomePageMusic(2,1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(musics -> {
                    mSubscriptions.add(musicBiz.getHomePageMusic(1,1).subscribeOn(Schedulers.io())
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
