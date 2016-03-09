package com.example.moimusic.mvp.model.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by qqq34 on 2016/2/21.
 */
public class UserFollow extends BmobObject {
    private String userId,followId;
private Integer count=1;
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    @Override
    public String toString() {
        return "UserFollow{" +
                "userId='" + userId + '\'' +
                ", followId='" + followId + '\'' +
                '}';
    }
}
