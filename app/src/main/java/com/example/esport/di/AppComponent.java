package com.example.esport.di;

import com.example.esport.MyApplication;
import com.example.esport.di.module.AppModule;
import com.example.esport.di.module.ActivityBuilderModule;
import com.example.esport.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Author: created by MarkYoung on 4/01/2019 15:58
 */
@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilderModule.class,
        ViewModelModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MyApplication application);
        AppComponent build();
    }

    void inject(MyApplication application);
}
