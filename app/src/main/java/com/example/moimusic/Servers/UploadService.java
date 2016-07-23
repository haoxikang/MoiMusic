package com.example.moimusic.Servers;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.moimusic.mvp.model.updata.FileUploader;
import com.example.moimusic.mvp.model.updata.SimpleFile;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.bmob.v3.datatype.BmobFile;
import de.greenrobot.event.EventBus;

/**
 * Created by 康颢曦 on 2016/4/13.
 */
public class UploadService extends Service {
    private FileUploader fileUploader;
    private ExecutorService ex = Executors.newSingleThreadExecutor();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("上传服务","执行onCreate");
        fileUploader = new FileUploader();
        SimpleFile simpleFile = new SimpleFile(new BmobFile());
        fileUploader.startUploadNewFile(simpleFile);
        SimpleFile file = fileUploader.ShowAllProgress().get(0);
        Runnable statusRunnable = ()-> {
            while (!Thread.interrupted()) {


                try {
                    Thread.sleep(1000);
                    Log.d("上传服务",file.getProgress()+"");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //终结循环
                    Thread.currentThread().interrupt();
                }
            }

        };
        ex.execute(statusRunnable);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        fileUploader.destroyUploader();
        if (ex!=null&&!ex.isShutdown()){
            ex.shutdownNow();
            ex=null;
        }
    }
}
