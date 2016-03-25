package com.example.moimusic.mvp.model.biz;

import com.example.moimusic.mvp.model.entity.Recommend;
import com.example.moimusic.mvp.model.entity.Trends;
import com.example.moimusic.utils.ErrorList;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
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
