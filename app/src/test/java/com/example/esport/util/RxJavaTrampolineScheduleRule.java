package com.example.esport.util;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: created by MarkYoung on 17/01/2019 22:36
 */
public class RxJavaTrampolineScheduleRule implements TestRule{

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {

                RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());

                RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());

                RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.trampoline());

                RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());

                try{
                    base.evaluate();
                }finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };

    }
}
