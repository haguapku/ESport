package com.example.esport.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esport.R;
import com.example.esport.databinding.CollectionItemBinding;
import com.example.esport.model.Collection;
import com.example.esport.util.OnItemClick;

import java.util.List;

public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.CollectionViewHolder> {

    private List<Collection> data;

    private OnItemClick click;

    public void setCollections(final List<Collection> collections){
        if(data == null){
            data = collections;
            notifyItemRangeInserted(0, data.size());
        }else{
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return data.size();
                }

                @Override
                public int getNewListSize() {
                    return collections.size();
                }

                @Override
                public boolean areItemsTheSame(int i, int i1) {
                    return data.get(i) == collections.get(i1);
                }

                @Override
                public boolean areContentsTheSame(int i, int i1) {
                    Collection old_data = data.get(i);
                    Collection new_data = collections.get(i1);
                    return old_data.id.equals(new_data.id)
                            && old_data.href.equals(new_data.href)
                            && old_data.title.equals(new_data.title)
                            && old_data.categories.equals(new_data.categories);
                }
            });
            data = collections;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CollectionItemBinding collectionItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.collection_item, viewGroup, false);
        return new CollectionViewHolder(collectionItemBinding,click);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionViewHolder collectionViewHolder, int i) {
        collectionViewHolder.binding.setCollection(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data == null? 0 : data.size();
    }

    static class CollectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final CollectionItemBinding binding;

        OnItemClick click;

        public CollectionViewHolder(@NonNull CollectionItemBinding collectionItemBinding,OnItemClick click) {
            super(collectionItemBinding.getRoot());
            binding = collectionItemBinding;
            this.click = click;
            collectionItemBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view != null)
                click.onItemClick(view,getAdapterPosition());
        }
    }

    public void setItemClick(OnItemClick click){
        this.click= click;
    }
}
