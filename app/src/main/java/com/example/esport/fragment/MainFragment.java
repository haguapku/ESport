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
import android.widget.Toast;

import com.example.esport.MainActivity;
import com.example.esport.R;
import com.example.esport.adapter.CollectionsAdapter;
import com.example.esport.http.RecyclerListener;
import com.example.esport.model.Collection;
import com.example.esport.util.OnItemClick;
import com.example.esport.viewmodel.CollectionsResponse;
import com.example.esport.viewmodel.CollectionsViewModel;

import java.util.List;

public class MainFragment extends Fragment implements OnItemClick {

    public static final String TAG = "MainFragment";

    private CollectionsViewModel mViewModel;

    private CollectionsAdapter mAdapter;

    private MainActivity mainActivity;

    private List<Collection> collectionList;

    private SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Get the instance of the MainFragment.
     * @return a instance of MainFragment
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment,container,false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        mAdapter = new CollectionsAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.collections);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setItemClick(this);

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
        mViewModel = ViewModelProviders.of(this).get(CollectionsViewModel.class);
        subscribCollections(mViewModel);
        setListener(new RecyclerListener() {
            @Override
            public void refresh() {
                mViewModel.loadCollections();
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

    private void subscribCollections(CollectionsViewModel viewModel){
        viewModel.getCollectionsResponseLiveData().observe(this, new Observer<CollectionsResponse>() {
            @Override
            public void onChanged(@Nullable CollectionsResponse collectionsResponse) {
                if(collectionsResponse.service != null){
                    swipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                    collectionList = collectionsResponse.service.workspace.collections;
                    mAdapter.setCollections(collectionList);
                    if (mainActivity != null) mainActivity.setTitle(collectionsResponse.service.workspace.title);
                }else if(collectionsResponse.errorMessage != null){
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

    @Override
    public void onItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        bundle.putString("url",collectionList.get(position).href);
        FeedFragment feedFragment = FeedFragment.newInstance();
        feedFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, feedFragment).addToBackStack(null)
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
