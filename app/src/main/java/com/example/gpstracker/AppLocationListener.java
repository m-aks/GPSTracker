package com.example.gpstracker;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;

public class AppLocationListener implements LocationListener {

    private LocationListenerInterface locationListenerInterface;

    @Override
    public void onLocationChanged(@NonNull Location location) {
        locationListenerInterface.onLocationChanged(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    public void setLocationListenerInterface(LocationListenerInterface locationListenerInterface) {
        this.locationListenerInterface = locationListenerInterface;
    }
}
