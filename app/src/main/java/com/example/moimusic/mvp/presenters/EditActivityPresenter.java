package com.example.moimusic.mvp.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.UserBiz;
import com.example.moimusic.mvp.model.entity.EvenUserCall;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.views.EditAcitivityView;
import com.example.moimusic.ui.activity.EditActivity;
import com.example.moimusic.utils.Utils;
import com.soundcloud.android.crop.Crop;

import java.io.File;

import cn.bmob.v3.BmobUser;
import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/3/1.
 */
public class EditActivityPresenter extends BasePresenterImpl {
    private Context context;
    private String id;
    private Factory factory;
    private EditAcitivityView view;
    public int PICK_PHOTO = 1;
    private Uri imageUri;
    private File file;

    public EditActivityPresenter(ApiService apiService) {
        factory = new DataBizFactory();
    }

    public void attach(Context context, EditAcitivityView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = view.getIntent();
        String image = "";
        String name = "";
        String introduce = "";
        String Sex="男";
        if (intent.getStringExtra("userImage") != null) {
            image = intent.getStringExtra("userImage");
        }
        if (intent.getStringExtra("userName") != null) {
            name = intent.getStringExtra("userName");
        }
        if (intent.getStringExtra("userIntroduce") != null) {
            introduce = intent.getStringExtra("userIntroduce");
        }
        if (intent.getStringExtra("userSex") != null) {
            Sex = intent.getStringExtra("userSex");
        }
        view.setView(image,name,introduce,Sex);
        view.setViewEnable(intent.getBooleanExtra("isCurrent", false));

    }

    public void onBackClick() {
        view.finish();
    }

    public void onItemClick() {
        Intent intent = view.getIntent();
        if (intent.getBooleanExtra("isCurrent", false)) {
            updata();
        } else {
            view.finish();
        }
    }

    public void onPhotoPickerClick() {
//        Intent intent = new Intent(context, PhotoPickerActivity.class);
//        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
//        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
//        view.startActivityForResult(intent, PICK_PHOTO);
        Crop.pickImage((EditActivity) context);
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
        file = new File(context.getCacheDir(), "cropped" + BmobUser.getCurrentUser(context, MoiUser.class).getObjectId() + System.currentTimeMillis() + ".png");
        Uri destination = Uri.fromFile(file);
        Crop.of(source, destination)
                .asSquare()
                .withMaxSize(500, 500)
                .start((EditActivity) context);
    }

    public void updata() {
        view.setViewEnable(false);
        view.showProgress(true);
        String[] data = new String[4];
        for (int i = 0; i < 4; i++) {
            if (3 != i) {
                data[i] = view.getData()[i];
            } else {
                if (imageUri != null) {
                    Log.d("TAG", "文件大小" + file.length());
                    data[i] = file.getPath();
                }

            }

        }
        UserBiz userBiz = factory.createBiz(UserBiz.class);
        mSubscriptions.add(userBiz.updata(data).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moiUser -> {
                    EventBus.getDefault().post(new EvenUserCall());
                    view.finish();
                }, throwable -> {
                    view.setViewEnable(true);
                    view.showProgress(false);
                    view.showSnackBar(throwable.getMessage());
                }));
    }
}
