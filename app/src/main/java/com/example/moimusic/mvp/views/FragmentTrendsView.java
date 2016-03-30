package com.example.moimusic.mvp.views;

import android.content.Intent;

import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.Trends;

import java.util.List;

/**
 * Created by Administrator on 2016/3/25.
 */
public interface FragmentTrendsView {
    void ShowSnackBar(String s);
    void onDatafetched(List<Trends> trendses);
     void ShowSwipe(boolean isShow);
    void startActivity(Intent intent);
}
