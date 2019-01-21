package com.example.esport.http;

import com.example.esport.util.NetWorkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: created by MarkYoung on 19/12/2018 13:26
 */
public class BaseIntercepter implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        if (!NetWorkUtil.Companion.isNetWorkConnected()){
            System.out.println("------FORCE_CACHE-----");
            int maxStale = 28 * 24 * 60 * 60;
            request = request.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        Response response = chain.proceed(request);
        System.out.println("code: " + response.code() + " body: " + response.body());
        return response;
    }
}
