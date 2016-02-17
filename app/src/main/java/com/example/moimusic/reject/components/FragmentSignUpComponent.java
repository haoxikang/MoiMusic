package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.FragmentSignInModule;
import com.example.moimusic.reject.models.FragmentSignUpModule;
import com.example.moimusic.ui.fragment.FragmentSignIn;
import com.example.moimusic.ui.fragment.FragmentSignUp;

import dagger.Component;

/**
 * Created by qqq34 on 2016/2/17.
 */
@ActivityScope
@Component(modules = FragmentSignUpModule.class,dependencies = AppComponent.class)
public interface FragmentSignUpComponent {
    FragmentSignUp inject(FragmentSignUp fragmentSignUp);
}
