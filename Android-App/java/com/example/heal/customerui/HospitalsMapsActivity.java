package com.example.heal.customerui;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.heal.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HospitalsMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng delhi = new LatLng(28.7, 77.1);
        LatLng Hosp1 = new LatLng(28.7,77.05);
        LatLng Hosp2 = new LatLng(28.79,77.05);
        LatLng Hosp3 = new LatLng(28.75,77.19);
        LatLng Hosp4 = new LatLng(28.59,77.08);
        LatLng Hosp5 = new LatLng(28.78,77.2);
        mMap.addMarker(new MarkerOptions().position(delhi).title("My Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(delhi,11));
        mMap.addMarker(new MarkerOptions().position(Hosp1).title("Hospital 1 - Ph:XXXXXXXXXXXX"));
        mMap.addMarker(new MarkerOptions().position(Hosp2).title("Hospital 2 - Ph:XXXXXXXXXXXX"));
        mMap.addMarker(new MarkerOptions().position(Hosp3).title("Hospital 3 - Ph:XXXXXXXXXXXX"));
        mMap.addMarker(new MarkerOptions().position(Hosp4).title("Hospital 4 - Ph:XXXXXXXXXXXX"));
        mMap.addMarker(new MarkerOptions().position(Hosp5).title("Hospital 5 - Ph:XXXXXXXXXXXX"));

    }
}