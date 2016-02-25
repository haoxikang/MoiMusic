package com.example.moimusic.mvp.model.biz;



import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.utils.ErrorList;


import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.Observable;

/**
 * Created by qqq34 on 2016/1/29.
 */
public class UserBiz extends  DataBiz{
    public Observable<Boolean> sendMSG(String s) {
        Observable<Boolean> observable = Observable.create(subscriber -> {
            BmobSMS.requestSMSCode(context, s,"短信验证", new RequestSMSCodeListener() {

                @Override
                public void done(Integer integer, BmobException e) {
                    if (e==null){
                       subscriber.onNext(true);
                    }else {
                        subscriber.onError(new Throwable(new ErrorList().getErrorMsg(e.getErrorCode())));
                    }
                }
            });
        });
        return observable;
    }
    public Observable<MoiUser> Logup(String[] s){
        Observable<MoiUser> observable = Observable.create(subscriber -> {
            MoiUser user = new MoiUser();
            user.setMobilePhoneNumber(s[0]);//设置手机号码（必填）
            user.setPassword(s[2]);                  //设置用户密码
            user.signOrLogin(context, s[1], new SaveListener() {

                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub
                    BmobUser newUser = new BmobUser();
                    newUser.setPassword(s[2]);
                    BmobUser bmobUser = BmobUser.getCurrentUser(context);
                    newUser.update(context,bmobUser.getObjectId(),new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            subscriber.onNext(user);
                        }
                        @Override
                        public void onFailure(int code, String msg) {
                            // TODO Auto-generated method stub
                            subscriber.onError(new Throwable(new ErrorList().getErrorMsg(code)));
                        }
                    });

                }

                @Override
                public void onFailure(int code, String msg) {
                    // TODO Auto-generated method stub
                    subscriber.onError(new Throwable(new ErrorList().getErrorMsg(code)));
                }
            });
        });
        return observable;
    }
    public Observable<MoiUser> LogIn(String[] s){
         Observable<MoiUser> observable = Observable.create(subscriber -> {
             MoiUser.loginByAccount(context, s[0], s[1], new LogInListener<MoiUser>() {
                 @Override
                 public void done(MoiUser moiUser, BmobException e) {
                     if(moiUser!=null){
                         subscriber.onNext(moiUser);
                     }else {
                         subscriber.onError(new Throwable(new ErrorList().getErrorMsg(e.getErrorCode())));
                     }
                 }
             });
        });
        return observable;
    }
    public Observable<MoiUser> getUser(String id){
        Observable<MoiUser> observable = Observable.create(subscriber -> {
            BmobQuery<MoiUser> query = new BmobQuery<MoiUser>();
            query.getObject(context, id, new GetListener<MoiUser>() {
                @Override
                public void onSuccess(MoiUser moiUser) {
                    subscriber.onNext(moiUser);
                }

                @Override
                public void onFailure(int i, String s) {
                    subscriber.onError(new Throwable(new ErrorList().getErrorMsg(i)));
                }
            });
        });
        return observable;
    }


}

