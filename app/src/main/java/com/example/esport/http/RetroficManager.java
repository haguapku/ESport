package com.example.esport.http;

import com.example.esport.MyApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Author: created by MarkYoung on 7/11/2018 12:20
 */
public class RetroficManager {

    private static RetroficManager instance;

    public static RetroficManager getInstance(){
        if(instance !=null){
            return instance;
        }else {
            return new RetroficManager();
        }
    }

    public APIService createRequest(){

        // create the request interface instance through retrofit
        return new Retrofit.Builder()
                .baseUrl("http://feed.esportsreader.com/") // The base URL
                .addConverterFactory(SimpleXmlConverterFactory.create())// The converter for parsing Xml string to Java Object
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient(false))
                .build().create(APIService.class);
    }

    // create okHttpClient singleton
    OkHttpClient createOkHttpClient(boolean debug) {
        //set cache 100M
        Cache cache = new Cache(new File(MyApplication.getInstance().getCacheDir(),"httpCache"),1024 * 1024 * 100);
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new BaseIntercepter())
                .addNetworkInterceptor(new HttpCacheInterceptor())
//                .addInterceptor(
//                        new HttpLoggingInterceptor().setLevel(
//                                debug ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .build();
    }
}
