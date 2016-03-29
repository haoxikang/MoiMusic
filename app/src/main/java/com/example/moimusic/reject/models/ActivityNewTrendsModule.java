package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.ActivityNewTrendsPresenter;
import com.example.moimusic.mvp.presenters.ActivityPlayNowPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.activity.ActivityNewTrends;
import com.example.moimusic.ui.activity.ActivityPlayNow;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/3/29.
 */
@Module
public class ActivityNewTrendsModule {
    private ActivityNewTrends activityNewTrends;

    public ActivityNewTrendsModule(ActivityNewTrends activityNewTrends) {
        this.activityNewTrends = activityNewTrends;
    }
    @Provides
    @ActivityScope
    ActivityNewTrends provideActivityNewTrends() {
        return activityNewTrends;
    }


    @Provides
    @ActivityScope
    ActivityNewTrendsPresenter provideActivityNewTrendsPresenter(ApiService apiService) {
        return new ActivityNewTrendsPresenter( apiService);
    }
}
