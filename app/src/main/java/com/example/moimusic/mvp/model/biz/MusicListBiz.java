package com.example.moimusic.mvp.model.biz;

import android.content.Context;
import android.util.Log;


import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.MusicList;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import rx.Observable;

/**
 * Created by qqq34 on 2016/1/29.
 */
public class MusicListBiz  extends DataBiz{

    public Observable<List<MusicList>> getCollegeMusic(MoiUser moiUser) {
        Observable<List<MusicList>> observable = Observable.create(subscriber -> {
            BmobQuery<MusicList> query = new BmobQuery<>();
            query.addWhereRelatedTo("College", new BmobPointer(moiUser));
            query.findObjects(context, new FindListener<MusicList>() {
                @Override
                public void onSuccess(List<MusicList> list) {
                    Log.d("TAG","成功");
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                }

                @Override
                public void onError(int i, String s) {
                    subscriber.onError(new Throwable(i+""));
                }
            });
        });
        return observable;
    }

}
