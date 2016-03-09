package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.ActivityEditMusicListModule;
import com.example.moimusic.ui.activity.ActivityEditMusicList;

import dagger.Component;

/**
 * Created by Administrator on 2016/3/9.
 */
@ActivityScope
@Component(modules = ActivityEditMusicListModule.class,dependencies = AppComponent.class)
public interface ActivityEditMusicListComponent {
    ActivityEditMusicList inject(ActivityEditMusicList activityMusicList);
}
