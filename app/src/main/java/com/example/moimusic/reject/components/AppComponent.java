package com.example.moimusic.reject.components;


import android.app.Application;


import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.reject.models.ApiServiceModule;
import com.example.moimusic.reject.models.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by GuDong on 2015/12/24.
 */
@Singleton
@Component(modules = {AppModule.class, ApiServiceModule.class})
public interface AppComponent {

    Application getApplication();

    ApiService getService();

}
