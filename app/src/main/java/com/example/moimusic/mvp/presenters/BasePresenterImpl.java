package com.example.moimusic.mvp.presenters;


import android.util.Log;

import com.example.moimusic.utils.RxUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @Project CommonProject
 * @Packate com.micky.commonproj.presenter
 * @Description
 * @Author Micky Liu
 * @Email mickyliu@126.com
 * @Date 2015-12-22 14:34
 * @Version 0.1
 */
public class BasePresenterImpl implements BasePresenter {
    protected CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Override
    public void onCreate() {
        mSubscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(mSubscriptions);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        mSubscriptions.unsubscribe();
        Log.d("TAG","反注册成功");
    }

    protected  <T> Observable.Transformer<T, T> applyScheduler() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
