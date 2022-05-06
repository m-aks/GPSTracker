package com.example.gpstracker.database.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.gpstracker.database.DateConverters;

import java.util.Date;

@Entity(tableName = "tracking",
        foreignKeys = @ForeignKey(
                entity = UserEntity.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = CASCADE))
public class TrackEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @TypeConverters({DateConverters.class})
    private Date date;

    private long distance;

    private float averageSpeed;

    private long time;

    @ColumnInfo(name = "user_id")
    private long userId;

    public TrackEntity(Date date, long distance, float averageSpeed, long time, long userId) {
        this.date = date;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
        this.time = time;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public long getDistance() {
        return distance;
    }

    public float getAverageSpeed() {
        return averageSpeed;
    }

    public long getTime() {
        return time;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public void setAverageSpeed(float averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
