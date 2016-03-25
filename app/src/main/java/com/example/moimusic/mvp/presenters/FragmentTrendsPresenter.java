package com.example.moimusic.mvp.presenters;

import android.content.Context;

import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.TrendsBiz;
import com.example.moimusic.mvp.views.FragmentGroomView;
import com.example.moimusic.mvp.views.FragmentTrendsView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/25.
 */
public class FragmentTrendsPresenter extends BasePresenterImpl {
    private Context context;
    private FragmentTrendsView mView;
    private Factory factory;
    public FragmentTrendsPresenter(ApiService apiService) {

    }
    public void attach(Context context, FragmentTrendsView mView) {
        this.context = context;
        this.mView = mView;
        factory = new DataBizFactory();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        getTrends();
    }

    public void getTrends(){
        TrendsBiz biz = factory.createBiz(TrendsBiz.class);
        mSubscriptions.add(biz.getTrends().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(trendses -> {
            mView.onDatafetched(trendses);
            mView.ShowSwipe(false);
        },throwable -> {
            mView.ShowSnackBar(throwable.getMessage());
            mView.ShowSwipe(false);
        }));
    }
}
