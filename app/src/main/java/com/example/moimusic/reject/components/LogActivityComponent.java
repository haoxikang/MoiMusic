package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.LogActivityModule;
import com.example.moimusic.reject.models.MainActivityModule;
import com.example.moimusic.ui.activity.LogActivity;
import com.example.moimusic.ui.activity.MainActivity;

import dagger.Component;

/**
 * Created by qqq34 on 2016/2/14.
 */
@ActivityScope
@Component(modules = LogActivityModule.class,dependencies = AppComponent.class)
public interface LogActivityComponent {
    LogActivity inject(LogActivity logActivity);
}
