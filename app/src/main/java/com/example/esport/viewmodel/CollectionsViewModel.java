package com.example.esport.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.esport.model.Service;
import com.example.esport.repository.CollectionsRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionsViewModel extends ViewModel{

    // The Live Data contains the fact list or error message.
    private MutableLiveData<CollectionsResponse> collectionsResponseLiveData;

    // The repository for network interactions.
    private CollectionsRepository collectionsRepository;

    /**
     * This method will return invoker a LiveData, with the LiveData, all the data and following update
     * can be notified to the observers.
     *
     * @return a observable LiveData
     */
    public LiveData<CollectionsResponse> getCollectionsResponseLiveData(){
        // If the live data is null, create a new instance.
        if (collectionsResponseLiveData == null){
            collectionsResponseLiveData = new MutableLiveData<>();
            // send request to load fact list from server for the first time.
            loadCollections();
        }
        return collectionsResponseLiveData;
    }

    /**
     * Load the fact list from the server.
     */
    public void loadCollections(){
        if (collectionsRepository == null){
            // create network repository if it hasn't be initialized.
            collectionsRepository = new CollectionsRepository();
        }

        // start Http request to load fact list, response will be processed by the callback.
        collectionsRepository.loadCollectionsFromServer(new Callback<Service>() {
            @Override
            public void onResponse(Call<Service> call, Response<Service> response) {
                CollectionsResponse collectionsResponse = new CollectionsResponse(response.body(),null);
                collectionsResponseLiveData.postValue(new CollectionsResponse(response.body(),null));
            }

            @Override
            public void onFailure(Call<Service> call, Throwable t) {
                collectionsResponseLiveData.postValue(new CollectionsResponse(null,t.getMessage()));
            }
        });
    }

    public MutableLiveData getServiceResponse(){
        return collectionsResponseLiveData;
    }
}
