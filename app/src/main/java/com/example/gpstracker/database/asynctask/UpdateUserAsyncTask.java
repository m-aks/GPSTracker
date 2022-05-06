package com.example.gpstracker.database.asynctask;

import android.os.AsyncTask;

import com.example.gpstracker.App;
import com.example.gpstracker.database.entity.UserEntity;

public class UpdateUserAsyncTask extends AsyncTask<UserEntity, Integer, Void> {

    @Override
    protected Void doInBackground(UserEntity... entities) {
        App.getInstance().getDatabase().getUserDao().update(entities[0]);
        return null;
    }
}