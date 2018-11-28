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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esport.MainActivity;
import com.example.esport.R;
import com.example.esport.adapter.RssAdapter;
import com.example.esport.http.RecyclerListener;
import com.example.esport.model.Item;
import com.example.esport.util.NetWorkUtil;
import com.example.esport.util.OnItemClick;
import com.example.esport.viewmodel.RssResponse;
import com.example.esport.viewmodel.RssViewModel;

import java.util.List;

public class RssFragment extends Fragment implements OnItemClick{

    private RssViewModel rssViewModel;
    private RssAdapter rssAdapter;
    private MainActivity mainActivity;
    private List<Item> items;
    private String url;
    WebView webView;
    RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url")+"/ ";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rss_fragment,container,false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        webView = view.findViewById(R.id.webview);
        rssAdapter = new RssAdapter();
        recyclerView = view.findViewById(R.id.rss);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(rssAdapter);
        rssAdapter.setItemClick(this);
        return view;
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
        rssViewModel = ViewModelProviders.of(this).get(RssViewModel.class);
        suscribRss(rssViewModel);
        setListener(new RecyclerListener() {
            @Override
            public void refresh() {
                rssViewModel.loadRss(url);
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

    void suscribRss(RssViewModel rssViewModel){
        rssViewModel.getRssResponseLiveData(url).observe(this, new Observer<RssResponse>() {
            @Override
            public void onChanged(@Nullable RssResponse rssResponse) {
                if(rssResponse.rss !=null){
                    swipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                    items = rssResponse.rss.channel.items;
                    rssAdapter.setItems(items);
                    if (mainActivity != null) mainActivity.setTitle(rssResponse.rss.channel.title);
                }else if(rssResponse.errorMessage !=null){
                    swipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                    if(!NetWorkUtil.Companion.isNetWorkConnected()){
                        Toast.makeText(getContext(), "data failed to load", Toast.LENGTH_SHORT).show();
                    }else{
                        swipeRefreshLayout.setVisibility(View.GONE);
                        webView.setVisibility(View.VISIBLE);
                        webView.loadUrl(url);
                    }
                }

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("url",items.get(position).link);
        bundle.putString("title",items.get(position).title);
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, detailFragment).addToBackStack(null)
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
