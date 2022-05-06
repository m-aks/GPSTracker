package com.example.gpstracker.database.asynctask;

import android.os.AsyncTask;

import com.example.gpstracker.App;
import com.example.gpstracker.database.entity.UserEntity;

public class GetUserByLoginAsyncTask extends AsyncTask<String, Integer, UserEntity> {

    @Override
    protected UserEntity doInBackground(String... strings) {
        return App.getInstance().getDatabase().getUserDao().getUserByLogin(strings[0]);
    }
}
