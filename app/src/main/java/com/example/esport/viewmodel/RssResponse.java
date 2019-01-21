package com.example.esport.viewmodel;

import com.example.esport.data.model.Rss;

public class RssResponse {

    public Rss rss;
    public String errorMessage;

    public RssResponse(Rss rss, String msg) {
        this.rss = rss;
        this.errorMessage = msg;
    }
}
