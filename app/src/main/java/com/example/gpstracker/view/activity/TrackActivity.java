package com.example.gpstracker.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.example.gpstracker.AppLocationListener;
import com.example.gpstracker.LocationListenerInterface;
import com.example.gpstracker.R;
import com.example.gpstracker.databinding.ActivityTrackBinding;
import com.example.gpstracker.viewModel.TrackViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Objects;

public class TrackActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListenerInterface {

    private static final int LOCATION_REQUEST = 1;
    private boolean locationPermissionGranted;

    private GoogleMap map;
    private LocationManager locationManager;
    private Location lastLocation;
    private long distance;
    private AppLocationListener appLocationListener;

    private PolylineOptions routePointsList;

    TrackViewModel viewModel = new TrackViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityTrackBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_track);
        binding.setTrackViewModel(viewModel);

        initialize();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
    }

    private void initialize(){
        locationPermissionGranted = false;
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        appLocationListener = new AppLocationListener();
        appLocationListener.setLocationListenerInterface(this);

        routePointsList = new PolylineOptions();
        routePointsList.width(17).color(Color.BLUE);
    }

    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        if (locationPermissionGranted) {
            try {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10,
                        1,appLocationListener);
            } catch (SecurityException e) {
                Log.e("Exception: %s", e.getMessage());
            }
        }
        else {
            checkLocationPermissions();
        }
    }

    private void drawRoute(float bearing) {
        map.addPolyline(routePointsList);
        map.moveCamera(CameraUpdateFactory
                .newCameraPosition(new CameraPosition(routePointsList.getPoints()
                        .get(routePointsList.getPoints().size() - 1), 18, 20, bearing)));
    }

    private void checkLocationPermissions(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String []
                    { Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION }, LOCATION_REQUEST);
        else {
            locationPermissionGranted = true;
            updateLocationUI();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location.hasSpeed() && lastLocation != null) {

            viewModel.addPoint(location.getLongitude(), location.getLatitude());

            viewModel.setSpeed(location.getSpeed());
            viewModel.setDistance(distance += lastLocation.distanceTo(location));

            routePointsList.add(new LatLng(location.getLatitude(), location.getLongitude()));
            drawRoute(lastLocation.bearingTo(location));
        }
        else if (lastLocation == null) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18));
        }
        lastLocation = location;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST: {
                if (grantResults.length == 2 && grantResults[0] == RESULT_OK && grantResults[1] == RESULT_OK)
                    locationPermissionGranted = true;
            }
        }
        updateLocationUI();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        map.clear();
        checkLocationPermissions();
    }

    public void stopLocationListen() {
        locationManager.removeUpdates(appLocationListener);
    }
}