package com.example.gpstracker.database.asynctask;

import android.os.AsyncTask;

import com.example.gpstracker.App;
import com.example.gpstracker.database.entity.TrackEntity;

import java.util.Date;

public class GetTrackByDateAsyncTask extends AsyncTask<Date, Integer, TrackEntity> {
    @Override
    protected TrackEntity doInBackground(Date... dates) {
        long userId = App.getInstance().getDatabase().getUserDao().getActiveUser().getId();
        return App.getInstance().getDatabase().getTrackDao().getTrackByDate(dates[0], userId);
    }
}
