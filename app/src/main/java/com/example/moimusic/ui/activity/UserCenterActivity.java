package com.example.moimusic.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.moimusic.R;
import com.example.moimusic.adapter.FragmentAdapter;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.presenters.UserCenterActivityPresenter;
import com.example.moimusic.mvp.views.IUserCenterActivity;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerMainActivityComponent;
import com.example.moimusic.reject.components.DaggerUserCenterActivityComponent;
import com.example.moimusic.reject.models.MainActivityModule;
import com.example.moimusic.reject.models.UserCenterActivityModule;
import com.example.moimusic.ui.fragment.FragmentAniMusic;
import com.example.moimusic.ui.fragment.FragmentFavouriteMusicList;
import com.example.moimusic.ui.fragment.FragmentGroom;
import com.example.moimusic.ui.fragment.FragmentMusicList;
import com.example.moimusic.ui.fragment.FragmentOriginal;
import com.example.moimusic.ui.fragment.FragmentTrends;
import com.facebook.drawee.view.SimpleDraweeView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by qqq34 on 2016/2/19.
 */
public class UserCenterActivity extends BaseActivity implements IUserCenterActivity{
    private ViewPager viewPager;
    private TabLayout tabs;
    private SimpleDraweeView simpleDraweeView;
    private TextView textName,tvLike,textFollowed;
    @Inject
    UserCenterActivityPresenter userCenterActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_user_center);
        initData();
        initView();
        initClick();
        userCenterActivityPresenter.setView();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserCenterActivityComponent.builder()
                .appComponent(appComponent)
                .userCenterActivityModule(new UserCenterActivityModule(this))
                .build()
                //这里的inject 会把 MainActivity 所有标注了注解的成员 给动态实例化了
                .inject(this);
    }
    private void initData(){
userCenterActivityPresenter.attach(this,this);
    }
    private void initView(){
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        tabs = (TabLayout)findViewById(R.id.tabs);
        setupViewPager();
        viewPager.setOffscreenPageLimit(3);
        simpleDraweeView = (SimpleDraweeView)findViewById(R.id.userImage);
        textName = (TextView)findViewById(R.id.user_center_name);
        tvLike = (TextView)findViewById(R.id.like_text);
        textFollowed = (TextView)findViewById(R.id.followed_text);
    }
    private void initClick(){
        simpleDraweeView.setOnClickListener(v -> userCenterActivityPresenter.startEditActivity());
    }
    private void setupViewPager() {
        if (userCenterActivityPresenter.isCurrentUser()){
            List<String> titles = new ArrayList<>();
            titles.add(getResources().getString(R.string.list_create));
            titles.add(getResources().getString(R.string.list_like));
            tabs.addTab(tabs.newTab().setText(titles.get(0)));
            tabs.addTab(tabs.newTab().setText(titles.get(1)));
            List<Fragment> fragments = new ArrayList<>();
            fragments.add(FragmentMusicList.newInstance(userCenterActivityPresenter.getID()));
            fragments.add(new FragmentFavouriteMusicList());
            FragmentAdapter adapter =
                    new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
            viewPager.setAdapter(adapter);
            tabs.setupWithViewPager(viewPager);
            tabs.setTabsFromPagerAdapter(adapter);
        }else {
            List<String> titles = new ArrayList<>();
            titles.add(getResources().getString(R.string.list_create));
            titles.add(getResources().getString(R.string.trends));
            tabs.addTab(tabs.newTab().setText(titles.get(0)));
            tabs.addTab(tabs.newTab().setText(titles.get(1)));
            List<Fragment> fragments = new ArrayList<>();
            fragments.add(FragmentMusicList.newInstance(userCenterActivityPresenter.getID()));
            fragments.add(new FragmentTrends()); //记得换成好友动态fragment ，现在这个fragment 是主页上用的！
            FragmentAdapter adapter =
                    new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
            viewPager.setAdapter(adapter);
            tabs.setupWithViewPager(viewPager);
            tabs.setTabsFromPagerAdapter(adapter);
        }


    }

    @Override
    public Intent GetIntent() {
       return getIntent();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void ToNextActivity(Intent intent) {
startActivity(intent);
    }

    @Override
    public void updataView(MoiUser moiUser,int like,int followed) {
        Log.d("TAG","在个人中心打印个人类:"+moiUser.getMobilePhoneNumber());
        if (moiUser.getImageUri()!=null&&!moiUser.getImageUri().equals("")){
            simpleDraweeView.setImageURI(Uri.parse(moiUser.getImageUri()));
        }
       if (moiUser.getName()!=null&&!moiUser.getName().equals("")){
           textName.setText(moiUser.getName());
       }else {
           textName.setText("未设置用户名");
       }
        if (followed>99){
            textFollowed.setText("粉丝：99+");
        }else {
            textFollowed.setText("粉丝："+followed);
        }
        if (like>99){
            tvLike.setText("关注：99+");
        }else {
            tvLike.setText("关注："+like);
        }

    }

    @Override
    public void ShowSnackBar(String s) {
        Snackbar.make(viewPager,s,Snackbar.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userCenterActivityPresenter!=null){
            userCenterActivityPresenter.onDestroy();
        }
    }
}
