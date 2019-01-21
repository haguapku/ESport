package com.example.esport.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esport.R;
import com.example.esport.databinding.RssItemBinding;
import com.example.esport.data.model.Item;
import com.example.esport.util.OnItemClick;

import java.util.List;

public class RssAdapter extends RecyclerView.Adapter<RssAdapter.RssViewHolder> {

    private List<Item> items;

    private OnItemClick click;

    public void setItems(final List<Item> new_items){
        if(items == null){
            this.items = new_items;
            notifyItemRangeInserted(0, items.size());
        }else{
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return items.size();
                }

                @Override
                public int getNewListSize() {
                    return new_items.size();
                }

                @Override
                public boolean areItemsTheSame(int i, int i1) {
                    return items.get(i) == new_items.get(i1);
                }

                @Override
                public boolean areContentsTheSame(int i, int i1) {
                    Item old_item = items.get(i);
                    Item new_item = new_items.get(i1);
                    return old_item.description.equals(new_item.description)
                            && old_item.link.equals(new_item.link)
                            && old_item.title.equals(new_item.title);
                }
            });
            items = new_items;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RssViewHolder rssViewHolder, int i) {
        rssViewHolder.binding.setItem(items.get(i));
    }

    @NonNull
    @Override
    public RssViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RssItemBinding rssItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()), R.layout.rss_item,viewGroup,false);
        return new RssViewHolder(rssItemBinding,click);
    }

    @Override
    public int getItemCount() {
        return items == null? 0 : items.size();
    }

    static class RssViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final RssItemBinding binding;
        OnItemClick click;

        public RssViewHolder(@NonNull RssItemBinding rssItemBinding, OnItemClick click) {
            super(rssItemBinding.getRoot());
            binding = rssItemBinding;
            this.click = click;
            rssItemBinding.getRoot().setOnClickListener(this);
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
