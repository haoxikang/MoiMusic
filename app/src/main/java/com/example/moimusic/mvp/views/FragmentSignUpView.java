package com.example.moimusic.mvp.views;

/**
 * Created by qqq34 on 2016/2/17.
 */
public interface FragmentSignUpView {
    void phoneNumberShowError(boolean isShow);
    void passwordShowError(boolean isShow);
    void phoneNumberEnable(boolean isEnable);
    void showSnakBar(String s);
    void setButonFlatEnable(boolean isEnable);
    void setButtonFlatText(String s);
    String[] getUserText();
    void showProgressWithAllViewUnEnable(boolean isShow);
    void finishActivity();
}
