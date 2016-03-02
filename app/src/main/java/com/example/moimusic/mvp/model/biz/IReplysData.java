package com.example.moimusic.mvp.model.biz;

import com.example.moimusic.mvp.model.entity.IReply;
import com.example.moimusic.mvp.model.entity.MusicListReply;

import java.util.Collection;
import java.util.List;

import rx.Observable;

/**
 * Created by qqq34 on 2016/2/28.
 */
public interface IReplysData {
    Observable<List<IReply>> getReplys(int page, String id);
    Observable<String> sendComment(String s,String id);
}
