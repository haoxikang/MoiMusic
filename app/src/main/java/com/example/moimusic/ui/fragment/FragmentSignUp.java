package com.example.moimusic.ui.fragment;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.moimusic.AppApplication;
import com.example.moimusic.R;
import com.example.moimusic.mvp.presenters.FragmentSignUpPresenter;
import com.example.moimusic.mvp.views.FragmentSignUpView;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerFragmentSignInComponent;
import com.example.moimusic.reject.components.DaggerFragmentSignUpComponent;
import com.example.moimusic.reject.models.FragmentSignInModule;
import com.example.moimusic.reject.models.FragmentSignUpModule;
import com.gc.materialdesign.views.ButtonFlat;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.ProgressView;

import javax.inject.Inject;

/**
 * Created by qqq34 on 2016/2/14.
 */
public class FragmentSignUp extends BaseFragment implements FragmentSignUpView {
    @Inject
    FragmentSignUpPresenter presenter;
    private TextInputLayout phoneTPL, YZTPL, passwordTPL;
    private ButtonFlat buttonFlat;
    private CheckBox checkBox;
    private FloatingActionButton floatingActionButton;
    private ProgressView progressView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.attach(this);
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
        initClick();
        return v;
    }

    public void initView(View view) {
        phoneTPL = (TextInputLayout) view.findViewById(R.id.log_up_phone);
        YZTPL = (TextInputLayout) view.findViewById(R.id.log_up_yanzheng);
        passwordTPL = (TextInputLayout) view.findViewById(R.id.log_up_password);
        checkBox = (CheckBox) view.findViewById(R.id.log_up_checkBox);
        buttonFlat = (ButtonFlat) view.findViewById(R.id.log_up_button);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.sign_up_float);
        buttonFlat.setEnabled(false);
        progressView = (ProgressView)view.findViewById(R.id.progress);
        progressView.setVisibility(View.INVISIBLE);
    }

    public void initClick() {
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
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                passwordTPL.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                passwordTPL.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
        floatingActionButton.setOnClickListener(v -> {
            presenter.Logup();
        });
        buttonFlat.setOnClickListener(v -> {
            presenter.getYZM(phoneTPL.getEditText().getText().toString());
        });
    }

    @Override
    public void phoneNumberShowError(boolean isShow) {


        if (isShow) {
            phoneTPL.setErrorEnabled(true);
            phoneTPL.setError(AppApplication.context.getResources().getString(R.string.errorPhone));
            buttonFlat.setEnabled(false);
        } else {
            phoneTPL.setErrorEnabled(false);
            buttonFlat.setEnabled(true);
            if (phoneTPL.getEditText().getText().length() == 0) {
                buttonFlat.setEnabled(false);
            }
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
    public void phoneNumberEnable(boolean isEnable) {
        phoneTPL.getEditText().setEnabled(isEnable);
    }

    @Override
    public void showSnakBar(String s) {
        Snackbar.make(floatingActionButton, s, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setButonFlatEnable(boolean isEnable) {
        buttonFlat.setEnabled(isEnable);
    }

    @Override
    public void setButtonFlatText(String s) {
        buttonFlat.setText(s);
    }

    @Override
    public String[] getUserText() {
       String[] a = new  String[3];
        a[0]=phoneTPL.getEditText().getText().toString();
        a[1]=YZTPL.getEditText().getText().toString();
        a[2]=passwordTPL.getEditText().getText().toString();
        return a;
    }
    @Override
    public void finishActivity() {
        getActivity().finish();
    }
    @Override
    public void showProgressWithAllViewUnEnable(boolean isShow) {
        if (isShow){
            progressView.setVisibility(View.VISIBLE);
        }else {
            progressView.setVisibility(View.INVISIBLE);
        }
        phoneTPL .getEditText().setEnabled(!isShow);
        YZTPL .getEditText().setEnabled(!isShow);
        passwordTPL .getEditText().setEnabled(!isShow);
        checkBox.setEnabled(!isShow);
        buttonFlat.setEnabled(!isShow);
        floatingActionButton .setEnabled(!isShow);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }
}
