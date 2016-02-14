package com.example.moimusic.ui.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.moimusic.AppApplication;
import com.example.moimusic.R;
import com.example.moimusic.reject.components.AppComponent;
import com.readystatesoftware.systembartint.SystemBarTintManager;


/**
 * Created by qqq34 on 2016/1/28.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActivityComponent(AppApplication.get(this).getAppComponent());

    }

    protected abstract  void setupActivityComponent(AppComponent appComponent);

}
