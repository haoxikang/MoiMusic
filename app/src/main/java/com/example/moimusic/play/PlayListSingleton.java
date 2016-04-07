package com.example.moimusic.play;

import android.content.Context;
import android.util.Log;

import com.example.moimusic.AppApplication;
import com.example.moimusic.mvp.model.entity.Music;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qqq34 on 2016/2/2.
 */
public enum  PlayListSingleton implements Serializable {
    INSTANCE;
    private List<Music> musicList;
    private APlaySequence aPlaySequence = new ListLoop();
    private int currentPosition=0;
    public boolean isPlay=false; //是否已经开始播放（暂停也算播放）
    public boolean isUnderPlay =false ; //是否放出了声音（暂停 为false）

    private int now;
    private int all;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public int getNow() {
        return now;
    }

    public void setNow(int now) {
        this.now = now;
    }
    public void initPlayList( ){
        this.musicList = new ArrayList<>();
        this.currentPosition= 0;
    }
    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public List<Music> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }

    public void setaPlaySequence(APlaySequence aPlaySequence) {
        this.aPlaySequence = aPlaySequence;
    }
    public void next(){
        if (musicList.size()!=0){
            currentPosition=aPlaySequence.nextMusic(currentPosition,musicList);
        }


    }
    public void previous(){
        if (musicList.size()!=0){
            currentPosition=aPlaySequence.previous(currentPosition,musicList);
        }

    }
    public void  initList(){
        if (musicList.size()!=0){
            musicList=aPlaySequence.initList(musicList);
        }
    }
    public String getCurrentMusicId(){
        if (musicList.size()!=0){
            return   musicList.get(currentPosition).getObjectId();
        }else {
            return null;
        }
    }
    public String getCurrentMusic(){
        if (musicList.size()!=0){
          return   musicList.get(currentPosition).getMusicUri();
        }else {
            return null;
        }
    }
    public Music getCurrent(){
        if (musicList.size()!=0){
            return   musicList.get(currentPosition);
        }else {
            return null;
        }
    }
    public boolean saveList()  {
        Gson gson = new Gson();
        String str = gson.toJson(musicList);
        if (str!=null&&!str.equals("")){
            Writer writer = null;
            try {
                OutputStream outputStream = AppApplication.context.openFileOutput("musicList", Context.MODE_PRIVATE);
                writer = new OutputStreamWriter(outputStream);
                writer.write(str);
                Log.d("储存","储存成功"+str);
                return true;
            }catch (Exception e){
                return false;
            }finally {
                if (writer!=null){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else
            return false;
    }
    public List<Music> LoadList()  {
        List<Music> list = new ArrayList<>();
        BufferedReader reader = null;
        try {
            InputStream in = AppApplication.context.openFileInput("musicList");
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder json = new StringBuilder();
            String line = null;
            while ((line=reader.readLine())!=null){
                json.append(line);
            }
            Gson gson = new Gson();
            Type listType=new TypeToken<ArrayList<Music>>(){}.getType();
            List<Music> ps = gson.fromJson(json.toString(), listType);
            return ps;
        }catch (Exception e){
            return null;
        }finally {
            if (reader!=null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }
    public boolean saveCurrent(){
        Writer writer = null;
        try {
            OutputStream outputStream = AppApplication.context.openFileOutput("currentNum", Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(outputStream);
            writer.write(String.valueOf(currentPosition));
            Log.d("储存","储存成功"+ String.valueOf(currentPosition));
            return true;
        }catch (Exception e){
            return false;
        }finally {
            if (writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String loadCurrent(){
        BufferedReader reader = null;
        try {
            InputStream in = AppApplication.context.openFileInput("currentNum");
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder json = new StringBuilder();
            String line = null;
            while ((line=reader.readLine())!=null){
                json.append(line);
            }
            Log.d("储存","读取当前序号"+json.toString());
            return json.toString();
        }catch (Exception e){
            return "0";
        }finally {
            if (reader!=null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
