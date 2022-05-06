package com.example.gpstracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpstracker.databinding.TrackItemBinding;
import com.example.gpstracker.model.TrackModel;
import com.example.gpstracker.viewModel.TrackItemViewModel;

import java.util.LinkedList;
import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    private final List<TrackModel> data;

    public TrackAdapter() {
        this.data = new LinkedList<>();
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_item, new LinearLayout(parent.getContext()), false);
        return new TrackViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        TrackModel trackModel = data.get(position);
        holder.setViewModel(new TrackItemViewModel(trackModel));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull TrackViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.bind();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull TrackViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    public void updateData(@Nullable List<TrackModel> data) {
        if (data == null || data.isEmpty()) {
            this.data.clear();
        }
        else {
            this.data.clear();
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

     static class TrackViewHolder extends RecyclerView.ViewHolder {

        TrackItemBinding binding;

        TrackViewHolder(View itemView) {
            super(itemView);
            bind();
        }

        void bind() {
            if (binding == null) {
                binding = DataBindingUtil.bind(itemView);
            }
        }

        void unbind() {
            if (binding != null) {
                binding.unbind();
            }
        }

        void setViewModel(TrackItemViewModel viewModel) {
            if (binding != null) {
                binding.setViewModel(viewModel);
            }
        }
    }
}
