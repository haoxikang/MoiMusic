package com.example.moimusic.mvp.model.entity;

import com.a.a.a.I;
import com.vanniktech.emoji.emoji.Symbols;

import cn.bmob.v3.BmobObject;

/**
 * Created by 康颢曦 on 2016/4/1.
 */
public class TrendsReply extends BmobObject implements IReply {
    private MoiUser userid;
    private Trends trendsid;
    private TrendsReply parent_reply;
    private String content;
    private Integer likes;

    public MoiUser getUserid() {
        return userid;
    }

    public void setUserid(MoiUser userid) {
        this.userid = userid;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Trends getTrendsid() {
        return trendsid;
    }

    public void setTrendsid(Trends trendsid) {
        this.trendsid = trendsid;
    }

    public TrendsReply getParent_reply() {
        return parent_reply;
    }

    public void setParent_reply(TrendsReply parent_reply) {
        this.parent_reply = parent_reply;
    }

    @Override
    public MoiUser getUser() {
        return userid;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;

    }

}
