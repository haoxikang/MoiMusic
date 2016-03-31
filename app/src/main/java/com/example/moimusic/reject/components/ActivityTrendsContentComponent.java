package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;

import com.example.moimusic.reject.models.ActivityTrendsContentModule;

import com.example.moimusic.ui.activity.ActivityTrendsContent;

import dagger.Component;

/**
 * Created by Administrator on 2016/3/31.
 */
@ActivityScope
@Component(modules = ActivityTrendsContentModule.class,dependencies = AppComponent.class)
public interface ActivityTrendsContentComponent {
    ActivityTrendsContent inject(ActivityTrendsContent activityTrendsContent);
}
