package com.example.moimusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moimusic.AppApplication;
import com.example.moimusic.R;
import com.example.moimusic.mvp.presenters.FragmentSignInPresenter;
import com.example.moimusic.mvp.views.FragmentSignInView;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerFragmentSignInComponent;
import com.example.moimusic.reject.models.FragmentSignInModule;
import com.rey.material.widget.ProgressView;

import javax.inject.Inject;

/**
 * Created by qqq34 on 2016/2/14.
 */
public class FragmentSignIn extends BaseFragment implements FragmentSignInView{
    @Inject
    FragmentSignInPresenter presenter;
    private TextInputLayout phoneTPL,  passwordTPL;
    private FloatingActionButton floatingActionButton;
    private ProgressView progressView;
    private TextView textForget;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.attach(this,getContext());
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragmentSignInComponent.builder()
                .appComponent(appComponent)
                .fragmentSignInModule(new FragmentSignInModule(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);
        Initview(v);
        InitClick();
        return v;
    }
    private void Initview(View view){
        phoneTPL = (TextInputLayout)view.findViewById(R.id.log_in_username);
        passwordTPL = (TextInputLayout)view.findViewById(R.id.log_in_password);
        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.login);
        textForget = (TextView)view.findViewById(R.id.forget);
        progressView =(ProgressView)view.findViewById(R.id.progress);
        progressView.setVisibility(View.INVISIBLE);
    }
    private void InitClick(){
        phoneTPL.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onphoneNumPrint(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordTPL.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onPasswordPrint(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        floatingActionButton.setOnClickListener(v -> presenter.LogIn());
        textForget.setOnClickListener(v -> presenter.showDialog());
    }
    @Override
    public void phoneNumberShowError(boolean isShow) {
        if (isShow) {
            phoneTPL.setErrorEnabled(true);
            phoneTPL.setError(AppApplication.context.getResources().getString(R.string.errorPhone));
        } else {
            phoneTPL.setErrorEnabled(false);
        }
    }

    @Override
    public void passwordShowError(boolean isShow) {
        if (isShow) {
            passwordTPL.setErrorEnabled(true);
            passwordTPL.setError(AppApplication.context.getResources().getString(R.string.inputRightPassword));
        } else {
            passwordTPL.setErrorEnabled(false);
        }
    }

    @Override
    public void showSnakBar(String s) {
        Snackbar.make(floatingActionButton, s, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressWithAllViewUnEnable(boolean isShow) {
        if (isShow){
            progressView.setVisibility(View.VISIBLE);
        }else {
            progressView.setVisibility(View.INVISIBLE);
        }
        phoneTPL .getEditText().setEnabled(!isShow);
        passwordTPL .getEditText().setEnabled(!isShow);
        textForget.setEnabled(!isShow);
        floatingActionButton.setEnabled(!isShow);
    }

    @Override
    public String[] getUserText() {
        String[] a = new  String[2];
        a[0]=phoneTPL.getEditText().getText().toString();
        a[1]=passwordTPL.getEditText().getText().toString();
        return a;
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }
}
