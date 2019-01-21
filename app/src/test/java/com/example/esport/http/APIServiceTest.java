package com.example.esport.http;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.esport.data.model.Service;
import com.example.esport.di.AppInjector;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import io.reactivex.Single;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Author: created by MarkYoung on 17/01/2019 14:49
 */
@RunWith(MockitoJUnitRunner.class)
public class APIServiceTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private APIService apiService;
    private MockWebServer mockWebServer;

    @Before
    public void setUp() throws Exception {

        mockWebServer = new MockWebServer();

        apiService = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/")) // The base URL
                .addConverterFactory(SimpleXmlConverterFactory.create())// The converter for parsing Xml string to Java Object
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(APIService.class);
    }


    @Test
    public void load() throws IOException {
        enqueueResponse("sports-3.xml");
        Single<Service> response = apiService.getService();
        Service service = response.blockingGet();
        assertThat(service.workspace.title, Is.is("eSportsReader Sports"));
        assertThat(service.workspace.collections.get(0).title, Is.is("Dota 2"));
    }

    private void enqueueResponse(String fileName) throws IOException {

        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("api-resource/"+fileName);
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse mockResponse = new MockResponse();
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)));
    }

}