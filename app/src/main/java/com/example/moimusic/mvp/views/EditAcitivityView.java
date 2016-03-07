package com.example.moimusic.mvp.views;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by qqq34 on 2016/3/1.
 */
public interface EditAcitivityView {
    void setViewEnable(boolean isEnable);
    Intent getIntent();
    void finish();
    void setView(String imageUri,String name,String introduce,String Sex);
    void startActivity(Intent intent);
    void startActivityForResult(Intent intent,int res);
    void showImage(Uri uri);
    void showProgress(boolean isShow);
    String[] getData();
    void showSnackBar(String s);
}
