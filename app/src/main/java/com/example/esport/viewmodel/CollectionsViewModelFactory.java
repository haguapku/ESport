package com.example.esport.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.esport.repository.CollectionsRepository;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Provides;

/**
 * Author: created by MarkYoung on 17/01/2019 12:06
 */
@Singleton
public class CollectionsViewModelFactory implements ViewModelProvider.Factory{

    private Map<Class<? extends ViewModel>,Provider<ViewModel>> creators;

    @Inject
    public CollectionsViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        this.creators = creators;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        Provider<? extends ViewModel> creator = creators.get(modelClass);
        if(creator == null){
            for(Map.Entry<Class<? extends ViewModel>,Provider<ViewModel>> entry:creators.entrySet()){
                if(entry.getKey().isAssignableFrom(modelClass)){
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if(creator == null)
            throw new IllegalArgumentException("Unknown ViewModel class");
        return (T)creator.get();
    }
}
