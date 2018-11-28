package com.example.esport.viewmodel;

import com.example.esport.model.Service;

public class CollectionsResponse {

    // The Data class converted by the Xml string.
    public Service service;
    // The error message when Http request fail.
    public String errorMessage;

    /**
     * Constructor method for FactListResponse
     * @param service the collection list
     * @param msg the errro message
     */
    public CollectionsResponse(Service service, String msg){
        this.service = service;
        this.errorMessage = msg;
    }
}
