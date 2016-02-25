package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.ActivityMusicListPresenter;
import com.example.moimusic.mvp.presenters.MainActivityPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.activity.ActivityMusicList;
import com.example.moimusic.ui.activity.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qqq34 on 2016/2/23.
 */
@Module
public class ActivityMusicListModule {

    private ActivityMusicList activityMusicList;

    public ActivityMusicListModule(ActivityMusicList activityMusicList) {
        this.activityMusicList = activityMusicList;
    }


    @Provides
    @ActivityScope
    ActivityMusicList provideActivityMusicList() {
        return activityMusicList;
    }


    @Provides
    @ActivityScope
    ActivityMusicListPresenter provideActivityMusicListPresenter(ApiService apiService) {
        return new ActivityMusicListPresenter( apiService);
    }
}
