package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.moimusic.R;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.FollowBiz;
import com.example.moimusic.mvp.model.biz.UserBiz;
import com.example.moimusic.mvp.model.entity.EvenFollowCall;
import com.example.moimusic.mvp.model.entity.EvenUserCall;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.views.IMainView;
import com.example.moimusic.mvp.views.IUserCenterActivity;
import com.example.moimusic.ui.activity.EditActivity;
import com.example.moimusic.ui.activity.LogActivity;
import com.example.moimusic.utils.Utils;

import cn.bmob.v3.BmobUser;
import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/2/19.
 */
public class UserCenterActivityPresenter extends BasePresenterImpl {
    private ApiService api;
    private IUserCenterActivity mView;
    private Factory factory;
    private Context context;
    private MoiUser User;
    private boolean isFollowed;
    private String followedId;
    private int[] i;

    public UserCenterActivityPresenter(ApiService apiService) {
        factory = new DataBizFactory();
        api = apiService;
        EventBus.getDefault().register(this);
    }

    public void attach(IUserCenterActivity iView, Context context) {
        mView = iView;
        this.context = context;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (isCurrentUser()) {
            mView.hideFloatButton(true);
        }
        Intent intent = mView.GetIntent();
        String s = intent.getStringExtra("userID");
        FollowBiz followBiz = factory.createBiz(FollowBiz.class);
        mSubscriptions.add(followBiz.isFollowed(s)
                .subscribe(s1 -> {
                    if (s1 == null) {

                        isFollowed = false;
                        mView.setButtonBK(false);
                    } else {
                        followedId = s1;
                        isFollowed = true;
                        mView.setButtonBK(true);
                    }
                },throwable -> {
                    Log.d("ERROR","1");
                    mView.ShowSnackBar(throwable.getMessage());
                })
        );

    }

    public boolean isCurrentUser() {
        Intent intent = mView.GetIntent();
        String s = intent.getStringExtra("userID");
        if (s.equals("") || s == null) {
            mView.ToNextActivity(new Intent(context, LogActivity.class));
            mView.finishActivity();
        }
        Log.d("TAG", "id=" + s);
        if (s.equals(BmobUser.getCurrentUser(context, MoiUser.class).getObjectId())) {
            return true;
        } else
            return false;
    }

    public String getID() {
        Intent intent = mView.GetIntent();
        String s = intent.getStringExtra("userID");
        return s;
    }

    public void setView() {
        Intent intent = mView.GetIntent();
        String s = intent.getStringExtra("userID");
        FollowBiz followBiz = factory.createBiz(FollowBiz.class);
        UserBiz userBiz = factory.createBiz(UserBiz.class);
        mSubscriptions.add(followBiz.getFollowData(s)
                .subscribe(ints -> {
                    if (s.equals(BmobUser.getCurrentUser(context, MoiUser.class).getObjectId())) {
                        User = BmobUser.getCurrentUser(context, MoiUser.class);
                        mView.updataView(BmobUser.getCurrentUser(context, MoiUser.class), ints[0], ints[1]);
                        i = ints;
                    } else {
                        mSubscriptions.add(userBiz.getUser(s)
                                .subscribe(moiUser -> {
                                    User = moiUser;
                                    mView.updataView(moiUser, ints[0], ints[1]);
                                    i = ints;
                                }, throwable -> {
                                    mView.ShowSnackBar(throwable.getMessage());
                                }));
                    }
                }, throwable1 -> {
                    mView.ShowSnackBar(throwable1.getMessage());
                }));


    }

    public void startEditActivity() {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra("isCurrent", isCurrentUser());
        if (User.getImageFile()!=null){
            intent.putExtra("userImage", User.getImageFile().getFileUrl(context));
        }

        intent.putExtra("userName", User.getName());
        intent.putExtra("userIntroduce", User.getIntroduction());
        intent.putExtra("userSex", User.getSex());


        mView.ToNextActivity(intent);
    }

    public void onFollowClick() {

        Intent intent = mView.GetIntent();
        String s = intent.getStringExtra("userID");
        FollowBiz followBiz = factory.createBiz(FollowBiz.class);
        if (isFollowed) {
            mView.setButtonEnable(false);
            mSubscriptions.add(followBiz.deleteFollow(followedId)
                    .subscribe(aBoolean -> {
                        isFollowed = false;
                        mView.setButtonBK(false);
                        mView.ShowSnackBar(context.getResources().getString(R.string.unfollowSuccess));
                        mView.setButtonEnable(true);
                        EvenFollowCall evenFollowCall = new EvenFollowCall();
                        evenFollowCall.setFollow(false);
                        EventBus.getDefault().post(evenFollowCall);
                    }, throwable -> {
                        mView.ShowSnackBar(throwable.getMessage());
                        mView.setButtonEnable(true);
                    }));
        } else {
            mSubscriptions.add(followBiz.addFollow(s)
                    .subscribe(s1 -> {
                        followedId = s1;
                        isFollowed = true;
                        mView.setButtonBK(true);
                        mView.setButtonEnable(true);
                        mView.ShowSnackBar(context.getResources().getString(R.string.followSuccess));
                        EvenFollowCall evenFollowCall = new EvenFollowCall();
                        evenFollowCall.setFollow(true);
                        EventBus.getDefault().post(evenFollowCall);
                    }, throwable -> {
                        mView.ShowSnackBar(throwable.getMessage());
                        mView.setButtonEnable(true);
                    }));
        }
    }

    public void onEventMainThread(EvenUserCall evenUserCall) {
        mView.ShowSnackBar(context.getResources().getString(R.string.userInfoUpdataSucc));
        User = BmobUser.getCurrentUser(context, MoiUser.class);
        mView.updataImageAndName(BmobUser.getCurrentUser(context, MoiUser.class).getImageFile().getFileUrl(context), BmobUser.getCurrentUser(context, MoiUser.class).getName());
    }

    public void onEventMainThread(EvenFollowCall evenFollowCall) {
        if (isCurrentUser()) {
            if (evenFollowCall.isFollow()) {
                i[0]++;
            } else {
                i[0]--;
            }
            mView.updataView(BmobUser.getCurrentUser(context, MoiUser.class), i[0], i[1]);
        }
    }
}
