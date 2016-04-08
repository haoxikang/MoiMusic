package com.example.moimusic.mvp.presenters;

import android.content.Context;

import com.example.moimusic.adapter.SearchMusicAdapter;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.MusicBiz;
import com.example.moimusic.mvp.model.biz.UserBiz;
import com.example.moimusic.mvp.model.entity.EvenSearchCall;
import com.example.moimusic.mvp.views.FragmentSearchSingerView;
import com.example.moimusic.mvp.views.FragmentSearchUserView;

import de.greenrobot.event.EventBus;

/**
 * Created by 康颢曦 on 2016/4/8.
 */
public class FragmentSearchUserPresenter extends BasePresenterImpl {
    private Factory factory;
    private Context context;
    private FragmentSearchUserView view;
    private String s;
    public FragmentSearchUserPresenter(ApiService apiService) {
    }
    public void attach(Context context,FragmentSearchUserView view){
        EventBus.getDefault().register(this);
        this.context = context;
        this.view = view;
        factory = new DataBizFactory();
    }
    public void getSearchUser(){
        if (s!=""){
            UserBiz biz = factory.createBiz(UserBiz.class);
            mSubscriptions.add(biz.SearchUserFromTellPhone(s).subscribe(moiUsers -> {
                if (moiUsers.size()!=0){
                    view.showView(moiUsers.get(0));
                }
                view.showAndHideSwip(false);
            },throwable -> {
                view.showSnackbar(throwable.getMessage());
                view.showAndHideSwip(false);
            }));
        }

    }
    public void onEventMainThread(EvenSearchCall call) {
        if (call.getSearchString()!=null&&call.getSearchString()!=""){
            view.showAndHideSwip(true);
            s = call.getSearchString();
            getSearchUser();
        }
    }
    public void setString(String str){
        s=str;
    }
}
