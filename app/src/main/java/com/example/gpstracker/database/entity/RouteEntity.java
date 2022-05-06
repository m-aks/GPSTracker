package com.example.gpstracker.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "routes",
        foreignKeys = @ForeignKey(
                entity = TrackEntity.class,
                parentColumns = "id",
                childColumns = "track_id",
                onDelete = CASCADE))
public class RouteEntity {

    public RouteEntity(double latitude, double longitude, long trackId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.trackId = trackId;
    }

    @PrimaryKey(autoGenerate = true)
    private long id;

    private double latitude; //Широта

    private double longitude; //Долгота

    @ColumnInfo(name = "track_id")
    private long trackId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTrackId() {
        return trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }
}
