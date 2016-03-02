package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.moimusic.AppApplication;
import com.example.moimusic.R;
import com.example.moimusic.adapter.MusicListReplyAdapter;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.IReplysData;
import com.example.moimusic.mvp.model.biz.MusicListReplysBiz;
import com.example.moimusic.mvp.model.entity.EvenActivityMusicListCall;
import com.example.moimusic.mvp.model.entity.EvenMusicListChangeCall;
import com.example.moimusic.mvp.model.entity.IReply;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.MusicListReply;
import com.example.moimusic.mvp.views.FragmentMuiscListReplysView;
import com.example.moimusic.ui.activity.LogActivity;
import com.example.moimusic.ui.fragment.FragmentMuiscListReplys;
import com.rey.material.app.Dialog;

import java.util.List;

import cn.bmob.v3.BmobUser;
import de.greenrobot.event.EventBus;
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
    private IReplysData iReplysData;
    public FragmentMuiscListReplysPresenter(ApiService apiService) {
        factory = new DataBizFactory();
    }
    public void attach(String id,FragmentMuiscListReplysView view,FragmentActivity activity,IReplysData data){
        this.view = view;
        this.id=id;
        this.activity = activity;
        this.iReplysData=data;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSubscriptions.add(iReplysData.getReplys(page,id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(iReplies -> {
                if (page==1){
                     adapter = new MusicListReplyAdapter(iReplies,activity);
                    view.ShowList(adapter);
                }else{
                    if (adapter!=null)
                    {
                        adapter.addData(iReplies);
                    }

                }
            page++;
        },throwable -> {
            if (throwable.getMessage().toString().equals("没有评论")){
                view.setViewEnable(true);
            }
                }
        ));
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
            mSubscriptions.add(iReplysData.sendComment(s,id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            .subscribe(s1 -> {

            },throwable ->  {
                view.setViewEnable(true);
                view.ShowSnackBar(throwable.getMessage());
            },()->{
                view.setViewEnable(true);
                view.ShowSnackBar(activity.getResources().getString(R.string.sendSuccess));
                if (adapter!=null){
                    adapter.ClearData();
                }
                view.updataList();
            }));
        }
    }
    public void setPage(int i){
        page=i;
    }
    public void onEventMainThread(EvenMusicListChangeCall evenMusicListChangeCall){
        Log.d("Tag","收到消息！！！！！");
        if (adapter!=null){
            adapter.ClearData();
            adapter.notifyDataSetChanged();
        }
        setPage(1);
        id=evenMusicListChangeCall.getMusicId();
        onCreate();
    }
}
