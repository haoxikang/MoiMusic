package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.FragmentSignInModule;
import com.example.moimusic.ui.fragment.FragmentSignIn;

import javax.inject.Scope;

import dagger.Component;

/**
 * Created by qqq34 on 2016/2/16.
 */
@ActivityScope
@Component(modules = FragmentSignInModule.class,dependencies = AppComponent.class)
public interface FragmentSignInComponent {
    FragmentSignIn inject(FragmentSignIn fragmentSignIn);
}
