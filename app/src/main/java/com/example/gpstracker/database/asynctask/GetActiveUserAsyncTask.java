package com.example.gpstracker.database.asynctask;

import android.os.AsyncTask;

import com.example.gpstracker.App;
import com.example.gpstracker.database.entity.UserEntity;

public class GetActiveUserAsyncTask extends AsyncTask<Void, Integer, UserEntity> {

    @Override
    protected UserEntity doInBackground(Void... voids) {
        return App.getInstance().getDatabase().getUserDao().getActiveUser();
    }
}
