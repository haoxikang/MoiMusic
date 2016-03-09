package com.example.moimusic.mvp.model.biz;

import android.content.Context;
import android.util.Log;


import com.example.moimusic.R;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.utils.ErrorList;

import java.io.File;
import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import rx.Observable;

/**
 * Created by qqq34 on 2016/1/29.
 */
public class MusicListBiz extends DataBiz {

    public Observable<List<MusicList>> getMyMusic(int page, boolean isMe,String id) {
        MoiUser moiUser = new MoiUser();
        moiUser.setObjectId(id);
        Observable<List<MusicList>> observable = Observable.create(subscriber -> {
            BmobQuery<MusicList> query = new BmobQuery<>();
            query.addWhereRelatedTo("myMusicList", new BmobPointer(moiUser));
            if (!isMe) {
                query.addWhereEqualTo("isRelease", true);
            }
            query.order("-createdAt");
            query.setLimit(8);
            query.setSkip((page - 1) * 8);
            query.findObjects(context, new FindListener<MusicList>() {
                @Override
                public void onSuccess(List<MusicList> list) {
                    Log.d("TAG", "成功");
                    if (list.size() == 0) {
                        subscriber.onError(new Throwable(context.getResources().getString(R.string.no_data)));
                    }
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

    public Observable<List<MusicList>> getCollegeMusic(int page,boolean isAnimal) {
        MoiUser moiUser = BmobUser.getCurrentUser(context, MoiUser.class);
        Observable<List<MusicList>> observable = Observable.create(subscriber -> {
            BmobQuery<MusicList> query = new BmobQuery<>();
            query.addWhereRelatedTo("College", new BmobPointer(moiUser));
            query.order("-createdAt");
            if (isAnimal){
                query.addWhereEqualTo("isAnimal", true);
            }else {
                query.addWhereEqualTo("isAnimal", false);
            }
            query.setLimit(8);
            query.setSkip((page - 1) * 8);
            query.findObjects(context, new FindListener<MusicList>() {
                @Override
                public void onSuccess(List<MusicList> list) {
                    Log.d("TAG", "成功");
                    if (list.size() == 0) {
                        subscriber.onError(new Throwable(context.getResources().getString(R.string.no_data)));
                    }
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

    public Observable<MusicList> getMusicList(String id) {
        Observable<MusicList> observable = Observable.create(subscriber -> {
            BmobQuery<MusicList> query = new BmobQuery<>();
            query.include("createUser");
            query.getObject(context, id, new GetListener<MusicList>() {
                @Override
                public void onSuccess(MusicList musicList) {
                    if (musicList!=null){
                        subscriber.onNext(musicList);
                    }else {
                        subscriber.onError(new Throwable("位置错误"));
                    }

                }

                @Override
                public void onFailure(int i, String s) {
                    subscriber.onError(new Throwable(new ErrorList().getErrorMsg(i)));
                }
            });
        });
        return observable;
    }
    public Observable<MusicList> updataMusicList(String id,String path,String name){
        Observable<MusicList> observable = Observable.create(subscriber -> {
            BmobFile bmobFile = new BmobFile(new File(path));
            bmobFile.uploadblock(context, new UploadFileListener() {

                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub
                    MusicList musicList = new MusicList();
                     musicList.setListImageUri(bmobFile.getFileUrl(context));
                    musicList.setName(name);
                    musicList.update(context, id, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            subscriber.onNext(musicList);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            subscriber.onError(new Throwable(new ErrorList().getErrorMsg(i)));
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
    public Observable<MusicList> ReleaceList(String id){
        Observable<MusicList> observable = Observable.create(subscriber -> {
            MusicList musicList = new MusicList();
            musicList.setRelease(true);
            musicList.update(context, id, new UpdateListener() {
                @Override
                public void onSuccess() {
                    subscriber.onNext(musicList);
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
