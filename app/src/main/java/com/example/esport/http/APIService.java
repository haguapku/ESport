package com.example.esport.http;

import com.example.esport.model.Feed;
import com.example.esport.model.Item;
import com.example.esport.model.Rss;
import com.example.esport.model.Service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface APIService {

    @GET("/reader/sports")
    Call<Service> getService();

    @GET
    Call<Feed> getFeed(@Url String url);

    @GET
    Call<Rss> getRss(@Url String url);

}
