package com.example.esport.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.esport.MainActivity;
import com.example.esport.R;
import com.example.esport.adapter.FeedAdapter;
import com.example.esport.http.RecyclerListener;
import com.example.esport.data.model.Entry;
import com.example.esport.util.OnItemClick;
import com.example.esport.viewmodel.FeedResponse;
import com.example.esport.viewmodel.FeedViewModel;

import java.util.List;

public class FeedFragment extends Fragment implements OnItemClick{

    private FeedViewModel feedViewModel;

    private FeedAdapter feedAdapter;

    private MainActivity mainActivity;

    private List<Entry> entries;

    private int position;

    private String url;

    private SwipeRefreshLayout swipeRefreshLayout;

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("position");
        url = getArguments().getString("url")+"/ ";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
        suscribFeed(feedViewModel);
        setListener(new RecyclerListener() {
            @Override
            public void refresh() {
                feedViewModel.loadFeed(url);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity){
            mainActivity = (MainActivity)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    void suscribFeed(FeedViewModel feedViewModel){
        feedViewModel.getFeedResponseLiveData(url).observe(this, new Observer<FeedResponse>() {
            @Override
            public void onChanged(@Nullable FeedResponse feedResponse) {
                if(feedResponse.feed != null){
                    swipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                    entries = feedResponse.feed.entries;
                    feedAdapter.setEntries(entries);
                    if (mainActivity != null) mainActivity.setTitle(feedResponse.feed.id);
                }else if(feedResponse.errorMessage != null){
                    swipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                    Toast.makeText(getContext(), "data failed to load", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment,container,false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        feedAdapter = new FeedAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.feed);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(feedAdapter);
        feedAdapter.setItemClick(this);
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("url",entries.get(position).source.id);
        bundle.putInt("position",position);
        RssFragment rssFragment = new RssFragment();
        rssFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, rssFragment).addToBackStack(null)
                .commit();
    }

    private void setListener(final RecyclerListener recyclerListener){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerListener.refresh();
            }
        });
    }
}
