package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.ActivityPlayNowPresenter;
import com.example.moimusic.mvp.presenters.ActivityTrendsContentPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.activity.ActivityPlayNow;
import com.example.moimusic.ui.activity.ActivityTrendsContent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/3/31.
 */
@Module
public class ActivityTrendsContentModule {
    private ActivityTrendsContent activityTrendsContent;

    public ActivityTrendsContentModule(ActivityTrendsContent activityTrendsContent) {
        this.activityTrendsContent = activityTrendsContent;
    }
    @Provides
    @ActivityScope
    ActivityTrendsContent provideActivityTrendsContent() {
        return activityTrendsContent;
    }


    @Provides
    @ActivityScope
    ActivityTrendsContentPresenter provideActivityTrendsContentPresenter(ApiService apiService) {
        return new ActivityTrendsContentPresenter( apiService);
    }
}
