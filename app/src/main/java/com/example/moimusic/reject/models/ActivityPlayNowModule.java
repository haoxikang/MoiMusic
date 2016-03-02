package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.ActivityPlayNowPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.activity.ActivityPlayNow;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qqq34 on 2016/2/29.
 */
@Module
public class ActivityPlayNowModule {
    private ActivityPlayNow activityPlayNow;

    public ActivityPlayNowModule(ActivityPlayNow activityPlayNow) {
        this.activityPlayNow = activityPlayNow;
    }
    @Provides
    @ActivityScope
    ActivityPlayNow provideActivityPlayNow() {
        return activityPlayNow;
    }


    @Provides
    @ActivityScope
    ActivityPlayNowPresenter provideActivityPlayNowPresenter(ApiService apiService) {
        return new ActivityPlayNowPresenter( apiService);
    }
}
