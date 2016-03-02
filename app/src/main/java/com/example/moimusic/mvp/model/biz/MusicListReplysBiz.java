package com.example.moimusic.mvp.model.biz;

import android.util.Log;

import com.example.moimusic.mvp.model.entity.IReply;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.mvp.model.entity.MusicListReply;
import com.example.moimusic.utils.ErrorList;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import rx.Observable;

/**
 * Created by qqq34 on 2016/2/25.
 */
public class MusicListReplysBiz extends DataBiz implements IReplysData{
    @Override
    public Observable<List<IReply>> getReplys(int page, String id) {

        Observable<List<IReply>> observable = Observable.create(subscriber -> {
            BmobQuery<MusicListReply> query = new BmobQuery<>();
            MusicList musicList = new MusicList();
            musicList.setObjectId(id);
            query.addWhereEqualTo("musicListId", new BmobPointer(musicList));
            query.include("userId");
            query.order("-createdAt");
            query.setLimit(15);
            query.setSkip((page - 1) * 15);
            query.findObjects(context, new FindListener<MusicListReply>() {
                @Override
                public void onSuccess(List<MusicListReply> list) {
                    if (list.size() != 0) {
                        List<IReply> iReplies = new ArrayList<>();
                        for (int i=0;i<list.size();i++){
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
                }
            });
        });
        return observable;
    }
    @Override
    public Observable<String> sendComment(String s,String id){
        Observable observable = Observable.create(subscriber -> {
            MoiUser moiUser = BmobUser.getCurrentUser(context,MoiUser.class);
            MusicList musicList = new MusicList();
            musicList.setObjectId(id);
           final MusicListReply reply = new MusicListReply();
            reply.setContent(s);
            reply.setMusicListId(musicList);
            reply.setUserId(moiUser);
            reply.save(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    subscriber.onNext("send success");
                    subscriber.onCompleted();
                }

                @Override
                public void onFailure(int i, String s) {
                    subscriber.onError(new Throwable(new ErrorList().getErrorMsg(i)));
                }
            });
            subscriber.onNext("send success");
        });
        return observable;
    }
}
