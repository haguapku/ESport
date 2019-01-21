package com.example.esport.viewmodel;

import com.example.esport.data.model.Feed;

public class FeedResponse {

    public Feed feed;
    public String errorMessage;

    public FeedResponse(Feed feed, String msg){
        this.feed = feed;
        this.errorMessage = msg;
    }
}
