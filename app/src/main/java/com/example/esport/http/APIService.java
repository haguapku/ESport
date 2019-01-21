package com.example.esport.http;

import com.example.esport.data.model.Feed;
import com.example.esport.data.model.Item;
import com.example.esport.data.model.Rss;
import com.example.esport.data.model.Service;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIService {

    /*@GET("/reader/sports")
    Call<Service> getService();*/
    @GET("/reader/sports")
    Single<Service> getService();

    @GET
    Call<Feed> getFeed(@Url String url);

    @GET
    Call<Rss> getRss(@Url String url);

}
