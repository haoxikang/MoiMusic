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
    private int currentOrder;

    public int getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(int currentOrder) {
        this.currentOrder = currentOrder;
    }
}
