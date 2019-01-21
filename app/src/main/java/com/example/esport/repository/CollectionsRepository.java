package com.example.esport.repository;

import com.example.esport.MyApplication;
import com.example.esport.http.APIService;
import com.example.esport.http.BaseIntercepter;
import com.example.esport.http.HttpCacheInterceptor;
import com.example.esport.http.RetroficManager;
import com.example.esport.data.model.Service;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Singleton
public class CollectionsRepository {

    private APIService apiService;

    @Inject
    public CollectionsRepository(APIService apiService) {
        this.apiService = apiService;
    }

    public Single<Service> loadCollectionsFromServer(){
        return apiService.getService();
    }

}
