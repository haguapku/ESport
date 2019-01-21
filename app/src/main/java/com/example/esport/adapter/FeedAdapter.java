package com.example.esport.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esport.R;
import com.example.esport.databinding.FeedItemBinding;
import com.example.esport.data.model.Entry;
import com.example.esport.util.OnItemClick;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private List<Entry> entries;

    private OnItemClick click;

    public void setEntries(final List<Entry> new_entries){
        if(entries == null){
            this.entries = new_entries;
            notifyItemRangeInserted(0, entries.size());
        }else{
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return entries.size();
                }

                @Override
                public int getNewListSize() {
                    return new_entries.size();
                }

                @Override
                public boolean areItemsTheSame(int i, int i1) {
                    return entries.get(i) == new_entries.get(i1);
                }

                @Override
                public boolean areContentsTheSame(int i, int i1) {
                    Entry old_entry = entries.get(i);
                    Entry new_entry = new_entries.get(i1);
                    return old_entry.id.equals(new_entry.id)
                            && old_entry.source.equals(new_entry.source)
                            && old_entry.summary.equals(new_entry.summary)
                            && old_entry.title.equals(new_entry.title)
                            && old_entry.updated.equals(new_entry.updated);
                }
            });
            entries = new_entries;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FeedItemBinding feedItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()), R.layout.feed_item,viewGroup,false);
        return new FeedViewHolder(feedItemBinding,click);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder feedViewHolder, int i) {
        feedViewHolder.binding.setEntry(entries.get(i));
    }

    @Override
    public int getItemCount() {
        return entries == null? 0 : entries.size();
    }

    static class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final FeedItemBinding binding;

        OnItemClick click;

        public FeedViewHolder(@NonNull FeedItemBinding feedItemBinding,OnItemClick click) {
            super(feedItemBinding.getRoot());
            binding = feedItemBinding;
            this.click = click;
            feedItemBinding.getRoot().setOnClickListener(this);
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
