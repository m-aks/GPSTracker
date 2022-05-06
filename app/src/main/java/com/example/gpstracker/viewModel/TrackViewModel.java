package com.example.gpstracker.viewModel;

import android.util.Pair;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.gpstracker.BR;
import com.example.gpstracker.database.asynctask.GetActiveUserAsyncTask;
import com.example.gpstracker.database.asynctask.InsertRouteDataAsyncTask;
import com.example.gpstracker.database.asynctask.InsertTrackDataAsyncTask;
import com.example.gpstracker.database.entity.TrackEntity;
import com.example.gpstracker.view.activity.TrackActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class TrackViewModel extends BaseObservable {

    private int counter;
    private float speed;
    private float averageSpeed;
    private long distance;
    private long time;

    private final List<Pair<Double, Double>> points;

    private final Timer timer;

    public TrackViewModel() {
        points = new ArrayList<>();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setTime();
            }
        }, 0, 1000);
    }

    private float average() {
        float temp = (this.averageSpeed * counter) + this.speed;
        counter++;
        return temp / counter;
    }

    @Bindable
    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        notifyPropertyChanged(BR.speed);

        this.averageSpeed = average();
        notifyPropertyChanged(BR.averageSpeed);
    }

    @Bindable
    public long getDistance() {
        return this.distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
        notifyPropertyChanged(BR.distance);
    }

    @Bindable
    public float getAverageSpeed() {
        return this.averageSpeed;
    }

    @Bindable
    public long getTime() {
        return time;
    }

    public void setTime() {
        this.time++;
        notifyPropertyChanged(BR.time);
    }

    public void addPoint(double longitude, double latitude) {
        points.add(new Pair<>(longitude, latitude));
    }

    public void finishRide(Object activity) {
        if (activity instanceof TrackActivity) {
            timer.cancel();
            InsertRouteDataAsyncTask routeTask = new InsertRouteDataAsyncTask();
            InsertTrackDataAsyncTask trackTask = new InsertTrackDataAsyncTask();
            try {
                long userId = new GetActiveUserAsyncTask().execute().get().getId();
                trackTask.execute(new TrackEntity(new Date(), distance, averageSpeed, time, userId));
                trackTask.get();
                routeTask.execute(points);
                routeTask.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            ((TrackActivity) activity).stopLocationListen();
            ((TrackActivity) activity).finish();
        }
    }
}
