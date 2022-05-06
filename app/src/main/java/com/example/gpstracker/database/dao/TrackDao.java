package com.example.gpstracker.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gpstracker.database.entity.TrackEntity;

import java.util.Date;
import java.util.List;

@Dao
public interface TrackDao {
    @Insert
    void insert(TrackEntity obj);

    @Query("SELECT * FROM tracking WHERE user_id = :userId ORDER BY date DESC")
    List<TrackEntity> getTracks(long userId);

    @Query("SELECT * FROM tracking WHERE user_id = :userId ORDER BY id DESC LIMIT 1")
    TrackEntity getLastTrack(long userId);

    @Query("SELECT * FROM tracking WHERE date = :date and user_id = :userId")
    TrackEntity getTrackByDate(Date date, long userId);
}
