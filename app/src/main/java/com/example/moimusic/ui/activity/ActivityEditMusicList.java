package com.example.moimusic.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.a.a.a.V;
import com.balysv.materialripple.MaterialRippleLayout;
import com.example.moimusic.R;
import com.example.moimusic.mvp.presenters.ActivityEditMusicListPresenter;
import com.example.moimusic.mvp.views.ActivityEditMusicListView;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerActivityEditMusicListComponent;
import com.example.moimusic.reject.components.DaggerEditActivityComponent;
import com.example.moimusic.reject.models.ActivityEditMusicListModule;
import com.example.moimusic.reject.models.EditActivityModule;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rey.material.widget.ProgressView;
import com.soundcloud.android.crop.Crop;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/3/9.
 */
public class ActivityEditMusicList extends BaseActivity implements ActivityEditMusicListView{
    private MaterialRippleLayout ripple;
    private Toolbar mToolbar;
    private ProgressView progressView;
    private TextInputLayout textInputLayout;
    private SimpleDraweeView simpleDraweeView;
@Inject
    ActivityEditMusicListPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_edit_music_list);
        presenter.attach(this,this);
        initView();
        initClick();
    }

    private void initClick() {
        ripple.setOnClickListener(v -> presenter.onEditImageClick());
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("修改封面信息");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ripple = (MaterialRippleLayout) findViewById(R.id.rippleimage);
        progressView = (ProgressView)findViewById(R.id.progress);
        progressView.setVisibility(View.INVISIBLE);
        textInputLayout = (TextInputLayout)findViewById(R.id.textInput);
        simpleDraweeView = (SimpleDraweeView)findViewById(R.id.image);

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerActivityEditMusicListComponent.builder()
                .appComponent(appComponent)
                .activityEditMusicListModule(new ActivityEditMusicListModule(this))
                .build()
                //这里的inject 会把 MainActivity 所有标注了注解的成员 给动态实例化了
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.check: {
                presenter.upData();
                break;
            }


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onDestroy();
        }
    }

    @Override
    public void showImage(Uri uri) {
simpleDraweeView.setImageURI(uri);
    }

    @Override
    public void showProgress(boolean isShow) {
if (isShow){
    progressView.setVisibility(View.VISIBLE);
}else {
    progressView.setVisibility(View.INVISIBLE);
}
    }

    @Override
    public void setEnable(boolean isEnable) {
textInputLayout.getEditText().setEnabled(isEnable);
        ripple.setEnabled(isEnable);
    }

    @Override
    public Editable getText() {
        return textInputLayout.getEditText().getText();
    }

    @Override
    public void ShowError(boolean isError) {
            textInputLayout.setErrorEnabled(isError);
            textInputLayout.setError("名字不能为空");

    }

    @Override
    public void ShowSnackBar(String s) {
        Snackbar.make(progressView,s,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public Intent GetIntent() {
        return getIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            presenter.onActivityResult(data,1);
        } else if (requestCode == Crop.REQUEST_CROP) {
            presenter.onActivityResult(data,2);
        }
    }
}
