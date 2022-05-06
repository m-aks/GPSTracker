package com.example.gpstracker.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.gpstracker.database.dao.RouteDao;
import com.example.gpstracker.database.dao.TrackDao;
import com.example.gpstracker.database.dao.UserDao;
import com.example.gpstracker.database.entity.RouteEntity;
import com.example.gpstracker.database.entity.TrackEntity;
import com.example.gpstracker.database.entity.UserEntity;

@Database(entities = {UserEntity.class, TrackEntity.class, RouteEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();
    public abstract TrackDao getTrackDao();
    public abstract RouteDao getRouteDao();
}
