package com.example.moimusic.mvp.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.views.EditAcitivityView;
import com.example.moimusic.ui.activity.EditActivity;
import com.lling.photopicker.PhotoPickerActivity;
import com.soundcloud.android.crop.Crop;

import java.io.File;

/**
 * Created by qqq34 on 2016/3/1.
 */
public class EditActivityPresenter extends BasePresenterImpl {
    private Context context;
    private String id;
    private EditAcitivityView view;
    public int PICK_PHOTO = 1;

    public EditActivityPresenter(ApiService apiService) {
    }

    public void attach(Context context, EditAcitivityView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = view.getIntent();
        view.setViewEnable(intent.getBooleanExtra("isCurrent", false));

    }

    public void onBackClick() {
        view.finish();
    }

    public void onItemClick() {
        Intent intent = view.getIntent();
        if (intent.getBooleanExtra("isCurrent", false)) {

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
        }

    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(context.getCacheDir(), "cropped"+System.currentTimeMillis()));
        Crop.of(source, destination)
                .asSquare()
                .start((EditActivity) context);
    }
}
