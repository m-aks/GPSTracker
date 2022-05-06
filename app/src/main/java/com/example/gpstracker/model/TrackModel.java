package com.example.gpstracker.model;

import java.util.Date;

public class TrackModel {

    private long id;

    private Date date;

    private long distance;

    private float averageSpeed;

    private long time;

    public TrackModel(Date date, long distance, float averageSpeed, long rideTime) {
        this.date = date;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
        this.time = rideTime;
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
}
