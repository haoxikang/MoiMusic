package com.example.moimusic.mvp.model.biz;

import android.util.Log;

import com.example.moimusic.mvp.model.entity.IReply;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.MusicReply;
import com.example.moimusic.mvp.model.entity.Trends;
import com.example.moimusic.mvp.model.entity.TrendsReply;
import com.example.moimusic.utils.ErrorList;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.Observable;

/**
 * Created by 康颢曦 on 2016/4/1.
 */
public class TrendsReplysBiz extends DataBiz implements IReplysData {
    @Override
    public Observable<List<IReply>> getReplys(int page, String id) {
        Observable<List<IReply>> observable = Observable.create(subscriber -> {
            BmobQuery<TrendsReply> query = new BmobQuery<>();
            Trends trends = new Trends();
            trends.setObjectId(id);
            query.addWhereEqualTo("trendsid", new BmobPointer(trends));
            query.include("userid");
            query.order("-createdAt");
            query.setLimit(15);
            query.setSkip((page - 1) * 15);
            query.findObjects(context, new FindListener<TrendsReply>() {
                @Override
                public void onSuccess(List<TrendsReply> list) {
                    if (list.size() != 0) {
                        List<IReply> iReplies = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            iReplies.add(list.get(i));
                        }
                        subscriber.onNext(iReplies);
                    } else {
                        subscriber.onError(new Throwable("没有评论"));
                    }
                }

                @Override
                public void onError(int i, String s) {
                    subscriber.onError(new Throwable(new ErrorList().getErrorMsg(i)));
                    Log.d("没有评论","查询失败");
                }
            });
        });
        return observable;
    }

    @Override
    public Observable<String> sendComment(String s, String id) {
        Observable observable = Observable.create(subscriber -> {
            MoiUser moiUser = BmobUser.getCurrentUser(context, MoiUser.class);
            Trends trends = new Trends();
            trends.setObjectId(id);
            final TrendsReply reply = new TrendsReply();

            reply.setContent(s);
            reply.setTrendsid(trends);
            reply.setUserid(moiUser);
            reply.save(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    subscriber.onNext("send success");
                    subscriber.onCompleted();
                    trends.increment("replysNum");
                    trends.update(context, new UpdateListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }

                @Override
                public void onFailure(int i, String s) {
                    subscriber.onError(new Throwable(new ErrorList().getErrorMsg(i)));
                }
            });
        });
        return observable;
    }
}

