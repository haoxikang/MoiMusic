package com.example.moimusic.mvp.model.updata;

import android.util.Log;

import com.example.moimusic.utils.RxUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 康颢曦 on 2016/4/13.
 */
public class FileUploader {
    private Hashtable<SimpleFile, Subscription> hashMap;
    private IFileUpload fileUpload;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    public FileUploader() {
        mSubscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(mSubscriptions);
        fileUpload = new MusicUpload();
        hashMap = new Hashtable<>();
    }

    public void setFileUpload(IFileUpload fileUpload) {   //选择上传对象
        this.fileUpload = fileUpload;
    }

    public void startUploadNewFile(SimpleFile simpleFile) {        //开启一个新的上传任务
        if (hashMap.get(simpleFile) != null) {

        } else {
            Subscription subscription = fileUpload.startUpload(simpleFile.getBmobFile())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> {
                        simpleFile.setProgress(integer);
                    }, throwable -> {
                        Log.d("错误",throwable.getMessage());
                        simpleFile.setProgress(-1);

                    });
            mSubscriptions.add(subscription);
            hashMap.put(simpleFile, subscription);
        }

    }

    public void reStartUploadFile(SimpleFile simpleFile) { //重新开始某个上传任务 如果任务不存在将会开启一个新的
        stopUpload(simpleFile);
        startUploadNewFile(simpleFile);

    }

    public void stopUpload(SimpleFile simpleFile) {   //停止某个上传任务
        if (hashMap.get(simpleFile) != null) {
            fileUpload.stopUpload(simpleFile);
            mSubscriptions.remove(hashMap.get(simpleFile));
            hashMap.remove(simpleFile);
        }
    }

    public List<SimpleFile> ShowAllProgress() {  //返回当前上传的所有文件的进度
        Iterator it = hashMap.keySet().iterator();
        List<SimpleFile> simpleFiles = new ArrayList<>();
        while (it.hasNext()) {
            SimpleFile file = (SimpleFile) it.next();
            simpleFiles.add(file);
        }
        return simpleFiles;
    }

    public void destroyUploader() {
        mSubscriptions.unsubscribe();
    }
}
