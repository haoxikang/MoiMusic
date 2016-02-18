package com.example.moimusic.mvp.views;

import com.example.moimusic.mvp.model.entity.EvenReCall;
import com.example.moimusic.ui.activity.LogActivity;

/**
 * Created by qqq34 on 2016/1/28.
 */
public interface IMainView {
     void setPlayViewEnable(boolean isEnable);
    void updataPlayView();
    void setPlayButton(boolean ispause);
    void setProgressbar(int current,int all );
    void startNextActivity(Class<?> cls);
    void ShowSnackbar(String s);
}
