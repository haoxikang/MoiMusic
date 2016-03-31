package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.content.Intent;

import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.Trends;
import com.example.moimusic.mvp.views.ActivityNewTrendsView;
import com.example.moimusic.mvp.views.ActivityTrendsContentView;
import com.example.moimusic.ui.activity.LogActivity;
import com.example.moimusic.ui.activity.UserCenterActivity;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/3/31.
 */
public class ActivityTrendsContentPresenter extends BasePresenterImpl {
    private Factory factory;
private Context context;
    private Trends trends;
    private ActivityTrendsContentView view;
    public ActivityTrendsContentPresenter(ApiService apiService) {
        factory = new DataBizFactory();
    }
    public void attach(ActivityTrendsContentView view, Context context){
        this.context = context;
        this.view =view;
    }

    @Override
    public void onCreate() {
        super.onCreate();
       trends = (Trends) (view.getIntent().getSerializableExtra ("TrendsObject"));
        if (trends!=null){
            view.ShowView(trends);
        }
    }
    public void onUserImageClick(){
        if (BmobUser.getCurrentUser(context,MoiUser.class)==null){
            context.startActivity(new Intent(context, LogActivity.class));
        }else {
            Intent intent = new Intent(context, UserCenterActivity.class);
            intent.putExtra("userID",trends.getUserid().getObjectId());
            context.startActivity(intent);
        }
    }
}
