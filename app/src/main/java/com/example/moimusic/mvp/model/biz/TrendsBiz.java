package com.example.moimusic.mvp.model.biz;

import android.util.Log;

import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.mvp.model.entity.Recommend;
import com.example.moimusic.mvp.model.entity.Trends;
import com.example.moimusic.utils.ErrorList;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import rx.Observable;

/**
 * Created by Administrator on 2016/3/25.
 */
public class TrendsBiz extends DataBiz {
    public Observable<List<Trends>> getTrends() {
        Observable<List<Trends>> observable = Observable.create(subscriber -> {
            BmobQuery<Trends> query = new BmobQuery<>();
            query.include("userid,songid.User,listid.createUser");
            query.setLimit(50);
            query.order("-replysNum");
            query.findObjects(context, new FindListener<Trends>() {
                @Override
                public void onSuccess(List<Trends> list) {
                    if (list.size()!=0){
                        for (Trends trends:list){
                            if (trends.getType()==null){
                                subscriber.onError(new Throwable("获取动态失败"));

                            }
                        }
                        subscriber.onNext(list);
                    }else {
                        subscriber.onError(new Throwable("获取动态失败"));
                    }

                }

                @Override
                public void onError(int i, String s) {
                    subscriber.onError(new Throwable(new ErrorList().getErrorMsg(i)));
                }
            });
        });
        return observable;
    }
    public Observable<Trends> updataTrends(String type,String content,String id,File file) {
        Observable<Trends> observable = Observable.create(subscriber -> {
            Trends trends = new Trends();
            trends.setContent(content);
            trends.setUserid(BmobUser.getCurrentUser(context,MoiUser.class));
            if (type.equals("文字")){
                trends.setType("1");
                trends.save(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        subscriber.onNext(trends);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        subscriber.onError(new Throwable(new ErrorList().getErrorMsg(i)));
                    }
                });
            }else if (type.equals("图片")){
                trends.setType("2");
                BmobFile bmobFile = new BmobFile(file);
                bmobFile.uploadblock(context, new UploadFileListener() {
                    @Override
                    public void onSuccess() {
                        trends.setImage(bmobFile);
                        trends.save(context, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                subscriber.onNext(trends);
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                subscriber.onError(new Throwable(new ErrorList().getErrorMsg(i)));
                            }
                        });
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        subscriber.onError(new Throwable(new ErrorList().getErrorMsg(i)));
                    }
                });
            }else if (type.equals("歌曲")){
                trends.setType("3");
                trends.setReplysNum(0);
                Music music = new Music();
                music.setObjectId(id);
                trends.setSongid(music);
                trends.save(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        subscriber.onNext(trends);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        subscriber.onError(new Throwable(new ErrorList().getErrorMsg(i)));
                    }
                });
            }else if (type.equals("歌单")){
                trends.setType("4");
                MusicList musicList = new MusicList();
                musicList.setObjectId(id);
                trends.setListid(musicList);
                trends.save(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        subscriber.onNext(trends);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        subscriber.onError(new Throwable(new ErrorList().getErrorMsg(i)));
                    }
                });
            }
        });
        return observable;
    }
}
