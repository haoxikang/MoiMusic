package com.example.moimusic.mvp.views;

import android.content.Intent;

import com.example.moimusic.mvp.model.entity.Trends;

/**
 * Created by Administrator on 2016/3/31.
 */
public interface ActivityTrendsContentView {
    void ShowView(Trends trends);
    Intent getIntent();
}
