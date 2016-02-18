package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.moimusic.AppApplication;
import com.example.moimusic.R;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.UserBiz;
import com.example.moimusic.mvp.views.FragmentSignUpView;
import com.example.moimusic.mvp.views.IMainView;
import com.example.moimusic.utils.Utils;

import java.util.concurrent.TimeUnit;

import cn.bmob.v3.exception.BmobException;
import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/2/17.
 */
public class FragmentSignUpPresenter extends BasePresenterImpl {
    private FragmentSignUpView fragmentSignUpView;
    private Factory factory;

    public FragmentSignUpPresenter(ApiService apiService) {
        factory = new DataBizFactory();
    }

    public void attach(FragmentSignUpView fragmentSignUpView) {
        this.fragmentSignUpView = fragmentSignUpView;

    }
    public void Logup(){
        String[] text = fragmentSignUpView.getUserText();
        if (TextUtils.isEmpty(text[0])||TextUtils.isEmpty(text[1])||TextUtils.isEmpty(text[2])){
            fragmentSignUpView.showSnakBar(AppApplication.context.getResources().getString(R.string.please_input_right_message));
        }else if (text[0].length()!=11||text[1].length()!=6||text[2].length()<6){
            fragmentSignUpView.showSnakBar(AppApplication.context.getResources().getString(R.string.please_input_right_message));
        }else {
            fragmentSignUpView.showProgressWithAllViewUnEnable(true);
            UserBiz userBiz = factory.createBiz(UserBiz.class);
            mSubscriptions.add(userBiz.Logup(text).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(moiUser -> {
                EventBus.getDefault().post(AppApplication.context.getResources().getString(R.string.log_up_succ));
                fragmentSignUpView.showProgressWithAllViewUnEnable(false);
                fragmentSignUpView.finishActivity();
            },throwable -> {
                fragmentSignUpView.showSnakBar(throwable.getMessage());
                fragmentSignUpView.showProgressWithAllViewUnEnable(false);
            }));
        }
    }
    public void onphoneNumPrint(String s) {
        fragmentSignUpView.phoneNumberShowError(!Utils.isMobileNO(s));
    }

    public void onPasswordPrint(String s) {
        fragmentSignUpView.passwordShowError(!Utils.isPassword(s));
    }

    public void getYZM(String s) {
        if (Utils.isMobileNO(s) && !TextUtils.isEmpty(s)) {
            fragmentSignUpView.phoneNumberEnable(false);
            fragmentSignUpView.setButonFlatEnable(false);
            UserBiz userBiz = factory.createBiz(UserBiz.class);
            mSubscriptions.add(countDowm().subscribeOn(Schedulers.io()
            ).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s1 -> fragmentSignUpView.setButtonFlatText(s1)
                            , throwable -> {

                            }, () -> {
                                fragmentSignUpView.setButtonFlatText(AppApplication.context.getResources().getString(R.string.re_send));
                                fragmentSignUpView.setButonFlatEnable(true);
                                fragmentSignUpView.phoneNumberEnable(true);
                            }));
            mSubscriptions.add(userBiz.sendMSG(s).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aBoolean -> {
                        if (aBoolean) {
                            fragmentSignUpView.showSnakBar(AppApplication.context.getResources().getString(R.string.send_msg_succ));
                        }
                    }, throwable -> {
                        fragmentSignUpView.showSnakBar(throwable.getMessage());
                    }));
        } else {
            fragmentSignUpView.phoneNumberShowError(true);
        }
    }



    private Observable<String> countDowm() {
        Observable<String> observable = Observable.create(subscriber -> {
            for (int i = 60; i > 0; i--) {
                subscriber.onNext(i + AppApplication.context.getResources().getString(R.string.re_send_s));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            subscriber.onCompleted();
        });
        return observable;
    }
}
