package com.example.gpstracker.view.activity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gpstracker.R;
import com.example.gpstracker.database.asynctask.GetRoutesListAsyncTask;
import com.example.gpstracker.database.entity.RouteEntity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class RouteMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        drawRoute();
    }

    private void drawRoute() {
        List<RouteEntity> routeEntityList = new ArrayList<>();

        Bundle arguments = getIntent().getExtras();
        long id = arguments.getLong("track");

        GetRoutesListAsyncTask task = new GetRoutesListAsyncTask();
        task.execute(id);
        try {
            routeEntityList = task.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if (!routeEntityList.isEmpty()) {
            PolylineOptions routePointsList = new PolylineOptions();
            routePointsList.width(17).color(Color.BLUE);

            for (int i = 0; i < routeEntityList.size(); i++) {
                routePointsList.add(new LatLng(routeEntityList.get(i).getLatitude(),
                        routeEntityList.get(i).getLongitude()));
            }

            map.addPolyline(routePointsList);
            map.moveCamera(CameraUpdateFactory
                    .newCameraPosition(new CameraPosition(routePointsList.getPoints()
                            .get(routePointsList.getPoints().size() / 2), 18, 20, 10)));
        }
    }
}