package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.moimusic.AppApplication;
import com.example.moimusic.R;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.UserBiz;
import com.example.moimusic.mvp.views.FragmentSignInView;
import com.example.moimusic.mvp.views.FragmentSignUpView;
import com.example.moimusic.utils.Utils;
import com.rey.material.app.Dialog;

import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/2/16.
 */
public class FragmentSignInPresenter extends BasePresenterImpl{
    private Factory factory;
    private FragmentSignInView fragmentSignInView;
    private Context context;
    public FragmentSignInPresenter(ApiService apiService) {
        factory = new DataBizFactory();
    }
    public void attach(FragmentSignInView fragmentSignInView,Context context) {
        this.fragmentSignInView = fragmentSignInView;
        this.context = context;

    }
    public void LogIn(){
        String[] text = fragmentSignInView.getUserText();
        if (TextUtils.isEmpty(text[0])||TextUtils.isEmpty(text[1])){
            fragmentSignInView.showSnakBar(AppApplication.context.getResources().getString(R.string.please_input_right_message));
        }else if (text[0].length()!=11||text[1].length()<6){
            fragmentSignInView.showSnakBar(AppApplication.context.getResources().getString(R.string.please_input_right_message));
        }else {
            fragmentSignInView.showProgressWithAllViewUnEnable(true);
            UserBiz userBiz = factory.createBiz(UserBiz.class);
            mSubscriptions.add(userBiz.LogIn(text).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(moiUser -> {
                        EventBus.getDefault().post(AppApplication.context.getResources().getString(R.string.log_in_succ));
                        fragmentSignInView.showProgressWithAllViewUnEnable(false);
                        fragmentSignInView.finishActivity();
                    },throwable -> {
                        fragmentSignInView.showSnakBar(throwable.getMessage());
                        fragmentSignInView.showProgressWithAllViewUnEnable(false);
                    }));

        }
    }
    public void showDialog(){
        Dialog mDialog = new Dialog(context);
        mDialog.setTitle(context.getResources().getString(R.string.frendlyhint));
        mDialog.positiveAction(context.getResources().getString(R.string.OK));
        TextView textView = new TextView(context);
        textView.setText(context.getResources().getString(R.string.frendlyhint_content));
        textView.setPadding(70,0,70,0);
        textView.setTextSize(16);
        mDialog.setContentView(textView);
        mDialog   .cancelable(true);
        mDialog.positiveActionClickListener(v -> mDialog.dismiss());
        mDialog.show();
    }
    public void onphoneNumPrint(String s) {
        fragmentSignInView.phoneNumberShowError(!Utils.isMobileNO(s));
    }

    public void onPasswordPrint(String s) {
        fragmentSignInView.passwordShowError(!Utils.isPassword(s));
    }
}
