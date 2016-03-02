package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.EditActivityModule;
import com.example.moimusic.ui.activity.EditActivity;

import dagger.Component;

/**
 * Created by qqq34 on 2016/3/1.
 */
@ActivityScope
@Component(modules = EditActivityModule.class,dependencies = AppComponent.class)
public interface EditActivityComponent  {
    EditActivity inject(EditActivity editActivity);
}
