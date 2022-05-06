package com.example.gpstracker.database.asynctask;

import android.os.AsyncTask;
import android.util.Pair;

import com.example.gpstracker.App;
import com.example.gpstracker.database.AppDatabase;
import com.example.gpstracker.database.dao.RouteDao;
import com.example.gpstracker.database.dao.TrackDao;
import com.example.gpstracker.database.entity.RouteEntity;

import java.util.List;

public class InsertRouteDataAsyncTask extends AsyncTask<List<Pair<Double, Double>>, Integer, Void> {

    @SafeVarargs
    @Override
    protected final Void doInBackground(List<Pair<Double, Double>>... lists) {
        final AppDatabase database = App.getInstance().getDatabase();
        RouteDao routeDao = database.getRouteDao();
        long userId = database.getUserDao().getActiveUser().getId();
        long id = database.getTrackDao().getLastTrack(userId).getId();
        for (int i = 0; i < lists[0].size(); i++) {
            routeDao.insert(new RouteEntity(lists[0].get(i).second, lists[0].get(i).first, id));
        }
        return null;
    }
}
