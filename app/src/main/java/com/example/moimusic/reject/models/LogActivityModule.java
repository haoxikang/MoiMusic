package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.LogActivityPresenter;
import com.example.moimusic.mvp.presenters.MainActivityPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.activity.LogActivity;
import com.example.moimusic.ui.activity.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qqq34 on 2016/2/14.
 */
@Module
public class LogActivityModule {
    private LogActivity logActivity;

    public LogActivityModule(LogActivity logActivity) {
        this.logActivity = logActivity;
    }
    @Provides
    @ActivityScope
    LogActivity provideLogActivity() {
        return logActivity;
    }
    @Provides
    @ActivityScope
    LogActivityPresenter provideLogActivityPresenter(ApiService apiService) {
        return new LogActivityPresenter( apiService);
    }
}
