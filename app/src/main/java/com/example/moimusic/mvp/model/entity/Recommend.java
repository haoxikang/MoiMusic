package com.example.moimusic.mvp.model.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by qqq34 on 2016/3/17.
 */
public class Recommend extends BmobObject {
    private String into,where;
    private BmobFile image;

    public String getInto() {
        return into;
    }

    public void setInto(String into) {
        this.into = into;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Recommend{" +
                "into='" + into + '\'' +
                ", where='" + where + '\'' +
                ", image=" + image +
                '}';
    }
}
