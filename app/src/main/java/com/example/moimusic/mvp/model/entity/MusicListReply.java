package com.example.moimusic.mvp.model.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by qqq34 on 2016/2/25.
 */
public class MusicListReply extends BmobObject implements IReply{
    private MoiUser userId;
    private MusicList musicListId;
    private MusicListReply parentId;
    private String content;
    private Integer likes;

    @Override
    public String toString() {
        return "MusicListReply{" +
                "userId=" + userId +
                ", musicListId=" + musicListId +
                ", parentId=" + parentId +
                ", content='" + content + '\'' +
                ", likes=" + likes +
                '}';
    }

    public MoiUser getUserId() {
        return userId;
    }

    public void setUserId(MoiUser userId) {
        this.userId = userId;
    }

    public MusicList getMusicListId() {
        return musicListId;
    }

    public void setMusicListId(MusicList musicListId) {
        this.musicListId = musicListId;
    }

    public MusicListReply getParentId() {
        return parentId;
    }

    public void setParentId(MusicListReply parentId) {
        this.parentId = parentId;
    }

    @Override
    public MoiUser getUser() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
