package com.example.moimusic.mvp.presenters;

import android.content.Context;

import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.views.ActivityNewTrendsView;
import com.example.moimusic.ui.activity.ActivityNewTrends;

/**
 * Created by Administrator on 2016/3/29.
 */
public class ActivityNewTrendsPresenter extends BasePresenterImpl {
    private Factory factory;
    private Context context;
    private ActivityNewTrendsView view;
    private String ShareType;
    private String name;
    private String singer;
    public ActivityNewTrendsPresenter(ApiService apiService) {
        factory = new DataBizFactory();
    }
    public void attach(ActivityNewTrendsView view, Context context){
        this.context = context;
        this.view =view;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ShareType= ((ActivityNewTrends)context).getIntent().getStringExtra("shareType");
        if (ShareType.equals("文字")){
                view.showBlackShare();
        }else if(ShareType.equals("歌曲")||ShareType.equals("歌单")){
                name= ((ActivityNewTrends)context).getIntent().getStringExtra("shareName");
            singer= ((ActivityNewTrends)context).getIntent().getStringExtra("shareSinger");
            view.showMusicShare();
        }
    }
}
