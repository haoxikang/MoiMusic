package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.FollowBiz;
import com.example.moimusic.mvp.model.biz.UserBiz;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.views.IMainView;
import com.example.moimusic.mvp.views.IUserCenterActivity;
import com.example.moimusic.ui.activity.EditActivity;
import com.example.moimusic.ui.activity.LogActivity;

import cn.bmob.v3.BmobUser;
import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/2/19.
 */
public class UserCenterActivityPresenter extends BasePresenterImpl {
    private ApiService api;
    private IUserCenterActivity mView;
    private Factory factory;
    private Context context;
    public UserCenterActivityPresenter(ApiService apiService) {
        factory = new DataBizFactory();
        api =apiService;
    }
    public void attach(IUserCenterActivity iView, Context context){
        mView = iView;
        this.context = context;

    }
    public boolean isCurrentUser(){
      Intent intent= mView.GetIntent();
        String s = intent.getStringExtra("userID");
        if (s.equals("")||s==null){
            mView.ToNextActivity(new Intent(context, LogActivity.class));
            mView.finishActivity();
        }
        Log.d("TAG","id=" +s);
        if (s.equals(BmobUser.getCurrentUser(context,MoiUser.class).getObjectId()) ){
            return true;
        }else
            return false;
    }
    public String getID(){
        Intent intent= mView.GetIntent();
        String s = intent.getStringExtra("userID");
        return s;
    }
    public void setView(){
        Intent intent= mView.GetIntent();
        String s = intent.getStringExtra("userID");
        FollowBiz followBiz = factory.createBiz(FollowBiz.class);
        UserBiz userBiz = factory.createBiz(UserBiz.class);
        mSubscriptions.add(followBiz.getFollowData(s).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(ints -> {
            if (s.equals(BmobUser.getCurrentUser(context,MoiUser.class).getObjectId())){
                mView.updataView( BmobUser.getCurrentUser(context,MoiUser.class),ints[0],ints[1]);
            }else {
                mSubscriptions.add(userBiz.getUser(s).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moiUser -> {
                    mView.updataView(moiUser,ints[0],ints[1]);
                },throwable -> {
                    mView.ShowSnackBar(throwable.getMessage());
                }));
            }
        },throwable1 -> {
            mView.ShowSnackBar(throwable1.getMessage());
        }));


    }
    public void startEditActivity(){
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra("isCurrent",isCurrentUser());
        mView.ToNextActivity(intent);
    }
}
