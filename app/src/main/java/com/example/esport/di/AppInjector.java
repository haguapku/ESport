package com.example.esport.di;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.esport.MyApplication;

import dagger.android.AndroidInjection;
import dagger.android.HasActivityInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Author: created by MarkYoung on 17/01/2019 13:44
 */
public class AppInjector {

    private AppInjector() {}
    
    public static void init(MyApplication application){
        
        DaggerAppComponent.builder().application(application).build().inject(application);
        
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                handleActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private static void handleActivity(Activity activity) {

        if(activity instanceof Injectable && activity instanceof HasSupportFragmentInjector)
            AndroidInjection.inject(activity);
        if(activity instanceof FragmentActivity){
            ((FragmentActivity)activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentPreAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
                    super.onFragmentPreAttached(fm, f, context);
                    if(f instanceof Injectable)
                        AndroidSupportInjection.inject(f);
                }
            },true);
        }
    }
}
