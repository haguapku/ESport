package com.example.esport.http;

import android.util.Log;

import com.example.esport.MyApplication;
import com.example.esport.util.NetWorkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpCacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        System.out.println("------SETMAXAGE-----");
        int maxAge = 60; // read from cache for 1 minute
        response.newBuilder()
                .removeHeader("Pragma")
                .addHeader("Cache-Control", "public, max-age=" + maxAge)
                .build();
        return response;
    }
}
