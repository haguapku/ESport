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
        String url = request.url().toString();
        if (NetWorkUtil.Companion.isNetWorkConnected()){
            request = request.newBuilder()
                    .addHeader("User-Agent","Mozilla/5.0 (Linux; Android 4.4.4;Google Nexus 5; Build/KTU84P; DPI/XHDPI; AppVersion/11) AppleWebKit/537.36(KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36")
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
        }else {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response response = chain.proceed(request);

        if (NetWorkUtil.Companion.isNetWorkConnected()) {
            int maxAge = 60; // read from cache for 1 minute
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }
}
