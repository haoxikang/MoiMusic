package com.example.moimusic.factorys;

import android.app.Application;

import com.example.moimusic.AppApplication;
import com.example.moimusic.mvp.model.biz.DataBiz;

import javax.inject.Inject;

/**
 * Created by qqq34 on 2016/1/29.
 */
public abstract class Factory {
    public abstract <T extends DataBiz> T createBiz(Class<T> clz);
}
