package com.example.moimusic.mvp.presenters;

import android.content.Context;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.views.IMainView;
import com.example.moimusic.mvp.views.IlogView;

import de.greenrobot.event.EventBus;

/**
 * Created by qqq34 on 2016/2/14.
 */
public class LogActivityPresenter extends BasePresenterImpl {
    private ApiService api;
    private IlogView ilogView;
    private Context context;
    public LogActivityPresenter(ApiService api) {
        this.api = api;
    }
    public void attach(IlogView ilogView, Context context){
        this.ilogView = ilogView;
        this.context = context;

    }
    public void setTabs(boolean isSelect){
        ilogView.SelectTabs(isSelect);
    }
}
