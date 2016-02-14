package com.example.moimusic.mvp.model.biz;

import android.content.Context;

import java.util.List;
import java.util.Objects;

import rx.Observable;

/**
 * Created by qqq34 on 2016/1/29.
 */
public abstract class DataBiz {
    protected Context context;
    public void setContext(Context context){
        this.context = context;
    }
}
