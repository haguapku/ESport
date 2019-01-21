package com.example.esport.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.example.esport.data.model.Service;
import com.example.esport.repository.CollectionsRepository;
import com.example.esport.util.RxJavaTrampolineScheduleRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Author: created by MarkYoung on 17/01/2019 15:27
 */
@RunWith(MockitoJUnitRunner.class)
public class CollectionsViewModelTest {

    @Rule
    public RxJavaTrampolineScheduleRule rxJavaTrampolineScheduleRule = new RxJavaTrampolineScheduleRule();

    @Mock
    CollectionsRepository collectionsRepository;

    CollectionsViewModel collectionsViewModel;

    @Mock
    MutableLiveData<CollectionsResponse> collectionsResponseMutableLiveData;

    @Before
    public void setUp() throws Exception {

        collectionsViewModel = new CollectionsViewModel(collectionsRepository);
        /*Category category = new Category("dota2");
        Categories categories = new Categories("http://feed.esportsreader.com/reader/sports/categories",category);
        Collection collection = new Collection("http://abc","1","Hero", categories);
        List<Collection> collections = Arrays.asList(collection);
        Workspace workspace = new Workspace("dota 2",collections);
        Service service = new Service(workspace);

        */

    }

    @Test
    public void load() {

        TestObserver<Service> testObserver = new TestObserver<>();
        when(collectionsRepository.loadCollectionsFromServer()).thenReturn(Single.just(new Service()));

        /*collectionsRepository.loadCollectionsFromServer().subscribe(testObserver);
        testObserver.assertComplete();
        testObserver.assertValueCount(1);*/

        collectionsViewModel.loadCollections();
        verify(collectionsRepository).loadCollectionsFromServer();

    }
}