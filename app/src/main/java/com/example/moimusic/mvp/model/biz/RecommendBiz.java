package com.example.moimusic.mvp.model.biz;





import android.util.Log;

import com.example.moimusic.mvp.model.entity.Recommend;

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
                    Log.d("传进来!",list.get(0).getWhere());
                }

                @Override
                public void onError(int i, String s) {
                }
            });
        });
        return observable;
    }
}
