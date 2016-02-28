package com.example.moimusic.mvp.presenters;

import android.support.v4.app.FragmentActivity;

import com.example.moimusic.adapter.MusicListContentViewAdapter;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.MusicBiz;
import com.example.moimusic.mvp.model.entity.EvenActivityMusicListCall;
import com.example.moimusic.mvp.model.entity.EvenCall;
import com.example.moimusic.mvp.views.IMusicListContentFragmentView;

import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/2/24.
 */
public class MusicListContentFragmentPresenter extends BasePresenterImpl {
    private Factory factory;
    private String id;
    private IMusicListContentFragmentView view;
    private FragmentActivity fragmentActivity;
    public MusicListContentFragmentPresenter(ApiService apiService) {
        factory = new DataBizFactory();
    }

    public void attach(String id,IMusicListContentFragmentView view,FragmentActivity fragmentActivity) {
        this.id = id;
        this.view =view;
        this.fragmentActivity =fragmentActivity;
    }

    @Override
    public void onCreate() {
        MusicBiz musicBiz = factory.createBiz(MusicBiz.class);
        super.onCreate();
        mSubscriptions.add(musicBiz.getMusic(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(musicList -> {
                    MusicListContentViewAdapter musicListContentViewAdapter = new MusicListContentViewAdapter(musicList,fragmentActivity);
                    view.ShowList(musicListContentViewAdapter);
                    EvenActivityMusicListCall evenActivityMusicListCall = new EvenActivityMusicListCall(true,musicList);
                    EventBus.getDefault().post(evenActivityMusicListCall);
                },throwable -> {
                    view.ShowSnackBar(throwable.getMessage());
                }));
    }
}
