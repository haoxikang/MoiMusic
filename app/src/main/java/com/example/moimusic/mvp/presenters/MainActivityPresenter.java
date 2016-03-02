package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moimusic.R;
import com.example.moimusic.adapter.UnderMusicListAdapter;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.MusicBiz;
import com.example.moimusic.mvp.model.biz.MusicListBiz;
import com.example.moimusic.mvp.model.entity.EvenCall;
import com.example.moimusic.mvp.model.entity.EvenReCall;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.views.IMainView;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.ui.activity.LogActivity;
import com.example.moimusic.ui.activity.UserCenterActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rey.material.app.BottomSheetDialog;

import java.io.IOException;

import cn.bmob.v3.BmobUser;
import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/1/28.
 */
public class MainActivityPresenter extends BasePresenterImpl{
    private ApiService api;
    private IMainView mView;
    private MoiUser user;
    private Factory factory;
    private Context context;
    private PlayListSingleton playListSingleton;

    public MainActivityPresenter(ApiService apiService) {
        playListSingleton = PlayListSingleton.INSTANCE;
        factory = new DataBizFactory();
        this.api = apiService;
    }
    public void attach(IMainView iMainView, Context context){
        mView = iMainView;
        EventBus.getDefault().register(this);
        this.context = context;

    }

    public void getMusicList(){
        MusicBiz musicBiz   = factory.createBiz(MusicBiz.class);
        mSubscriptions.add(musicBiz.getMusics().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(musicList -> {
                    Log.d("TAGList",musicList.get(0).toString());
                        playListSingleton.setMusicList(musicList);
                    mView.updataPlayView();
                    mView.setPlayViewEnable(true);
                },throwable -> {
                    Log.d("TAGerror",throwable.getMessage());
                }));

    }
    public void musicPlay(){
        if (playListSingleton.isUnderPlay){
            EvenCall evenCall = new EvenCall();
            evenCall.setCurrentOrder(EvenCall.PAUSE);
            EventBus.getDefault().post(evenCall);
            mView.setPlayButton(false);
        }else {

            EvenCall evenCall = new EvenCall();
            evenCall.setCurrentOrder(EvenCall.START);
            EventBus.getDefault().post(evenCall);
            mView.setPlayButton(true);
        }

    }
    public void nextMusic(){
        EvenCall evenCall = new EvenCall();
        evenCall.setCurrentOrder(EvenCall.NEXT);
        EventBus.getDefault().post(evenCall);
        mView.updataPlayView();
        mView.setPlayButton(true);
        mView.setProgressbar(0,0);
    }
    public void prevMusic(){
        EvenCall evenCall = new EvenCall();
        evenCall.setCurrentOrder(EvenCall.PRE);
        EventBus.getDefault().post(evenCall);
        mView.updataPlayView();
        mView.setPlayButton(true);
        mView.setProgressbar(0,0);
    }
   public void onEventMainThread(EvenReCall evenReCall){
       mView.updataPlayView();
       if (evenReCall.getMusicSize()!=0){
           playListSingleton.setAll(evenReCall.getMusicSize());
           playListSingleton.setNow(evenReCall.getCurrent());
       }
       if (evenReCall.getCurrent()!=0&&evenReCall.getMusicSize()!=0){
           mView.setProgressbar(evenReCall.getCurrent(),evenReCall.getMusicSize());
       }

   }
    public void onEventMainThread(Integer integer){
        getMusicList();

    }
    public void onEventMainThread(String s){
        mView.ShowSnackbar(s);

    }
    public void logButtonClick(){
        if (BmobUser.getCurrentUser(context,MoiUser.class)!=null){
            Intent intent = new Intent(context,UserCenterActivity.class);
            intent.putExtra("userID",BmobUser.getCurrentUser(context,MoiUser.class).getObjectId());
            mView.startNextActivity(intent);
        }else {
            mView.startNextActivity(new Intent(context,LogActivity.class));
        }

    }
    public void ShowMusicList() {
         BottomSheetDialog mDialog = new BottomSheetDialog(context);
            View view = LayoutInflater.from(context).inflate(R.layout.under_music_list,null);
            RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.under_musiclist);
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView)view.findViewById(R.id.music_list_image);
            TextView textView = (TextView)view.findViewById(R.id.music_list_title);
            textView.setText("播放队列（"+playListSingleton.getMusicList().size()+"）");
            UnderMusicListAdapter underMusicListAdapter = new UnderMusicListAdapter();
        underMusicListAdapter.setOnItemClickLitener(new UnderMusicListAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
              Log.d("TAG","点击recy"+position);
                if (position!=playListSingleton.getCurrentPosition()){
                    playListSingleton.setCurrentPosition(position);
                    EvenCall evenCall = new EvenCall();
                    evenCall.setCurrentOrder(EvenCall.PLAY);
                    EventBus.getDefault().post(evenCall);
                    mView.updataPlayView();
                    mView.setPlayButton(true);
                    mView.setProgressbar(0,0);
                    underMusicListAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(underMusicListAdapter);
            mDialog.contentView(view)
                    .heightParam(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .inDuration(500)
                    .cancelable(true)
                    .show();

        }
    public void saveData(){
            playListSingleton.saveList();
        playListSingleton.saveCurrent();

    }
    public void initUnderMusicList(){
        if (playListSingleton.LoadList()!=null){
            if (playListSingleton.getMusicList().size()==0){
                playListSingleton.setMusicList(playListSingleton.LoadList());
                playListSingleton.setCurrentPosition(0);
            }
            if (playListSingleton.loadCurrent()!=null&&!playListSingleton.loadCurrent().equals("")){
                playListSingleton.setCurrentPosition(Integer.parseInt(playListSingleton.loadCurrent()));
            }
            if (playListSingleton.getMusicList().size()!=0){
                mView.updataPlayView();
                mView.setPlayViewEnable(true);
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}