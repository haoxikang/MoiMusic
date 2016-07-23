package com.example.moimusic.mvp.model.updata;

import java.util.Random;

import cn.bmob.v3.datatype.BmobFile;
import rx.Observable;

/**
 * Created by 康颢曦 on 2016/4/13.
 */
public class MusicUpload implements IFileUpload {

    @Override
    public Observable<Integer> startUpload(BmobFile file) {
        Observable<Integer> observable = Observable.create(subscriber -> {
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i =new Random().nextInt(100);
               subscriber.onNext(i);
            }
        });
        return observable;
    }

    @Override
    public boolean stopUpload(SimpleFile simpleFile) {
        return true;
    }
}
