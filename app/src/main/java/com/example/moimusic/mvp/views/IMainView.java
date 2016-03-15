package com.example.moimusic.mvp.views;

import android.content.Intent;
import android.os.Bundle;

import com.example.moimusic.mvp.model.entity.EvenReCall;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.ui.activity.LogActivity;

import java.util.List;

import rx.Observable;

/**
 * Created by qqq34 on 2016/1/28.
 */
public interface IMainView {
     void setPlayViewEnable(boolean isEnable);
    void updataPlayView();
    void setPlayButton(boolean ispause);
    void setProgressbar(int current,int all );
    void startNextActivity(Intent intent);
    void ShowSnackbar(String s);
    void ShowLongSnackbar(String s);
    Observable<String> getSearchObservable();
    void onSearched(List<Music> musics);
}
