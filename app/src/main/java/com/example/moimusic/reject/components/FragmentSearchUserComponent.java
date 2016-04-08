package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.FragmentSearchUserModule;
import com.example.moimusic.ui.fragment.FragmentSearchUser;

import dagger.Component;

/**
 * Created by 康颢曦 on 2016/4/8.
 */
@ActivityScope
@Component(modules = FragmentSearchUserModule.class,dependencies = AppComponent.class)
public interface FragmentSearchUserComponent {
    FragmentSearchUser inject(FragmentSearchUser fragmentSearchUser);
}
