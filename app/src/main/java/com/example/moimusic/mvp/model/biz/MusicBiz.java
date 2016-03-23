package com.example.moimusic.mvp.model.biz;

import android.util.Log;

import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.utils.ErrorList;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import rx.Observable;

/**
 * Created by qqq34 on 2016/1/29.
 */
public class MusicBiz extends DataBiz {
    public Observable<List<Music>> getMusics() {

            Observable<List<Music>> observable = Observable.create(subscriber -> {
                BmobQuery<Music> query = new BmobQuery<>();
                query.findObjects(context, new FindListener<Music>() {
                    @Override
                    public void onSuccess(List<Music> list) {
                        Log.d("TAG", "成功" + list.size());
                        subscriber.onNext(list);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(int i, String s) {
                        subscriber.onError(new Throwable(new ErrorList().getErrorMsg(i)));
                    }
                });
            });
            return observable;
        }

    public Observable<List<Music>> getMusic(String id){
        Observable<List<Music>> observable = Observable.create(subscriber -> {
            BmobQuery<Music> query = new BmobQuery<>();
            MusicList musicList = new MusicList();
            musicList.setObjectId(id);
            query.addWhereRelatedTo("Music", new BmobPointer(musicList));
            query.findObjects(context, new FindListener<Music>() {
                @Override
                public void onSuccess(List<Music> list) {

                    Log.d("TAG", "成功" + list.size());
                    if (list.size()==0){
                        subscriber.onError(new Throwable("没有歌曲"));
                    }else {
                        subscriber.onNext(list);
                        subscriber.onCompleted();
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
public Observable<List<Music>> searchMusic(String name){
    Observable<List<Music>> observable = Observable.create(subscriber -> {
       BmobQuery<Music> query = new BmobQuery<>();
        query.addWhereContains("MusicName", name);
        query.setLimit(8);
        query.findObjects(context, new FindListener<Music>() {
            @Override
            public void onSuccess(List<Music> list) {
                subscriber.onNext(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    });
    return observable;
}
    public Observable<List<Music>> getHomePageMusic(int sort,int type){
        Observable<List<Music>> observable = Observable.create(subscriber -> {
            BmobQuery<Music> query = new BmobQuery<Music>();
            if (type==1){
                query.addWhereExists("wangyiID");  //1查询动漫歌曲  2查询原创歌曲  3,查询全部
            }else if(type==2){
                query.addWhereDoesNotExists("wangyiID");
            }

            query.setLimit(6);
            if (sort==1){   //1是最热,2是最新
                query.order("-PlayNum");
            }else if (sort==2){
                query.order("-createdAt");
            }
            query.findObjects(context, new FindListener<Music>() {
                @Override
                public void onSuccess(List<Music> list) {
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
