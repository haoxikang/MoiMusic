package com.example.moimusic.mvp.model;


import com.example.moimusic.mvp.model.entity.MoiUser;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by qqq34 on 2016/1/28.
 */
public interface ApiService {
    @Headers({"X-Bmob-Application-Id:8d05a01202de8f853bcaf7d088d2be4d","X-Bmob-REST-API-Key:77f2cf2316d31eda2c10670ef6f2a1f1"})
    @GET("{body}")
    Observable<MoiUser> getUser(@Path("body") String body);
}
