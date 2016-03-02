package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.ActivityPlayNowPresenter;
import com.example.moimusic.mvp.presenters.EditActivityPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.activity.ActivityPlayNow;
import com.example.moimusic.ui.activity.EditActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qqq34 on 2016/3/1.
 */
@Module
public class EditActivityModule {
    private EditActivity editActivity;

    public EditActivityModule(EditActivity editActivity) {
        this.editActivity = editActivity;
    }
    @Provides
    @ActivityScope
    EditActivity provideEditActivity() {
        return editActivity;
    }


    @Provides
    @ActivityScope
    EditActivityPresenter provideEditActivityPresenter(ApiService apiService) {
        return new EditActivityPresenter( apiService);
    }
}
