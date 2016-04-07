package com.example.moimusic.mvp.presenters;

import android.content.Context;

import com.example.moimusic.adapter.SearchMusicAdapter;
import com.example.moimusic.adapter.SearchMusicListAdapter;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.MusicBiz;
import com.example.moimusic.mvp.model.biz.MusicListBiz;
import com.example.moimusic.mvp.model.entity.EvenSearchCall;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.mvp.views.FragmentSearchAniView;
import com.example.moimusic.mvp.views.FragmentSearchMusicListView;

import de.greenrobot.event.EventBus;

/**
 * Created by 康颢曦 on 2016/4/7.
 */
public class FragmentSearchMusicListPresenter extends BasePresenterImpl{
    private SearchMusicListAdapter adapter;
    private Factory factory;
    private Context context;
    private FragmentSearchMusicListView view;
    private String s;
    private int page  =1;
    public FragmentSearchMusicListPresenter(ApiService apiService) {

    }
    public void attach(Context context,FragmentSearchMusicListView view){
        EventBus.getDefault().register(this);
        this.context = context;
        this.view = view;
        factory = new DataBizFactory();
    }
    public void getSearchList(){
        if (s!=""){
            MusicListBiz  biz= factory.createBiz(MusicListBiz.class);
            mSubscriptions.add(biz.searchMusicList(s,page).subscribe(musicLists -> {
                if (page==1){
                    adapter = new SearchMusicListAdapter(s,musicLists);
                    view.showList(adapter);
                }else {
                    adapter.addAndUpData(musicLists);
                }
                page++;
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
            page=1;
            s = call.getSearchString();
            getSearchList();
        }

    }
    public void setString(String str){
        s=str;
    }
}
