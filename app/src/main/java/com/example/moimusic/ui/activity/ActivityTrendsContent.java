package com.example.moimusic.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moimusic.R;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.mvp.model.biz.TrendsReplysBiz;
import com.example.moimusic.mvp.model.entity.Trends;
import com.example.moimusic.mvp.presenters.ActivityTrendsContentPresenter;
import com.example.moimusic.mvp.views.ActivityTrendsContentView;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerActivityTrendsContentComponent;
import com.example.moimusic.reject.models.ActivityTrendsContentModule;
import com.example.moimusic.ui.fragment.FragmentMuiscListReplys;
import com.facebook.drawee.view.SimpleDraweeView;
import com.vanniktech.emoji.EmojiTextView;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/3/31.
 */
public class ActivityTrendsContent extends BaseActivity implements ActivityTrendsContentView {
    protected TextView name, time, replysNumber;
    protected EmojiTextView content;
    protected SimpleDraweeView userImage;
    protected LinearLayout linearLayout;
    private TextView ListName, ListSinger;
    private SimpleDraweeView ListImage;
    private RelativeLayout areaMusic;
    @Inject
    ActivityTrendsContentPresenter presenter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_trends_content_layout);
        initview();
        initClick();
        presenter.attach(this,this);
        presenter.onCreate();

    }
    private void initClick(){
        userImage.setOnClickListener(v1 -> presenter.onUserImageClick());

    }
    private void initview(){

        areaMusic = (RelativeLayout)findViewById(R.id.areaMusic);
        ListName = (TextView) findViewById(R.id.musicName);
        ListSinger = (TextView)findViewById(R.id.musicSinger);
        ListImage = (SimpleDraweeView) findViewById(R.id.musicImage);
        name = (TextView) findViewById(R.id.name);
        time = (TextView) findViewById(R.id.time);
        content = (EmojiTextView) findViewById(R.id.content);
        replysNumber = (TextView) findViewById(R.id.replysNum);
        userImage = (SimpleDraweeView) findViewById(R.id.userImage);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
    }
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerActivityTrendsContentComponent.builder()
                .appComponent(appComponent)
                .activityTrendsContentModule(new ActivityTrendsContentModule(this))
                .build()
                //这里的inject 会把 MainActivity 所有标注了注解的成员 给动态实例化了
                .inject(this);
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onDestroy();
        }
    }

    @Override
    public void ShowView(Trends trends) {
        name.setText(trends.getUserid().getName());
        time.setText(trends.getCreatedAt());
        content.setText(trends.getContent());

        if (trends.getUserid().getImageFile() != null) {
            userImage.setImageURI(Uri.parse(trends.getUserid().getImageFile().getFileUrl(this)));
        }else {
            userImage.setImageURI(Uri.parse("null"));
        }

        if (trends.getReplysNum() != null) {
            replysNumber.setText(trends.getReplysNum() + "");
        } else {
            replysNumber.setText(0 + "");
        }
        ListName.setVisibility(View.GONE);
        ListSinger.setVisibility(View.GONE);
        ListImage.setVisibility(View.GONE);
        areaMusic.setVisibility(View.GONE);
        if (trends.getType().equals("1")){

        }else if (trends.getType().equals("2")){
            ListImage.setVisibility(View.VISIBLE);
            ListImage.setImageURI(Uri.parse(trends.getImage().getFileUrl(this)));
        }else if (trends.getType().equals("3")){
            ListName.setVisibility(View.VISIBLE);
            ListSinger.setVisibility(View.VISIBLE);
            ListImage.setVisibility(View.VISIBLE);
            areaMusic.setVisibility(View.VISIBLE);
            ListImage.setImageURI(Uri.parse(trends.getSongid().getMusicImageUri()));
            ListName.setText(trends.getSongid().getMusicName());
            ListSinger.setText(trends.getSongid().getSinger());
        }else if (trends.getType().equals("4")){
            ListName.setVisibility(View.VISIBLE);
            ListSinger.setVisibility(View.VISIBLE);
            ListImage.setVisibility(View.VISIBLE);
            areaMusic.setVisibility(View.VISIBLE);
            if (trends.getListid().getListImageUri() != null) {
              ListImage.setImageURI(Uri.parse(trends.getListid().getListImageUri()));
            }

      ListName.setText(trends.getListid().getName());
     ListSinger.setText(trends.getListid().getMoiUser().getName());
        }
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment==null){
            fragment = FragmentMuiscListReplys.newInstance(new DataBizFactory().createBiz(TrendsReplysBiz.class), trends.getObjectId(),true);
            fm.beginTransaction()
                    .add(R.id.fragmentContainer,fragment)
                    .commit();
        }
    }


}
