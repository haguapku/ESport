package com.example.esport.repository;

import com.example.esport.MyApplication;
import com.example.esport.http.APIService;
import com.example.esport.http.HttpCacheInterceptor;
import com.example.esport.http.RetroficManager;
import com.example.esport.data.model.Feed;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class FeedRepository {

    String url = "";

    public FeedRepository(String url){
        this.url = url;
    }
    /**
     * This method will send a asynchronous request to get the data from server.
     * The response will be processed in the callbacks.
     *
     * @param callback callback method to process the response.
     */
    public void loadFeedFromServer(Callback<Feed> callback){
        // get the request
        //APIService request = createRequest();

        // send the request asynchronously, and processing the response in the callback.
        //request.getFeed(url).enqueue(callback);
        RetroficManager.getInstance().createRequest().getFeed(url).enqueue(callback);
    }

    /**
     * create the instance of retrofit request interface.
     *
     * @return HttpRequesInterface the interface instance, representing the GetFactList Service API.
     */

    private APIService createRequest(){
        // create the request interface instance through retrofit
        return new Retrofit.Builder()
                .baseUrl("http://feed.esportsreader.com/") // The base URL
                .addConverterFactory(SimpleXmlConverterFactory.create()) // The converter for parsing Xml string to Java Object
                .client(createOkHttpClient(false))
                .build().create(APIService.class);
    }

    // create okHttpClient singleton
    OkHttpClient createOkHttpClient(boolean debug) {
        //set cache 100M
        Cache cache = new Cache(new File(MyApplication.getInstance().getCacheDir(),"httpCache"),1024 * 1024 * 100);
        return new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .addInterceptor(
                        new HttpLoggingInterceptor().setLevel(
                                debug ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                .build();
    }
}
