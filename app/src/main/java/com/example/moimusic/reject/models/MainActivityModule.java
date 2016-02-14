package com.example.moimusic.reject.models;




import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.MainActivityPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.activity.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by GuDong on 2015/6/10.
 */
@Module
public class MainActivityModule {

    private MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @Provides
    @ActivityScope
    MainActivity provideMainActivity() {
        return mainActivity;
    }


    @Provides
    @ActivityScope
    MainActivityPresenter provideMainActivityPresenter(ApiService apiService) {
        return new MainActivityPresenter( apiService);
    }


}
