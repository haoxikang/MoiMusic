package com.example.moimusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moimusic.R;
import com.example.moimusic.mvp.presenters.FragmentSignUpPresenter;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerFragmentSignInComponent;
import com.example.moimusic.reject.components.DaggerFragmentSignUpComponent;
import com.example.moimusic.reject.models.FragmentSignInModule;
import com.example.moimusic.reject.models.FragmentSignUpModule;
import com.gc.materialdesign.views.ButtonFlat;
import com.rey.material.widget.CheckBox;

import javax.inject.Inject;

/**
 * Created by qqq34 on 2016/2/14.
 */
public class FragmentSignUp extends BaseFragment {
    @Inject
    FragmentSignUpPresenter presenter;
   private TextInputLayout phoneTPL,YZTPL,passwordTPL;
   private ButtonFlat buttonFlat;
    private CheckBox checkBox;
     @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragmentSignUpComponent.builder()
                .appComponent(appComponent)
                .fragmentSignUpModule(new FragmentSignUpModule(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initView(v);
        return v;
    }
    public void initView(View view){
        phoneTPL = (TextInputLayout)view.findViewById(R.id.log_up_phone);
        YZTPL = (TextInputLayout)view.findViewById(R.id.log_up_yanzheng);
        passwordTPL = (TextInputLayout)view.findViewById(R.id.log_up_password);
        checkBox = (CheckBox)view.findViewById(R.id.log_up_checkBox);
        buttonFlat = (ButtonFlat)view.findViewById(R.id.log_up_button);
    }
    public void click(){

    }
}
