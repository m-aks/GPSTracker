package com.example.gpstracker.database.asynctask;

import android.os.AsyncTask;

import com.example.gpstracker.App;
import com.example.gpstracker.database.entity.RouteEntity;

import java.util.ArrayList;
import java.util.List;


public class GetRoutesListAsyncTask extends AsyncTask<Long, Integer, List<RouteEntity>> {

    @Override
    protected List<RouteEntity> doInBackground(Long... longs) {
        List<RouteEntity> routeEntityList = App.getInstance().getDatabase().getRouteDao()
                .getRouteByTrackId(longs[0]);
        if (routeEntityList == null || routeEntityList.isEmpty()) {
            return new ArrayList<>();
        } else {
            return routeEntityList;
        }
    }
}
