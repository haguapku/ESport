package com.example.esport.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.esport.data.model.Feed;
import com.example.esport.repository.FeedRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedViewModel extends ViewModel{

    private MutableLiveData<FeedResponse> feedResponseMutableLiveData;

    private FeedRepository feedRepository;

    public LiveData<FeedResponse> getFeedResponseLiveData(String url){
        if(feedResponseMutableLiveData == null){
            feedResponseMutableLiveData = new MutableLiveData<>();
            loadFeed(url);
        }
        return feedResponseMutableLiveData;
    }

    public void loadFeed(String url){
        if(feedRepository == null){
            feedRepository = new FeedRepository(url);
        }
        feedRepository.loadFeedFromServer(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                if(response.code() != 504) {
                    FeedResponse feedResponse = new FeedResponse(response.body(), null);
                    feedResponseMutableLiveData.postValue(feedResponse);
                }else {
                    feedResponseMutableLiveData.postValue(new FeedResponse(null,"No cache"));
                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                feedResponseMutableLiveData.postValue(new FeedResponse(null,t.getMessage()));
            }
        });
    }
}
