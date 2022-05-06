package com.example.gpstracker.databinding;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpstracker.TrackAdapter;
import com.example.gpstracker.model.TrackModel;

import java.util.List;

public class RecyclerViewDataBinding {
    @BindingAdapter({"app:adapter", "app:data"})
    public void bind(@NonNull RecyclerView recyclerView, TrackAdapter adapter, List<TrackModel> data) {
        recyclerView.setAdapter(adapter);
        adapter.updateData(data);
    }
}
