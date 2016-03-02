package com.example.moimusic.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.moimusic.R;
import com.example.moimusic.mvp.presenters.EditActivityPresenter;
import com.example.moimusic.mvp.views.EditAcitivityView;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerEditActivityComponent;

import com.example.moimusic.reject.models.EditActivityModule;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gc.materialdesign.views.ButtonFlat;
import com.rey.material.widget.RadioButton;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by qqq34 on 2016/3/1.
 */
public class EditActivity extends BaseActivity implements EditAcitivityView {
    @Inject
    EditActivityPresenter presenter;
    private SimpleDraweeView simpleDraweeView;
    private ButtonFlat buttonFlat;
    private TextInputLayout name, introduce;
    private RadioButton boy, girls;
    private Toolbar mToolbar;
    private MenuItem item;
    private Uri mDestinationUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activit_edit);
        mDestinationUri = Uri.fromFile(new File(getCacheDir(), "crop.jpeg"));
        initView();
        initClick();
        presenter.attach(this,this);
        presenter.onCreate();

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerEditActivityComponent.builder()
                .appComponent(appComponent)
                .editActivityModule(new EditActivityModule(this))
                .build()
                //这里的inject 会把 MainActivity 所有标注了注解的成员 给动态实例化了
                .inject(this);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    simpleDraweeView = (SimpleDraweeView)findViewById(R.id.userImage);
        buttonFlat = (ButtonFlat)findViewById(R.id.editImage);
        name = (TextInputLayout)findViewById(R.id.userName);
        introduce=(TextInputLayout)findViewById(R.id.userIntroduce);
        boy = (RadioButton)findViewById(R.id.boyRB);
        girls = (RadioButton)findViewById(R.id.girlRB);
    }
private void initClick(){
    mToolbar.setNavigationOnClickListener(v -> presenter.onBackClick());
    buttonFlat.setOnClickListener(v -> presenter.onPhotoPickerClick());
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        item = menu.findItem(R.id.check);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.check: {
                presenter.onItemClick();
                break;
            }


        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    @Override
    public void setViewEnable(boolean isEnable) {
        simpleDraweeView .setEnabled(isEnable);
        buttonFlat .setEnabled(isEnable);
        name.getEditText()  .setEnabled(isEnable);
        introduce.getEditText() .setEnabled(isEnable);
        boy  .setEnabled(isEnable);
        girls  .setEnabled(isEnable);

    }

    @Override
    public void showImage(Uri uri) {
        simpleDraweeView.setImageURI(uri);
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
