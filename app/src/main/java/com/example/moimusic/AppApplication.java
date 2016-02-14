package com.example.moimusic;

import android.app.Application;
import android.content.Context;

import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerAppComponent;
import com.example.moimusic.reject.models.ApiServiceModule;
import com.example.moimusic.reject.models.AppModule;
import com.facebook.drawee.backends.pipeline.Fresco;


/**
 * Created by qqq34 on 2016/1/28.
 */
public class AppApplication extends Application {

    private AppComponent appComponent;
    public static Context context;

    public static AppApplication get(Context context){
        return (AppApplication)context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent= DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiServiceModule(new ApiServiceModule())
                .build();
        context = getApplicationContext();
        Fresco.initialize(context);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
