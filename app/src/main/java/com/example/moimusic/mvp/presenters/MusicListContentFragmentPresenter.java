package com.example.moimusic.mvp.presenters;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.example.moimusic.adapter.MusicListContentViewAdapter;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.MusicBiz;
import com.example.moimusic.mvp.model.entity.EvenActivityMusicListCall;
import com.example.moimusic.mvp.model.entity.EvenCall;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.views.IMusicListContentFragmentView;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.ui.activity.ActivityPlayNow;

import java.util.List;

import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/2/24.
 */
public class MusicListContentFragmentPresenter extends BasePresenterImpl {
    private Factory factory;
    private IMusicListContentFragmentView view;
    public MusicListContentFragmentPresenter(ApiService apiService) {
        factory = new DataBizFactory();
    }

    public void attach(IMusicListContentFragmentView view) {
        this.view =view;
    }

}
