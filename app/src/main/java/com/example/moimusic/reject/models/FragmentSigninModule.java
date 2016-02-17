package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.FragmentSignInPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.fragment.FragmentSignIn;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qqq34 on 2016/2/16.
 */
@Module
public class FragmentSignInModule {
    private FragmentSignIn fragmentSignIn;

    public FragmentSignInModule(FragmentSignIn fragmentSignIn) {
        this.fragmentSignIn = fragmentSignIn;
    }
    @Provides
   @ ActivityScope
    FragmentSignIn provideFragmentSignIn() {
        return fragmentSignIn;
    }
    @Provides
    @ ActivityScope
    FragmentSignInPresenter provideFragmentSignInPresenter(ApiService apiService) {
        return new FragmentSignInPresenter( apiService);
    }
}

