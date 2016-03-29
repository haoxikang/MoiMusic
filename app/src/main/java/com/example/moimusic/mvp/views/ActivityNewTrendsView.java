package com.example.moimusic.mvp.views;

/**
 * Created by Administrator on 2016/3/29.
 */
public interface ActivityNewTrendsView {
    void showBlackShare();
    void showMusicShare();
    void showSnackbar(String s);
    void showProgress(boolean isShow);
    String getEditText();
}
