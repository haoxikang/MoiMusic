package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.MainActivityModule;
import com.example.moimusic.reject.models.UserCenterActivityModule;
import com.example.moimusic.ui.activity.UserCenterActivity;

import dagger.Component;

/**
 * Created by qqq34 on 2016/2/19.
 */
@ActivityScope
@Component(modules = UserCenterActivityModule.class,dependencies = AppComponent.class)
public interface UserCenterActivityComponent {
    UserCenterActivity inject(UserCenterActivity userCenterActivity);
}
