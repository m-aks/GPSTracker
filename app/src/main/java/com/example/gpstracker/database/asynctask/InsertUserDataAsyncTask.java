package com.example.gpstracker.database.asynctask;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.gpstracker.App;
import com.example.gpstracker.database.entity.UserEntity;


public class InsertUserDataAsyncTask extends AsyncTask<UserEntity, Integer, Void> {
    @Override
    protected Void doInBackground(@NonNull UserEntity... userEntities) {
        App.getInstance().getDatabase().getUserDao().insert(userEntities[0]);
        return null;
    }
}