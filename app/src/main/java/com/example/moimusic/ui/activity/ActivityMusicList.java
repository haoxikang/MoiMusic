package com.example.moimusic.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moimusic.R;
import com.example.moimusic.adapter.FragmentAdapter;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.mvp.model.biz.MusicListReplysBiz;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.mvp.presenters.ActivityMusicListPresenter;
import com.example.moimusic.mvp.views.IMusicListView;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerActivityMusicListComponent;
import com.example.moimusic.reject.models.ActivityMusicListModule;
import com.example.moimusic.ui.fragment.FragmentMuiscListReplys;
import com.example.moimusic.ui.fragment.MusicListContentFragment;
import com.example.moimusic.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by 康颢曦 on 2016/2/22.
 */
public class ActivityMusicList extends BaseActivity implements IMusicListView {
    private Toolbar mToolbar;
    private ViewPager viewPager;
    private TabLayout tabs;
    private SimpleDraweeView UserView, CoverView;
    private TextView textName, textuser;
    private RelativeLayout back;
    private FloatingActionButton floatingActionButton;
    private MenuItem like, release, edit,share;
    @Inject
    ActivityMusicListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_music_list_content);
        presenter.attach(this, this);
        initView();
        initClick();
        presenter.onCreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music_list_content, menu);
        like = menu.findItem(R.id.favorite);
        edit = menu.findItem(R.id.edit);
        release = menu.findItem(R.id.release);
        share = menu.findItem(R.id.share);
        share.setEnabled(false);
        like.setEnabled(false);
        edit.setEnabled(false);
        release.setEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        presenter.menuClick(item);
        return super.onOptionsItemSelected(item);
    }

    private void initClick() {
        mToolbar.setNavigationOnClickListener(v -> presenter.finishActivity());
        floatingActionButton.setOnClickListener(v -> presenter.floatClick());
        UserView.setOnClickListener(v -> presenter.ImageViewClicked());
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabs = (TabLayout) findViewById(R.id.tabs);
        UserView = (SimpleDraweeView) findViewById(R.id.userImage);
        UserView.setEnabled(false);
        CoverView = (SimpleDraweeView) findViewById(R.id.coverImage);
        textName = (TextView) findViewById(R.id.textTitle);
        textuser = (TextView) findViewById(R.id.textUser);
        back = (RelativeLayout) findViewById(R.id.center_area);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.actionButon);
        floatingActionButton.setEnabled(false);

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerActivityMusicListComponent.builder()
                .appComponent(appComponent)
                .activityMusicListModule(new ActivityMusicListModule(this))
                .build()
                //这里的inject 会把 MainActivity 所有标注了注解的成员 给动态实例化了
                .inject(this);
    }

    @Override
    public void setupViewPager(List<Music> musicList) {
        List<String> titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.music));
        titles.add(getResources().getString(R.string.reply));
        tabs.addTab(tabs.newTab().setText(titles.get(0)));
        tabs.addTab(tabs.newTab().setText(titles.get(1)));
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MusicListContentFragment.newInstance(musicList, "ActivityMusicList"));
        fragments.add(FragmentMuiscListReplys.newInstance(new DataBizFactory().createBiz(MusicListReplysBiz.class), presenter.getId(),true));
        FragmentAdapter adapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabsFromPagerAdapter(adapter);
        like.setEnabled(true);
        edit.setEnabled(true);
        release.setEnabled(true);
        share.setEnabled(true);


    }

    @Override
    public void ShowSnackBar(String s) {
        Snackbar.make(tabs, s, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void UpdataImageAndName(MusicList musicList) {
        if (musicList.getListImageUri() != null && !musicList.getListImageUri().equals("")) {

            Uri uri = Uri.parse(musicList.getListImageUri());
            Utils.reSizeImage(400,350,uri,CoverView);

        }

        if (musicList.getName() != null) {
            textName.setText(musicList.getName());
        }
    }


    @Override
    public Intent GetIntent() {
        return getIntent();
    }

    @Override
    public void showView(MusicList musicList) {
        UserView.setEnabled(true);
        if (musicList.getListImageUri() != null && !musicList.getListImageUri().equals("")) {
            Uri uri = Uri.parse(musicList.getListImageUri());
            Utils.reSizeImage(400,350,uri,CoverView);

        }

        if (musicList.getName() != null) {
            textName.setText(musicList.getName());
        }
        if (musicList.getMoiUser().getImageFile().getFileUrl(this)!=null){
            Uri uri = Uri.parse(musicList.getMoiUser().getImageFile().getFileUrl(this));
            Utils.reSizeImage(150,150,uri,UserView);
        }


        if (musicList.getMoiUser().getName() != null) {
            textuser.setText(musicList.getMoiUser().getName());
        }
    }

    @Override
    public void SetPlayButtonEnable(boolean isEnable) {
        floatingActionButton.setEnabled(isEnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

}
