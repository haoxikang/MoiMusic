package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.ActivityNewTrendsModule;

import com.example.moimusic.ui.activity.ActivityNewTrends;


import dagger.Component;

/**
 * Created by Administrator on 2016/3/29.
 */
@ActivityScope
@Component(modules = ActivityNewTrendsModule.class,dependencies = AppComponent.class)
public interface ActivityNewTrendsComponent {
    ActivityNewTrends inject(ActivityNewTrends activityNewTrends);
}
