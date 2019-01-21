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
import com.example.esport.adapter.CollectionsAdapter;
import com.example.esport.data.spf.MySharedPreference;
import com.example.esport.di.Injectable;
import com.example.esport.http.RecyclerListener;
import com.example.esport.data.model.Collection;
import com.example.esport.util.OnItemClick;
import com.example.esport.viewmodel.CollectionsResponse;
import com.example.esport.viewmodel.CollectionsViewModel;
import com.example.esport.viewmodel.CollectionsViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class MainFragment extends Fragment implements OnItemClick,Injectable {

    public static final String TAG = "MainFragment";

    private CollectionsViewModel mViewModel;

    private CollectionsAdapter mAdapter;

    private MainActivity mainActivity;

    private List<Collection> collectionList;

    private SwipeRefreshLayout swipeRefreshLayout;

    /*private SharedPreferences preferences;

    private SharedPreferences.Editor editor;*/

    @Inject
    MySharedPreference mySharedPreference;

    @Inject
    CollectionsViewModelFactory factory;

    /**
     * Get the instance of the MainFragment.
     * @return a instance of MainFragment
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MyApplication.getInstance().getAppComponent().inject(this);
//        AndroidSupportInjection.inject(this);

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
        mViewModel = ViewModelProviders.of(this,factory).get(CollectionsViewModel.class);
        subscribCollections(mViewModel);
        mViewModel.loadCollections();
        setListener(new RecyclerListener() {
            @Override
            public void refresh() {
                mViewModel.loadCollections();
            }
        });

        /*preferences = getActivity().getSharedPreferences("mypreference",Context.MODE_PRIVATE);
        editor = preferences.edit();*/
    }

    @Override
    public void onAttach(Context context) {
//        AndroidSupportInjection.inject(this);
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
                    saveToSharedpreference(collectionList);

                    List<Collection> toBeRemovedList = new ArrayList<>();
                    for(Collection c: collectionList)
//                        if(preferences.getString(c.title,null).equals("unChecked"))
                        if(mySharedPreference.getString(c.title).equals("unChecked"))
                            toBeRemovedList.add(c);
                    collectionList.removeAll(toBeRemovedList);
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

    private void saveToSharedpreference(List<Collection> list){

        for(Collection c: list){

           /* if(preferences.getString(c.title,null) == null)
                editor.putString(c.title,"Checked");*/
            if(mySharedPreference.getString(c.title) == null)
                mySharedPreference.putString(c.title,"Checked");
        }
//        editor.commit();
    }
}
