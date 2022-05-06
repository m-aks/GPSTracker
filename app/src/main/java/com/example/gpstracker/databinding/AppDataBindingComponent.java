package com.example.gpstracker.databinding;

public class AppDataBindingComponent implements androidx.databinding.DataBindingComponent {
    @Override
    public RecyclerViewDataBinding getRecyclerViewDataBinding() {
        return new RecyclerViewDataBinding();
    }
}

