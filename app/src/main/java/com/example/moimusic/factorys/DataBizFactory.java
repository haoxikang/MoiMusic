package com.example.moimusic.factorys;

import com.example.moimusic.AppApplication;
import com.example.moimusic.mvp.model.biz.DataBiz;

/**
 * Created by qqq34 on 2016/1/29.
 */
public class DataBizFactory extends Factory {

    @Override
    public <T extends DataBiz> T createBiz(Class<T> clz) {
        DataBiz dataBiz = null;
        try {
            dataBiz = (DataBiz)Class.forName(clz.getName()).newInstance();
            dataBiz.setContext(AppApplication.context);
        }catch (Exception e){
            e.printStackTrace();
        }
        return (T)dataBiz;
    }
}
