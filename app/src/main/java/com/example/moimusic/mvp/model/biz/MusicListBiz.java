package com.example.moimusic.mvp.model.biz;

import android.content.Context;
import android.util.Log;


import com.example.moimusic.R;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.utils.ErrorList;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import rx.Observable;

/**
 * Created by qqq34 on 2016/1/29.
 */
public class MusicListBiz  extends DataBiz{

    public Observable<List<MusicList>> getMyMusic(int page,boolean isMe) {
        MoiUser moiUser = BmobUser.getCurrentUser(context,MoiUser.class);
        Observable<List<MusicList>> observable = Observable.create(subscriber -> {
            BmobQuery<MusicList> query = new BmobQuery<>();
            query.addWhereRelatedTo("myMusicList", new BmobPointer(moiUser));
            if (!isMe){
                query.addWhereEqualTo("isRelease",true);
            }
            query.setLimit(10);
            query .setSkip((page-1)*10);
            query.findObjects(context, new FindListener<MusicList>() {
                @Override
                public void onSuccess(List<MusicList> list) {
                    Log.d("TAG","成功");
                    if (list.size()==0){
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
    public Observable<List<MusicList>> getCollegeMusic(int page) {
        MoiUser moiUser = BmobUser.getCurrentUser(context,MoiUser.class);
        Observable<List<MusicList>> observable = Observable.create(subscriber -> {
            BmobQuery<MusicList> query = new BmobQuery<>();
            query.addWhereRelatedTo("College", new BmobPointer(moiUser));
            query.setLimit(10);
            query .setSkip((page-1)*10);
            query.findObjects(context, new FindListener<MusicList>() {
                @Override
                public void onSuccess(List<MusicList> list) {
                    Log.d("TAG","成功");
                    if (list.size()==0){
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
}
