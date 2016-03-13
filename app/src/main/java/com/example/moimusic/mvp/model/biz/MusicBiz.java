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
            BmobQuery<Music> query = new BmobQuery<Music>();
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

}
