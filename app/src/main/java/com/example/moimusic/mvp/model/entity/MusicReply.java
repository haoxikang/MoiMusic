package com.example.moimusic.mvp.model.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by qqq34 on 2016/1/27.
 */
public class MusicReply extends BmobObject implements IReply{
    private MusicReply ParentReplyId;
    private MoiUser User;
    private Music Music;
    private String Content;

    @Override
    public String toString() {
        return "MusicReply{" +
                "ParentReplyId=" + ParentReplyId +
                ", User=" + User +
                ", Music=" + Music +
                ", Content='" + Content + '\'' +
                '}';
    }

    public MusicReply getParentReplyId() {
        return ParentReplyId;
    }

    public void setParentReplyId(MusicReply parentReplyId) {
        ParentReplyId = parentReplyId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public com.example.moimusic.mvp.model.entity.Music getMusic() {
        return Music;
    }

    public void setMusic(com.example.moimusic.mvp.model.entity.Music music) {
        Music = music;
    }

    public MoiUser getUser() {
        return User;
    }

    public void setUser(MoiUser user) {
        User = user;
    }
}
