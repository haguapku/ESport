package com.example.esport.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.view.View;

import com.example.esport.viewmodel.CollectionsViewModel;
import com.example.esport.viewmodel.CollectionsViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Author: created by MarkYoung on 17/01/2019 14:17
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CollectionsViewModel.class)
    abstract ViewModel bindCollectionsViewModel(CollectionsViewModel collectionsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindCollectionsViewModelFactory(CollectionsViewModelFactory collectionsViewModelFactory);
}
