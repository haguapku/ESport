package com.example.esport.util;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;

/**
 * Author: created by MarkYoung on 17/01/2019 17:43
 */
public class RxJavaTestScheduleRule implements TestRule{

    private final static TestScheduler mTestScheduler = new TestScheduler();

    public static TestScheduler getTestScheduler() {
        return mTestScheduler;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {

                RxJavaPlugins.setIoSchedulerHandler(scheduler -> mTestScheduler);

                RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> mTestScheduler);

                RxJavaPlugins.setComputationSchedulerHandler(scheduler -> mTestScheduler);

                RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> mTestScheduler);

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
