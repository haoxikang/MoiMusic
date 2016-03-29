package com.example.moimusic.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Administrator on 2016/3/29.
 */
public class SoftKeyboardUtil {
    public  static boolean isKeyboardEven = true;
    public static void observeSoftKeyboard(Activity activity, final OnSoftKeyboardChangeListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            int previousKeyboardHeight = -1;
            @Override
            public void onGlobalLayout() {
                if (isKeyboardEven){
                    Rect rect = new Rect();
                    decorView.getWindowVisibleDisplayFrame(rect);
                    int displayHeight = rect.bottom - rect.top;
                    int height = decorView.getHeight();
                    int keyboardHeight = height - displayHeight;
                    if (previousKeyboardHeight != keyboardHeight&&isKeyboardEven) {
                        boolean hide = (double) displayHeight / height > 0.8;
                        listener.onSoftKeyBoardChange(keyboardHeight, !hide);
                    }
                    previousKeyboardHeight = height;
                }else {
                    isKeyboardEven=true;
                }


            }
        });
    }

    public interface OnSoftKeyboardChangeListener {
        void onSoftKeyBoardChange(int softKeybardHeight, boolean visible);
    }
}
