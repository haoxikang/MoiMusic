package com.example.moimusic.mvp.model.biz;


import android.util.Log;


import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.utils.ErrorList;


import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;

import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import rx.Observable;

/**
 * Created by qqq34 on 2016/1/29.
 */
public class UserBiz extends DataBiz {
    public Observable<Boolean> sendMSG(String s) {
        Observable<Boolean> observable = Observable.create(subscriber -> {
            BmobSMS.requestSMSCode(context, s, "短信验证", new RequestSMSCodeListener() {

                @Override
                public void done(Integer integer, BmobException e) {
                    if (e == null) {
                        subscriber.onNext(true);
                    } else {
                        subscriber.onError(new Throwable(new ErrorList().getErrorMsg(e.getErrorCode())));
                    }
                }
            });
        });
        return observable;
    }

    public Observable<MoiUser> Logup(String[] s) {
        Observable<MoiUser> observable = Observable.create(subscriber -> {
            MoiUser user = new MoiUser();
            user.setMobilePhoneNumber(s[0]);//设置手机号码（必填）
            user.setSex("男");
            user.setPassword(s[2]);                  //设置用户密码
            user.signOrLogin(context, s[1], new SaveListener() {

                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub
                    BmobUser newUser = new BmobUser();
                    newUser.setPassword(s[2]);
                    BmobUser bmobUser = BmobUser.getCurrentUser(context);
                    newUser.update(context, bmobUser.getObjectId(), new UpdateListener() {
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

    public Observable<MoiUser> LogIn(String[] s) {
        Observable<MoiUser> observable = Observable.create(subscriber -> {
            MoiUser.loginByAccount(context, s[0], s[1], new LogInListener<MoiUser>() {
                @Override
                public void done(MoiUser moiUser, BmobException e) {
                    if (moiUser != null) {
                        subscriber.onNext(moiUser);
                    } else {
                        subscriber.onError(new Throwable(new ErrorList().getErrorMsg(e.getErrorCode())));
                    }
                }
            });
        });
        return observable;
    }

    public Observable<MoiUser> getUser(String id) {
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

    public Observable<MoiUser> updata(String[] data) {
        Observable<MoiUser> observable = Observable.create(subscriber -> {
            if (data[3] != null) {
                Log.d("TAG", "照片地址" + data[3]);
                BmobFile bmobFile = new BmobFile(new File(data[3]));
                bmobFile.uploadblock(context, new UploadFileListener() {
                    @Override
                    public void onSuccess() {

                        BmobFile file = new BmobFile();
                        if (BmobUser.getCurrentUser(context, MoiUser.class).getImageFile() != null) {
                            file.setUrl(BmobUser.getCurrentUser(context, MoiUser.class).getImageFile().getUrl());
                        }

                        file.delete(context, new DeleteListener() {

                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub
                                Log.d("TAG", "删除成功");
                            }

                            @Override
                            public void onFailure(int code, String msg) {
                                // TODO Auto-generated method stub
                                Log.d("TAG", "删除失败" + code + msg);
                            }
                        });
                        MoiUser moiUser = new MoiUser();
                        moiUser.setImageFile(bmobFile);
                        if (data[0] != null) {
                            moiUser.setName(data[0]);
                        }
                        if (data[1] != null) {
                            moiUser.setIntroduction(data[1]);
                        }
                        if (data[2] != null) {
                            if (data[2].equals(true)) {
                                moiUser.setSex("男");
                            } else {
                                moiUser.setSex("女");
                            }
                        }
                        MoiUser moiUser1 = MoiUser.getCurrentUser(context, MoiUser.class);
                        moiUser.update(context, moiUser1.getObjectId(), new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub
                                subscriber.onNext(moiUser);
                            }

                            @Override
                            public void onFailure(int code, String msg) {

                                subscriber.onError(new Throwable(new ErrorList().getErrorMsg(code)));
                                // TODO Auto-generated method stub
                            }
                        });
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        subscriber.onError(new Throwable(new ErrorList().getErrorMsg(i)));
                    }
                });

            } else {
                MoiUser moiUser = new MoiUser();
                if (data[0] != null) {
                    moiUser.setName(data[0]);
                }
                if (data[1] != null) {
                    moiUser.setIntroduction(data[1]);
                }
                if (data[2] != null) {
                    if (data[2].equals(true)) {
                        moiUser.setSex("男");
                    } else {
                        moiUser.setSex("女");
                    }
                }
                MoiUser moiUser1 = MoiUser.getCurrentUser(context, MoiUser.class);
                moiUser.update(context, moiUser1.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        subscriber.onNext(moiUser);
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        subscriber.onError(new Throwable(new ErrorList().getErrorMsg(code)));
                        // TODO Auto-generated method stub
                    }
                });
            }


        });
        return observable;
    }

    public Observable<MoiUser> CollegeList(String id) {
        Observable<MoiUser> observable = Observable.create(subscriber -> {
            MoiUser moiUser = BmobUser.getCurrentUser(context, MoiUser.class);
            MusicList musicList = new MusicList();
            musicList.setObjectId(id);
            BmobRelation relation = new BmobRelation();
            relation.add(musicList);
            moiUser.setCollege(relation);
            moiUser.update(context, new UpdateListener() {
                @Override
                public void onSuccess() {
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
    public Observable<List<MoiUser>> SearchUserFromTellPhone(String tellPhone){
        Observable<List<MoiUser>> observable = Observable.create(subscriber -> {
            BmobQuery<MoiUser> query = new BmobQuery<>();
            query.addWhereEqualTo("mobilePhoneNumber",tellPhone);
            query.findObjects(context, new FindListener<MoiUser>() {
                @Override
                public void onSuccess(List<MoiUser> list) {
                    subscriber.onNext(list);
                }

                @Override
                public void onError(int i, String s) {
                    subscriber.onError(new Throwable(new ErrorList().getErrorMsg(i)));
                }
            });
        });
        return observable;
    }
}

