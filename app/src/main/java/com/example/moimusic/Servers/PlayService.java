package com.example.moimusic.Servers;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.example.moimusic.mvp.model.entity.EvenCall;
import com.example.moimusic.mvp.model.entity.EvenReCall;
import com.example.moimusic.play.PlayListSingleton;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.greenrobot.event.EventBus;

public class PlayService extends Service implements MediaPlayer.OnErrorListener,MediaPlayer.OnPreparedListener{
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private PlayListSingleton playListSingleton;
    private ExecutorService ex = Executors.newSingleThreadExecutor();
    private EvenReCall evenReCall;
    public PlayService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        audioManager.abandonAudioFocus(afChangeListener);
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                playListSingleton.isPlay = false;
                playListSingleton.isUnderPlay =false;
        }
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (ex!=null&&!ex.isShutdown()){
ex.shutdown();
            ex=null;
        }
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        // 如果被回收了重启服务
            startService(new Intent(this, PlayService.class));
        }


    @Override
    public void onCreate() {
        evenReCall = new EvenReCall();
        super.onCreate();
        EventBus.getDefault().register(this);
        playListSingleton = PlayListSingleton.INSTANCE;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        ex.execute(statusRunnable);
    }
    private void play(){
            if (playListSingleton.getCurrentMusic()!=null){
                try {
                    if (mediaPlayer!=null){
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(this, Uri.parse(playListSingleton.getCurrentMusic()));
                        mediaPlayer.prepareAsync();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("TAG","播放失败可能是播放链接有问题");  //播放失败
                }
            }


    }
    private void pause(){
        if (mediaPlayer!=null&&playListSingleton.isPlay&&mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            playListSingleton.isUnderPlay=false;
            EventBus.getDefault().post(new EvenReCall());
        }else if (!playListSingleton.isPlay){
            stop();
            Log.d("TAG","进入暂停的 stop方法");
        }else {
            Log.d("TAG","进入暂停的 其他方法");
        }

    }
    private void next(){
        playListSingleton.next();
        play();
    }
    private void prev(){
        playListSingleton.previous();
        play();
    }
    private void start(){
        if (mediaPlayer!=null&&!mediaPlayer.isPlaying()&&playListSingleton.isPlay){
            mediaPlayer.start();
            playListSingleton.isUnderPlay=true;
            EventBus.getDefault().post(new EvenReCall());
        }else if (!playListSingleton.isPlay){
            play();
        }
    }
    public void stop() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            playListSingleton.isPlay = false;
            playListSingleton.isUnderPlay=false;
            EventBus.getDefault().post(new EvenReCall());
        }
    }
    public void stopService(){
        stopSelf();
    }
    public void onEventMainThread(EvenCall event) {
        Log.d("TAG","收到消息"+event.getCurrentOrder());
        switch (event.getCurrentOrder()){
            case EvenCall.PLAY:{
                play();
                break;
            }
            case EvenCall.NEXT:{
                next();
                break;
            }
            case EvenCall.START:{
                start();
                break;
            }
            case EvenCall.PAUSE:{
                pause();
                break;
            }
            case EvenCall.PRE:{
                prev();
                break;
            }
        }
    }
    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            /**
             * AUDIOFOCUS_GAIN：获得音频焦点。
             * AUDIOFOCUS_LOSS：失去音频焦点，并且会持续很长时间。这是我们需要停止MediaPlayer的播放。
             * AUDIOFOCUS_LOSS_TRANSIENT
             * ：失去音频焦点，但并不会持续很长时间，需要暂停MediaPlayer的播放，等待重新获得音频焦点。
             * AUDIOFOCUS_REQUEST_GRANTED 永久获取媒体焦点（播放音乐）
             * AUDIOFOCUS_GAIN_TRANSIENT 暂时获取焦点 适用于短暂的音频
             * AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK Duck我们应用跟其他应用共用焦点
             * 我们播放的时候其他音频会降低音量
             */
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                // Toast.makeText(context, "AUDIOFOCUS_LOSS_TRANSIENT",
                // Toast.LENGTH_LONG).show();
                stop();

            }  else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Toast.makeText(context, "AUDIOFOCUS_LOSS", Toast.LENGTH_LONG)
                // .show();
                // audioManager.abandonAudioFocus(afChangeListener);
                stop();
            }
            // else if (focusChange == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
            // {
            // logger.i("AUDIOFOCUS_REQUEST_GRANTED");
            // Toast.makeText(context, "AUDIOFOCUS_REQUEST_GRANTED",
            // Toast.LENGTH_LONG).show();
            // play();
            //
            // }
            else {
                // Toast.makeText(context, "focusChange:" + focusChange,
                // Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        playListSingleton.isUnderPlay=false;
        playListSingleton.isPlay=false;
        EventBus.getDefault().post(new EvenReCall());
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        int result = audioManager.requestAudioFocus(afChangeListener,
                // 指定所使用的音频流
                AudioManager.STREAM_MUSIC,
                // 请求长时间的音频焦点
                AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mp.start();
                playListSingleton.isPlay= true;
                playListSingleton.isUnderPlay=true;

            EventBus.getDefault().post(new EvenReCall());
        } else {
            Log.d("TAG","播放失败可能是没有获取到焦点");  //播放失败
            EventBus.getDefault().post(new EvenReCall());
        }
    }
    Runnable statusRunnable = ()->{
        while (true){
            if (mediaPlayer!=null&&mediaPlayer.isPlaying()){
                evenReCall.setMusicSize(mediaPlayer.getDuration());
                evenReCall.setCurrent(mediaPlayer.getCurrentPosition());
                EventBus.getDefault().post(evenReCall);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    private class MyThread extends Thread {
        public void run() {
            evenReCall.setMusicSize(mediaPlayer.getDuration());
            evenReCall.setCurrent(mediaPlayer.getCurrentPosition());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
