package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.example.moimusic.AppApplication;
import com.example.moimusic.R;
import com.example.moimusic.adapter.MusicListReplyAdapter;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.MusicListReplysBiz;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.views.FragmentMuiscListReplysView;
import com.example.moimusic.ui.activity.LogActivity;
import com.example.moimusic.ui.fragment.FragmentMuiscListReplys;
import com.rey.material.app.Dialog;

import cn.bmob.v3.BmobUser;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/2/25.
 */
public class FragmentMuiscListReplysPresenter extends BasePresenterImpl {
    private Factory factory;
    private FragmentMuiscListReplysView view;
    private String id;
    private int page = 1;
    private MusicListReplyAdapter adapter;
    private FragmentActivity activity;
    public FragmentMuiscListReplysPresenter(ApiService apiService) {
        factory = new DataBizFactory();
    }
    public void attach(String id,FragmentMuiscListReplysView view,FragmentActivity activity){
        this.view = view;
        this.id=id;
        this.activity = activity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MusicListReplysBiz biz = factory.createBiz(MusicListReplysBiz.class);
        mSubscriptions.add(biz.getReplys(page,id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(musicListReplies -> {
                if (page==1){
                     adapter = new MusicListReplyAdapter(musicListReplies,activity);
                    view.ShowList(adapter);
                }else if (adapter!=null){
                    adapter.addData(musicListReplies);

                }
            page++;
        },throwable -> {
            view.ShowSnackBar(throwable.getMessage());
        }));
    }
    public void sendComment(String s){
        MoiUser moiUser = BmobUser.getCurrentUser(AppApplication.context,MoiUser.class);
        if (moiUser==null){
            Dialog mDialog = new Dialog(activity);
            mDialog.setTitle(activity.getResources().getString(R.string.frendlyhint));
            mDialog.positiveAction(activity.getResources().getString(R.string.OK));
            TextView textView = new TextView(activity);
            textView.setText(activity.getResources().getString(R.string.notLog));
            textView.setPadding(70,0,70,0);
            textView.setTextSize(16);
            mDialog.setContentView(textView);
            mDialog   .cancelable(false);
            mDialog.positiveActionClickListener(v -> {
                mDialog.dismiss();
                activity.startActivity(new Intent(activity, LogActivity.class));
            });
            mDialog.show();
        }else {
            view.setViewEnable(false);
            MusicListReplysBiz biz = factory.createBiz(MusicListReplysBiz.class);
            mSubscriptions.add(biz.sendComment(s,id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            .subscribe(s1 -> {
                view.setViewEnable(true);
                view.ShowSnackBar(activity.getResources().getString(R.string.sendSuccess));
                view.updataList();
            },throwable -> {
                view.ShowSnackBar(throwable.getMessage());
            }));
        }
    }
    public void setPage(int i){
        page=i;
    }
}
