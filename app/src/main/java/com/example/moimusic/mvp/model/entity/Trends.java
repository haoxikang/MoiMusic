package com.example.moimusic.mvp.model.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/3/24.
 */
public class Trends extends BmobObject {
    private MoiUser userid;
    private Music songid;
    private MusicList listid;
    private String content,type;
    private Integer likes,replysNum;
private BmobFile image;

    @Override
    public String toString() {
        return "Trends{" +
                "userid=" + userid +
                ", songid=" + songid +
                ", listid=" + listid +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", likes=" + likes +
                ", replysNum=" + replysNum +
                ", image=" + image +
                '}';
    }

    public Integer getReplysNum() {
        return replysNum;
    }

    public void setReplysNum(Integer replysNum) {
        this.replysNum = replysNum;
    }

    public MoiUser getUserid() {
        return userid;
    }

    public void setUserid(MoiUser userid) {
        this.userid = userid;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MusicList getListid() {
        return listid;
    }

    public void setListid(MusicList listid) {
        this.listid = listid;
    }

    public Music getSongid() {
        return songid;
    }

    public void setSongid(Music songid) {
        this.songid = songid;
    }
}
