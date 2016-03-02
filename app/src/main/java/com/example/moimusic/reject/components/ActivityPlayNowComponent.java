package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.ActivityMusicListModule;
import com.example.moimusic.reject.models.ActivityPlayNowModule;
import com.example.moimusic.ui.activity.ActivityMusicList;
import com.example.moimusic.ui.activity.ActivityPlayNow;

import dagger.Component;

/**
 * Created by qqq34 on 2016/2/29.
 */
@ActivityScope
@Component(modules = ActivityPlayNowModule.class,dependencies = AppComponent.class)
public interface ActivityPlayNowComponent {
    ActivityPlayNow inject(ActivityPlayNow activityPlayNow);
}
