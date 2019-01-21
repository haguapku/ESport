package com.example.esport;

import android.app.Activity;
import android.app.Application;

import com.example.esport.di.AppInjector;
import com.example.esport.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MyApplication extends Application implements HasActivityInjector{

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    private static MyApplication instance;

//    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

//        DaggerAppComponent.builder().application(this).build().inject(this);
        AppInjector.init(this);
    }

//    public AppComponent getAppComponent() {
//        return appComponent;
//    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
