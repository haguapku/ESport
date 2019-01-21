package com.example.esport.di.module;

import com.example.esport.MainActivity;
import com.example.esport.fragment.MainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Author: created by MarkYoung on 17/01/2019 10:26
 */
@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract MainActivity contributeMainActivity();
}
