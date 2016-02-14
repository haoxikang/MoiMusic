package com.example.moimusic.ui.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.moimusic.R;
import com.example.moimusic.Servers.PlayService;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.reject.components.AppComponent;
import java.util.List;

import cn.bmob.v3.Bmob;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams. FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_start);
        Bmob.initialize(this, "8d05a01202de8f853bcaf7d088d2be4d");
        if (PlayListSingleton.INSTANCE.getMusicList()==null){
            PlayListSingleton.INSTANCE.initPlayList();
        }
       //初始化 ：后期需要做保存工作，如果存了就不用初始化了
            startService(new Intent(this, PlayService.class));
        initview();
        new Thread(() -> {
            try {
                Thread.sleep(3000);
               startActivity(new Intent(StartActivity.this,MainActivity.class));
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    private void initview() {
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
