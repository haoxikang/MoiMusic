package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.util.Log;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.entity.EvenCall;
import com.example.moimusic.mvp.model.entity.EvenMusicListContentAdapterCall;
import com.example.moimusic.mvp.model.entity.EvenReCall;
import com.example.moimusic.mvp.views.IActivityPlayNowView;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.utils.Utils;

import de.greenrobot.event.EventBus;

/**
 * Created by qqq34 on 2016/2/29.
 */
public class ActivityPlayNowPresenter extends BasePresenterImpl {
    private IActivityPlayNowView view;
private Context context;
    private int nowTime;
    private int allTime;
    private PlayListSingleton playListSingleton;
    public ActivityPlayNowPresenter(ApiService apiService) {
        playListSingleton = PlayListSingleton.INSTANCE;
        if (playListSingleton.getAll()!=0){
            Log.d("TAG","时间");
            nowTime = playListSingleton.getNow();
            allTime = playListSingleton.getAll();
        }
    }

    public void attach(IActivityPlayNowView view,Context context) {
        this.view = view;
        this.context = context;
        EventBus.getDefault().register(this);
    }
    public void Play(){
        if (playListSingleton.isUnderPlay){
            EvenCall evenCall = new EvenCall();
            evenCall.setCurrentOrder(EvenCall.PAUSE);
            EventBus.getDefault().post(evenCall);
            view.setPlayButton(false);
        }else {

            EvenCall evenCall = new EvenCall();
            evenCall.setCurrentOrder(EvenCall.START);
            EventBus.getDefault().post(evenCall);
            view.setPlayButton(true);
        }
    }
    public void Next(){
        EvenCall evenCall = new EvenCall();
        evenCall.setCurrentOrder(EvenCall.NEXT);
        EventBus.getDefault().post(evenCall);
        EventBus.getDefault().post(new EvenMusicListContentAdapterCall());
        view.updataPlayView();
        view.setPlayButton(true);
        view.setProgressbar(0,100);
    }
    public void Pre(){
        EvenCall evenCall = new EvenCall();
        evenCall.setCurrentOrder(EvenCall.PRE);
        EventBus.getDefault().post(evenCall);
        view.updataPlayView();
        view.setPlayButton(true);
        view.setProgressbar(0,100);
    }
    public void seekTo(int time){
        EvenCall evenCall = new EvenCall();
        evenCall.setCurrentOrder(EvenCall.SEEKTO);
        evenCall.setCurrent(time);
        EventBus.getDefault().post(evenCall);
    }
    public void onEventMainThread(EvenReCall evenReCall){
        if (evenReCall.getMusicSize()!=0){
            playListSingleton.setAll(evenReCall.getMusicSize());
            playListSingleton.setNow(evenReCall.getCurrent());
        }
        view.updataPlayView();
        if (evenReCall.getCurrent()!=0&&evenReCall.getMusicSize()!=0){
            view.setProgressbar(evenReCall.getCurrent(),evenReCall.getMusicSize());
        }

    }
    public String getnowTime(int now){
        return Utils.getTime(now*allTime/100);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
