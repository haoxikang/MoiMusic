package com.example.moimusic.mvp.model.biz;



import com.example.moimusic.mvp.model.entity.Recommend;
import com.example.moimusic.utils.ErrorList;
import com.rey.material.widget.RelativeLayout;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import rx.Observable;

/**
 * Created by qqq34 on 2016/3/17.
 */
public class RecommendBiz extends DataBiz {
    public Observable<List<Recommend>> getRecommend(String type){
        Observable<List<Recommend>> observable = Observable.create(subscriber -> {
            BmobQuery<Recommend> query = new BmobQuery<Recommend>();
            query.addWhereEqualTo("where",type);
            query.setLimit(4);
            query.findObjects(context, new FindListener<Recommend>() {
                @Override
                public void onSuccess(List<Recommend> list) {
                    subscriber.onNext(list);
                }

                @Override
                public void onError(int i, String s) {
                }
            });
        });
        return observable;
    }
}
