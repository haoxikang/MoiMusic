package com.example.moimusic.reject.components;


import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.MainActivityModule;
import com.example.moimusic.ui.activity.MainActivity;

import dagger.Component;

/**
 * Created by GuDong on 2015/6/10.
 */
@ActivityScope
@Component(modules = MainActivityModule.class,dependencies = AppComponent.class)

public interface MainActivityComponent {

    MainActivity inject(MainActivity mainActivity);

}
