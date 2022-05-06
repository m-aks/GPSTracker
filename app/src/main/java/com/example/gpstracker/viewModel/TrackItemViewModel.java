package com.example.gpstracker.viewModel;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.gpstracker.database.asynctask.GetTrackByDateAsyncTask;
import com.example.gpstracker.model.TrackModel;
import com.example.gpstracker.view.activity.MainActivity;
import com.example.gpstracker.view.activity.RouteMapActivity;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class TrackItemViewModel extends BaseObservable {

    private final TrackModel dataModel;

    public TrackItemViewModel(TrackModel dataModel) {
        this.dataModel = dataModel;
    }

    @Bindable
    public Date getDate() {
        return dataModel.getDate();
    }

    @Bindable
    public long getDistance() {
        return dataModel.getDistance();
    }

    @Bindable
    public float getAverageSpeed() {
        return dataModel.getAverageSpeed();
    }

    @Bindable
    public long getTime() {
        return dataModel.getTime();
    }

    public void showRoute(Object context) {
        if (context instanceof MainActivity) {
            GetTrackByDateAsyncTask task = new GetTrackByDateAsyncTask();
            task.execute(dataModel.getDate());
            try {
                Intent intent = new Intent((Context)context, RouteMapActivity.class);
                intent.putExtra("track", task.get().getId());
                ((MainActivity)context).startActivity(intent);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
