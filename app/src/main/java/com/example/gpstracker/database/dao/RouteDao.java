package com.example.gpstracker.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gpstracker.database.entity.RouteEntity;

import java.util.List;

@Dao
public interface RouteDao {
    @Insert
    void insert(RouteEntity obj);

    @Query("SELECT * FROM routes WHERE track_id = :id")
    List<RouteEntity> getRouteByTrackId(long id);
}
