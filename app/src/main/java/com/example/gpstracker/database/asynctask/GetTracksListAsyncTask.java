package com.example.gpstracker.database.asynctask;

import android.os.AsyncTask;

import com.example.gpstracker.App;
import com.example.gpstracker.database.AppDatabase;
import com.example.gpstracker.database.entity.TrackEntity;

import java.util.ArrayList;
import java.util.List;

public class GetTracksListAsyncTask extends AsyncTask<TrackEntity, Integer, List<TrackEntity>> {
    @Override
    protected List<TrackEntity> doInBackground(TrackEntity... trackEntities) {
        final AppDatabase database = App.getInstance().getDatabase();
        long userId = database.getUserDao().getActiveUser().getId();
        List<TrackEntity> trackEntityList = database.getTrackDao().getTracks(userId);
        if (trackEntityList == null || trackEntityList.isEmpty()) {
            return new ArrayList<>();
        } else {
            return trackEntityList;
        }
    }
}
