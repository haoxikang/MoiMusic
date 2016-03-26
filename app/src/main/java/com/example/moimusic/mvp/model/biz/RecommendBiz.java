package com.example.moimusic.mvp.model.biz;





import android.util.Log;

import com.example.moimusic.mvp.model.entity.Recommend;
import com.example.moimusic.utils.ErrorList;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import rx.Observable;

/**
 * Created by qqq34 on 2016/3/17.
 */
public class RecommendBiz extends DataBiz {
    public Observable<List<Recommend>> getRecommend(){
        Observable<List<Recommend>> observable = Observable.create(subscriber -> {
            BmobQuery<Recommend> query = new BmobQuery<>();
            query.findObjects(context, new FindListener<Recommend>() {
                @Override
                public void onSuccess(List<Recommend> list) {
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
