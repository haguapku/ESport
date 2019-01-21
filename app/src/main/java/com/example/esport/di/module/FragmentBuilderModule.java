package com.example.esport.di.module;

import com.example.esport.fragment.MainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Author: created by MarkYoung on 17/01/2019 11:16
 */
@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract MainFragment contributeMainFragment();
}
