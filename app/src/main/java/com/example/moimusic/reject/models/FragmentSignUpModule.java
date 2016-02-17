package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.FragmentSignInPresenter;
import com.example.moimusic.mvp.presenters.FragmentSignUpPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.fragment.FragmentSignUp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qqq34 on 2016/2/17.
 */
@Module
public class FragmentSignUpModule {
    private FragmentSignUp fragmentSignUp;

    public FragmentSignUpModule(FragmentSignUp fragmentSignUp) {
        this.fragmentSignUp = fragmentSignUp;
    }
    @Provides
    @ActivityScope
    FragmentSignUp provideFragmentSignUp(){
        return fragmentSignUp;
    }
    @Provides
    @ ActivityScope
    FragmentSignUpPresenter provideFragmentSignUpPresenter(ApiService apiService) {
        return new FragmentSignUpPresenter( apiService);
    }
}
