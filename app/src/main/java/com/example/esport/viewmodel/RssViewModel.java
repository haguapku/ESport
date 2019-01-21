package com.example.esport.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.esport.data.model.Item;
import com.example.esport.data.model.Rss;
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
                if(response.code() != 504) {
                    RssResponse rssResponse = new RssResponse(response.body(), null);
                    rssResponseMutableLiveData.postValue(rssResponse);
                }else {
                    rssResponseMutableLiveData.postValue(new RssResponse(null,"No cache"));
                }
            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
                rssResponseMutableLiveData.postValue(new RssResponse(null,t.getMessage()));
            }
        });
    }
}
