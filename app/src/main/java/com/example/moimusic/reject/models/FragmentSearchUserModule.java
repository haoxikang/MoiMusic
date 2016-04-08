package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.FragmentSearchUserPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.fragment.FragmentSearchUser;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 康颢曦 on 2016/4/8.
 */
@Module
public class FragmentSearchUserModule {
    private FragmentSearchUser fragmentSearchUser;

    public FragmentSearchUserModule(FragmentSearchUser fragmentSearchUser) {
        this.fragmentSearchUser = fragmentSearchUser;
    }
    @Provides
    @ActivityScope
    FragmentSearchUser provideFragmentSearchUser() {
        return fragmentSearchUser;
    }
    @Provides
    @ ActivityScope
    FragmentSearchUserPresenter provideFragmentSearchUserPresenter(ApiService apiService) {
        return new FragmentSearchUserPresenter( apiService);
    }
}
