package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.TrendsBiz;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.Trends;
import com.example.moimusic.mvp.views.FragmentGroomView;
import com.example.moimusic.mvp.views.FragmentTrendsView;
import com.example.moimusic.ui.activity.ActivityNewTrends;
import com.example.moimusic.ui.activity.LogActivity;
import com.example.moimusic.ui.fragment.FragmentTrends;

import cn.bmob.v3.BmobUser;
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
        mSubscriptions.add(biz.getTrends()
        .subscribe(trendses -> {
            for (Trends trends:trendses){
                if (trends.getType()==null){
                    trendses.remove(trends);
                }else {
                    Log.d("trends",trends.toString());
                }
            }
            if (trendses.size()!=0){
                mView.onDatafetched(trendses);
                mView.ShowSwipe(false);
            }else {
                mView.ShowSwipe(false);
            }

        },throwable -> {
            mView.ShowSwipe(false);
        }));
    }
    public void onFabClick(){
        if (BmobUser.getCurrentUser(context, MoiUser.class)==null){
            Intent intent = new Intent(context, LogActivity.class);
            mView.startActivity(intent);
        }else {
            Intent intent = new Intent(context, ActivityNewTrends.class);
            intent.putExtra("shareType","文字");
            mView.startActivity(intent);
        }


    }
}
