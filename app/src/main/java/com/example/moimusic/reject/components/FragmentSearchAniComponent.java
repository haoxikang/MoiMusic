package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.FragmentSearchAniModule;
import com.example.moimusic.ui.fragment.FragmentSearchAni;

import dagger.Component;

/**
 * Created by 康颢曦 on 2016/4/4.
 */
@ActivityScope
@Component(modules = FragmentSearchAniModule.class,dependencies = AppComponent.class)
public interface FragmentSearchAniComponent {
    FragmentSearchAni inject(FragmentSearchAni fragmentSearchAni);
}
