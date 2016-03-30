package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.TrendsBiz;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.views.ActivityNewTrendsView;
import com.example.moimusic.ui.activity.ActivityNewTrends;
import com.soundcloud.android.crop.Crop;

import java.io.File;

import cn.bmob.v3.BmobUser;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/3/29.
 */
public class ActivityNewTrendsPresenter extends BasePresenterImpl {
    private Factory factory;
    private Context context;
    private ActivityNewTrendsView view;
    private String ShareType;
    private String name;
    private String singer;
    private String ID;
    private String musicImage;
    private Uri imageUri;
    private File file;
    public ActivityNewTrendsPresenter(ApiService apiService) {
        factory = new DataBizFactory();
    }
    public void attach(ActivityNewTrendsView view, Context context){
        this.context = context;
        this.view =view;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ShareType= ((ActivityNewTrends)context).getIntent().getStringExtra("shareType");
        if (ShareType.equals("文字")){
                view.showBlackShare();
        }else if(ShareType.equals("歌曲")||ShareType.equals("歌单")){
                name= ((ActivityNewTrends)context).getIntent().getStringExtra("shareName");
            singer= ((ActivityNewTrends)context).getIntent().getStringExtra("shareSinger");
            ID= ((ActivityNewTrends)context).getIntent().getStringExtra("ID");
            musicImage= ((ActivityNewTrends)context).getIntent().getStringExtra("musicImage");
            view.showMusicShare(name,singer,musicImage);
        }
    }
    public void onRImageClick() {
        Crop.pickImage((ActivityNewTrends) context);
    }
    public void onActivityResult(Intent data, int flag) {
//       Intent intent = new Intent(context, ActivityCrop.class);
//       intent.putExtra("path","file://"+data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT).get(0));
//       view.startActivity(intent);
        if (flag == 1) {
            beginCrop(data.getData());
        } else if (flag == 2) {
            if (Crop.getOutput(data)!=null){
                ShareType = "图片";
                view.ShowImage(Crop.getOutput(data));
                imageUri = Crop.getOutput(data);
            }

        }

    }
    private void beginCrop(Uri source) {
        file = new File(context.getCacheDir(), "cropped" + BmobUser.getCurrentUser(context, MoiUser.class).getObjectId() + System.currentTimeMillis() + ".png");
        Uri destination = Uri.fromFile(file);
        Crop.of(source, destination)
                .asSquare()
                .withMaxSize(800, 800)
                .start((ActivityNewTrends) context);
    }
    public void onDeleteLayoutClick(){
        view.showBlackShare();
        ShareType = "文字";
    }
    public void onCheckClick(){
        if (!TextUtils.isEmpty(view.getEditText())){
            TrendsBiz biz = factory.createBiz(TrendsBiz.class);
            view.showProgress(true);
            mSubscriptions.add(biz.updataTrends(ShareType,view.getEditText(),ID,file).subscribe(trends -> {
                EventBus.getDefault().post("动态发布成功");
                view.finish();
            },throwable -> {
                view.showSnackbar(throwable.getMessage());
                view.showProgress(false);
            }));
        }else {
            view.showSnackbar("内容不能为空");

        }

    }
}
