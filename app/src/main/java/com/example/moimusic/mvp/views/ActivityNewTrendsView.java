package com.example.moimusic.mvp.views;

import android.net.Uri;

/**
 * Created by Administrator on 2016/3/29.
 */
public interface ActivityNewTrendsView {
    void showBlackShare();
    void showMusicShare(String Name,String Singer,String image);
    void showSnackbar(String s);
    void showProgress(boolean isShow);
    String getEditText();
    void ShowImage(Uri uri);
    void finish();
}
