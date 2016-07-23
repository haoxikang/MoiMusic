package com.example.moimusic.mvp.model.updata;

import com.example.moimusic.mvp.model.entity.IReply;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import rx.Observable;

/**
 * Created by 康颢曦 on 2016/4/13.
 */
public interface IFileUpload {
    Observable<Integer> startUpload(BmobFile file);
    boolean stopUpload(SimpleFile simpleFile);
}
