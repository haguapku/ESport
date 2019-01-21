package com.example.esport.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.esport.data.model.Collection;
import com.example.esport.data.model.Service;
import com.example.esport.repository.CollectionsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class CollectionsViewModel extends ViewModel{


    // The Live Data contains the fact list or error message.
    private MutableLiveData<CollectionsResponse> collectionsResponseLiveData = new MutableLiveData<>();

    // The repository for network interactions.
    private CollectionsRepository collectionsRepository;

    public CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public CollectionsViewModel(CollectionsRepository collectionsRepository) {
        this.collectionsRepository = collectionsRepository;
    }

    /**
     * This method will return invoker a LiveData, with the LiveData, all the data and following update
     * can be notified to the observers.
     *
     * @return a observable LiveData
     */
    public LiveData<CollectionsResponse> getCollectionsResponseLiveData(){

//        loadCollections();
        return collectionsResponseLiveData;
    }

    /**
     * Load the fact list from the server.
     */
    public void loadCollections(){

        disposables.add(collectionsRepository.loadCollectionsFromServer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Service>() {
                    @Override
                    public void onSuccess(Service service) {
                        collectionsResponseLiveData.setValue(new CollectionsResponse(service,null));
                    }

                    @Override
                    public void onError(Throwable e) {
                        collectionsResponseLiveData.setValue(new CollectionsResponse(null,e.getMessage()));
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
