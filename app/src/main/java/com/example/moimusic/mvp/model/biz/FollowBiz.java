package com.example.moimusic.mvp.model.biz;

import android.util.Log;

import com.example.moimusic.R;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.UserFollow;
import com.example.moimusic.utils.ErrorList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.FindStatisticsListener;
import cn.bmob.v3.listener.SaveListener;
import rx.Observable;

/**
 * Created by qqq34 on 2016/2/21.
 */
public class FollowBiz extends DataBiz {
    public Observable<int[]> getFollowData(String id) {
        int[] a = new int[2];
        Observable<int[]> observable = Observable.create(subscriber -> {
            BmobQuery<UserFollow> query = new BmobQuery<>();
            query.sum(new String[]{"count"});
            query.addWhereEqualTo("userId", id);
            query.findStatistics(context, UserFollow.class, new FindStatisticsListener() {

                @Override
                public void onSuccess(Object object) {
                    JSONArray ary = (JSONArray) object;
                    if (ary != null) {//
                        try {
                            JSONObject obj = ary.getJSONObject(0);
                            int sum = obj.getInt("_sumCount");//_(关键字)+首字母大写的列名
                            a[0] = sum;
                        } catch (JSONException e) {

                        }
                    } else {
                        a[0] = 0;
                    }
                    BmobQuery<UserFollow> query = new BmobQuery<>();
                    query.sum(new String[]{"count"});
                    query.addWhereEqualTo("followId", id);
                    query.findStatistics(context, UserFollow.class, new FindStatisticsListener() {

                        @Override
                        public void onSuccess(Object object) {
                            JSONArray ary = (JSONArray) object;
                            if (ary != null) {//
                                try {
                                    JSONObject obj = ary.getJSONObject(0);
                                    int sum = obj.getInt("_sumCount");
                                    a[1] = sum;
                                    subscriber.onNext(a);
                                } catch (JSONException e) {
                                    subscriber.onError(new Throwable(context.getResources().getString(R.string.unknow_error)));
                                }
                            } else {
                                a[1] = 0;
                                subscriber.onNext(a);
                            }

                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            // TODO Auto-generated method stub
                            subscriber.onError(new Throwable(new ErrorList().getErrorMsg(code)));
                        }
                    });
                }

                @Override
                public void onFailure(int code, String msg) {
                    // TODO Auto-generated method stub
                    subscriber.onError(new Throwable(new ErrorList().getErrorMsg(code)));
                }
            });
        });
        return observable;
    }
    public Observable<String> isFollowed(String id){
        Observable<String> observable = Observable.create(subscriber -> {
            BmobQuery<UserFollow> query = new BmobQuery<UserFollow>();
            query.addWhereEqualTo("userId", BmobUser.getCurrentUser(context, MoiUser.class).getObjectId());
            query.addWhereEqualTo("followId", id);
            query.findObjects(context, new FindListener<UserFollow>() {
                @Override
                public void onSuccess(List<UserFollow> object) {
                    // TODO Auto-generated method stub
                    if (object.size()!=0){
                        subscriber.onNext(object.get(0).getObjectId());
                    }else {
                        subscriber.onNext(null);
                    }
                }
                @Override
                public void onError(int code, String msg) {
                    // TODO Auto-generated method stub
                    subscriber.onError(new Throwable(new ErrorList().getErrorMsg(code)));
                }
            });
        });
        return observable;
    }
    public Observable<Boolean> deleteFollow(String id){
        Observable<Boolean> observable = Observable.create(subscriber -> {
            UserFollow userFollow = new UserFollow();
            userFollow.setObjectId(id);
            userFollow.delete(context, new DeleteListener() {

                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub
                    subscriber.onNext(true);
                }

                @Override
                public void onFailure(int code, String msg) {
                    // TODO Auto-generated method stub
                    subscriber.onError(new Throwable(new ErrorList().getErrorMsg(code)));
                }
            });
        });
        return observable;
    }
    public Observable<String> addFollow(String id){
        Observable<String> observable = Observable.create(subscriber -> {
            UserFollow userFollow = new UserFollow();
            userFollow.setUserId(BmobUser.getCurrentUser(context).getObjectId());
            userFollow.setFollowId(id);
            userFollow.save(context, new SaveListener() {

                @Override
                public void onSuccess() {
                   subscriber.onNext(userFollow.getObjectId());
                }

                @Override
                public void onFailure(int code, String arg0) {
                    // 添加失败
                    subscriber.onError(new Throwable(new ErrorList().getErrorMsg(code)));
                }
            });
        });
        return observable;
    }

}
