package com.example.gpstracker.viewModel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.gpstracker.BR;
import com.example.gpstracker.TrackAdapter;
import com.example.gpstracker.database.asynctask.GetTracksListAsyncTask;
import com.example.gpstracker.database.entity.TrackEntity;
import com.example.gpstracker.model.TrackModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends BaseObservable {

    private TrackAdapter adapter;
    private List<TrackModel> data;

    public MainViewModel() {
        data = new ArrayList<>();
        adapter = new TrackAdapter();
    }

    @Bindable
    public List<TrackModel> getData() {
        return this.data;
    }

    @Bindable
    public TrackAdapter getAdapter() {
        return this.adapter;
    }

    public void setup() {
        GetTracksListAsyncTask task = new GetTracksListAsyncTask();
        task.execute();
        List<TrackEntity> dataList = null;
        try {
            dataList = task.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (dataList != null && !dataList.isEmpty()) {
            data.clear();
            for (TrackEntity entity : dataList) {
                TrackModel model = new TrackModel(entity.getDate(), entity.getDistance(),
                        entity.getAverageSpeed(), entity.getTime());
                data.add(model);
            }
        }
        notifyPropertyChanged(BR.data);
    }
}