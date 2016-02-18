package com.example.moimusic.mvp.views;

/**
 * Created by qqq34 on 2016/2/18.
 */
public interface FragmentSignInView {
    void phoneNumberShowError(boolean isShow);
    void passwordShowError(boolean isShow);
    void showSnakBar(String s);
    void showProgressWithAllViewUnEnable(boolean isShow);
    String[] getUserText();
    void finishActivity();
}
