package com.example.esport.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.esport.MyApplication;
import com.example.esport.data.spf.MySharedPreference;
import com.example.esport.di.AppComponent;
import com.example.esport.http.APIService;
import com.example.esport.http.BaseIntercepter;
import com.example.esport.http.HttpCacheInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Author: created by MarkYoung on 17/01/2019 10:05
 */
@Module
public class AppModule {

    @Singleton
    @Provides
    APIService createRequest() {

        // create the request interface instance through retrofit
        return new Retrofit.Builder()
                .baseUrl("http://feed.esportsreader.com/") // The base URL
                .addConverterFactory(SimpleXmlConverterFactory.create())// The converter for parsing Xml string to Java Object
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient())
                .build().create(APIService.class);
    }

    @Singleton
    @Provides
    OkHttpClient createOkHttpClient() {
        //set cache 100M
        Cache cache = new Cache(new File(MyApplication.getInstance().getCacheDir(),"httpCache"),1024 * 1024 * 100);
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new BaseIntercepter())
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .build();
    }

    @Singleton
    @Provides
    SharedPreferences getSharedPreference(MyApplication myApplication){
        return myApplication.getApplicationContext().getSharedPreferences("mypreference", MODE_PRIVATE);
    }

}
