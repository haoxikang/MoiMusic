package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.MainActivityPresenter;
import com.example.moimusic.mvp.presenters.UserCenterActivityPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.activity.UserCenterActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qqq34 on 2016/2/19.
 */
@Module
public class UserCenterActivityModule {
    private UserCenterActivity userCenterActivity;

    public UserCenterActivityModule(UserCenterActivity userCenterActivity) {
        this.userCenterActivity = userCenterActivity;
    }
    @Provides
    @ActivityScope
    UserCenterActivity provideUserCenterActivity(){
        return userCenterActivity;
    }


    @Provides
    @ActivityScope
    UserCenterActivityPresenter provideUserCenterActivityPresenter(ApiService apiService) {
        return new UserCenterActivityPresenter( apiService);
    }
}
