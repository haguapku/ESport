package com.example.esport.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.esport.model.Item;
import com.example.esport.model.Rss;
import com.example.esport.repository.RssRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RssViewModel extends ViewModel {

    private MutableLiveData<RssResponse> rssResponseMutableLiveData;

    private RssRepository rssRepository;

    public LiveData<RssResponse> getRssResponseLiveData(String url){
        if(rssResponseMutableLiveData == null){
            rssResponseMutableLiveData = new MutableLiveData<>();
            loadRss(url);
        }
        return rssResponseMutableLiveData;
    }

    public void loadRss(String url){
        if(rssRepository == null){
            rssRepository = new RssRepository(url);
        }
        rssRepository.loadRssFromServer(new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                RssResponse rssResponse = new RssResponse(response.body(),null);
                rssResponseMutableLiveData.postValue(rssResponse);
            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
                rssResponseMutableLiveData.postValue(new RssResponse(null,t.getMessage()));
            }
        });
    }
}
