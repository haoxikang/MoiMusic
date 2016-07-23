package com.example.moimusic.mvp.model.updata;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 康颢曦 on 2016/4/13.
 */
public class SimpleFile {
    private BmobFile bmobFile;
    private int progress;

    public SimpleFile(BmobFile bmobFile) {
        this.bmobFile = bmobFile;
    }

    public BmobFile getBmobFile() {
        return bmobFile;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + bmobFile.hashCode();
        return result;
    }
}

