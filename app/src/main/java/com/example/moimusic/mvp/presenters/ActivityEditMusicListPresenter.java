package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.MusicListBiz;
import com.example.moimusic.mvp.model.entity.EvenMusicListEditCall;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.mvp.views.ActivityEditMusicListView;
import com.example.moimusic.ui.activity.ActivityEditMusicList;
import com.example.moimusic.ui.activity.EditActivity;
import com.example.moimusic.utils.Utils;
import com.soundcloud.android.crop.Crop;

import java.io.File;

import cn.bmob.v3.BmobUser;
import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/9.
 */
public class ActivityEditMusicListPresenter extends BasePresenterImpl {
    private Context context;
    private ActivityEditMusicListView view;
    private Uri imageUri;
    private File file;
    private Factory factory;
private String id;
    public ActivityEditMusicListPresenter(ApiService apiService) {
    }

    public void attach(Context context, ActivityEditMusicListView view) {
        this.context = context;
        this.view = view;
        this.factory = new DataBizFactory();
        id=this.view.GetIntent().getStringExtra("editlistid");
    }

    public void onEditImageClick() {
        Crop.pickImage((ActivityEditMusicList) context);
    }

    public void upData() {
        if (TextUtils.isEmpty(view.getText())) {
            view.ShowError(true);
        } else if (imageUri == null) {
            view.ShowSnackBar("必须设置封面哟");
        } else {
            MusicListBiz biz = factory.createBiz(MusicListBiz.class);
            view.setEnable(false);
            view.showProgress(true);
            mSubscriptions.add(biz.updataMusicList(id, file.getPath(), view.getText().toString()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(musicList -> {
                        view.setEnable(true);
                        view.showProgress(false);
                        view.finish();
                        EventBus.getDefault().post(new EvenMusicListEditCall(musicList));
                    },throwable -> {
                        view.ShowSnackBar(throwable.getMessage());
                        view.setEnable(true);
                        view.showProgress(false);
                    }));
        }
    }

    public void onActivityResult(Intent data, int flag) {
//       Intent intent = new Intent(context, ActivityCrop.class);
//       intent.putExtra("path","file://"+data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT).get(0));
//       view.startActivity(intent);
        if (flag == 1) {
            beginCrop(data.getData());
        } else if (flag == 2) {
            view.showImage(Crop.getOutput(data));
            imageUri = Crop.getOutput(data);
        }

    }

    private void beginCrop(Uri source) {
        file = new File(context.getCacheDir(), "Image" + BmobUser.getCurrentUser(context, MoiUser.class).getObjectId() + System.currentTimeMillis() + ".png");
        Uri destination = Uri.fromFile(file);
        Crop.of(source, destination)
                .asSquare()
                .withMaxSize(500, 500)
                .start((ActivityEditMusicList) context);
    }
}
