package com.example.moimusic.mvp.model.entity;

/**
 * Created by qqq34 on 2016/2/2.
 */
public class EvenCall {
    public final static int PLAY = 0;
    public final static int NEXT = 1;
    public final static int PRE = 2;
    public final static int PAUSE = 3;
    public final static int START=4;
    public final static int SEEKTO=5;
    private int currentOrder;
    private int current;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(int currentOrder) {
        this.currentOrder = currentOrder;
    }
}
