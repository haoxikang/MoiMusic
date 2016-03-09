package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.ActivityEditMusicListPresenter;
import com.example.moimusic.mvp.presenters.ActivityMusicListPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.activity.ActivityEditMusicList;
import com.example.moimusic.ui.activity.ActivityMusicList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/3/9.
 */

@Module
public class ActivityEditMusicListModule {
    private ActivityEditMusicList activityEditMusicList;

    public ActivityEditMusicListModule(ActivityEditMusicList activityEditMusicList) {
        this.activityEditMusicList = activityEditMusicList;
    }

    @Provides
    @ActivityScope
    ActivityEditMusicList provideActivityEditMusicList() {
        return activityEditMusicList;
    }


    @Provides
    @ActivityScope
    ActivityEditMusicListPresenter provideActivityEditMusicListPresenter(ApiService apiService) {
        return new ActivityEditMusicListPresenter( apiService);
    }
}
