package com.example.gpstracker.database.asynctask;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.gpstracker.App;
import com.example.gpstracker.database.dao.TrackDao;
import com.example.gpstracker.database.entity.TrackEntity;

public class InsertTrackDataAsyncTask extends AsyncTask<TrackEntity, Integer, Void> {
    @Override
    protected Void doInBackground(@NonNull TrackEntity... trackEntities) {
        App.getInstance().getDatabase().getTrackDao().insert(trackEntities[0]);
        return null;
    }
}