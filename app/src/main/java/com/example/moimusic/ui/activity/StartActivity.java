package com.example.moimusic.ui.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.moimusic.R;
import com.example.moimusic.Servers.PlayService;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.reject.components.AppComponent;
import com.rey.material.app.Dialog;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class StartActivity extends BaseActivity {
    final public static int REQUEST_CODE_READ_PHONE_STATE= 123;
    private LinearLayout linearLayout;
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
            startService(new Intent(this, PlayService.class));
        initview();
            if (Build.VERSION.SDK_INT >= 23) {
                int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
                int checkReadStoragePermission = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
                int checkWriteStoragePermission = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                List<String> permisson = new ArrayList<>();
                if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                    permisson.add(Manifest.permission.READ_PHONE_STATE);
                }
                if (checkReadStoragePermission != PackageManager.PERMISSION_GRANTED){
                    permisson.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                if (checkWriteStoragePermission != PackageManager.PERMISSION_GRANTED){
                    permisson.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                if (permisson.size()!=0){
                    String[] strArr = new String[permisson.size()];
                    permisson.toArray(strArr);
                    ActivityCompat.requestPermissions(this,strArr,REQUEST_CODE_READ_PHONE_STATE);
                }else {
                    startActivity();
                }
            } else {
                //上面已经写好的拨号方法
                startActivity();
            }



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
                    int checkReadStoragePermission = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
                    int checkWriteStoragePermission = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if(checkCallPhonePermission == PackageManager.PERMISSION_GRANTED&&checkReadStoragePermission == PackageManager.PERMISSION_GRANTED&&checkWriteStoragePermission == PackageManager.PERMISSION_GRANTED){
                        startActivity();
                    }else {
                        finishActivity();
                    }

                } else {
                    // Permission Denied
                    finishActivity();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    private void initview() {
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
private void startActivity(){
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
    private void finishActivity(){
        Dialog dialog = new Dialog(this)
                .title(getResources().getString(R.string.failOpenPermission))
                .cancelable(false)
                .positiveAction(getResources().getString(R.string.OK))
                .positiveActionClickListener(v -> finish());
        dialog.show();
    }
}
