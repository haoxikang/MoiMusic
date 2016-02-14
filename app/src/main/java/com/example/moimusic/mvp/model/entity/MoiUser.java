package com.example.moimusic.mvp.model.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by qqq34 on 2016/1/27.
 */
public class MoiUser extends BmobUser{
    private BmobRelation College;
    private String ImageUri;
    private String Introduction;
    private String Sex ;
    private String Age;
    private String Name;

    public BmobRelation getCollege() {
        return College;
    }

    public void setCollege(BmobRelation college) {
        College = college;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getIntroduction() {
        return Introduction;
    }

    public void setIntroduction(String introduction) {
        Introduction = introduction;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    @Override
    public String toString() {
        return "MoiUser{" +
                "College=" + College +
                ", ImageUri='" + ImageUri + '\'' +
                ", Introduction='" + Introduction + '\'' +
                ", Sex='" + Sex + '\'' +
                ", Age='" + Age + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
