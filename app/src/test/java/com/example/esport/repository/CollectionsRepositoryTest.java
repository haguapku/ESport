package com.example.esport.repository;

import com.example.esport.data.model.Categories;
import com.example.esport.data.model.Category;
import com.example.esport.data.model.Collection;
import com.example.esport.data.model.Service;
import com.example.esport.data.model.Workspace;
import com.example.esport.http.APIService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
/**
 * Author: created by MarkYoung on 17/01/2019 13:32
 */
@RunWith(MockitoJUnitRunner.class)
public class  CollectionsRepositoryTest {

    @Mock
    APIService apiService;

    CollectionsRepository collectionsRepository;

    @Before
    public void setUp() throws Exception {

        collectionsRepository = new CollectionsRepository(apiService);
    }

    @Test
    public void loadFromServerTest() {

        collectionsRepository.loadCollectionsFromServer();
        verify(apiService,times(1)).getService();
    }
}