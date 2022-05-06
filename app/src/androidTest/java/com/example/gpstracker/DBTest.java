package com.example.gpstracker;

import android.content.Context;
import android.util.Pair;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.gpstracker.database.AppDatabase;
import com.example.gpstracker.database.asynctask.GetActiveUserAsyncTask;
import com.example.gpstracker.database.asynctask.InsertTrackDataAsyncTask;
import com.example.gpstracker.database.asynctask.InsertRouteDataAsyncTask;
import com.example.gpstracker.database.dao.TrackDao;
import com.example.gpstracker.database.dao.RouteDao;
import com.example.gpstracker.database.entity.TrackEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DBTest {

    private AppDatabase db;

    private float averageSpeed;
    private long distance;
    private long time;

    private List<Pair<Double, Double>> points;

    public DBTest() {}

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        distance = 1200;
        averageSpeed = (float) 3.45;
        time = 200;
        points = new ArrayList<>();
    }
    @After
    public void closeDb() {
        db.close();
    }
    @Test
    public void writeTrackEntity() {
        InsertTrackDataAsyncTask trackTask = new InsertTrackDataAsyncTask();
        try {
            trackTask.execute(new TrackEntity(new Date(), distance, averageSpeed, time, -1));
            trackTask.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void writeRouteEntity() {
        InsertRouteDataAsyncTask routeTask = new InsertRouteDataAsyncTask();
        points.add(new Pair<>(53.20377, 50.16061));
        points.add(new Pair<>(53.203805, 50.16059166666667));
        points.add(new Pair<>(53.20384, 50.16057));
        routeTask.execute(points);
        try {
            routeTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
