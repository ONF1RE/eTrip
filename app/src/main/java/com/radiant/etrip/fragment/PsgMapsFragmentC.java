package com.radiant.etrip.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.radiant.etrip.R;
import com.radiant.etrip.databinding.FragmentPsgMapsCBinding;
import com.radiant.etrip.direction.FetchURL;
import com.radiant.etrip.direction.TaskLoadedCallback;

import java.util.ArrayList;
import java.util.List;

public class PsgMapsFragmentC extends Fragment implements OnMapReadyCallback, TaskLoadedCallback {

    FragmentPsgMapsCBinding binding;
    SupportMapFragment mapFragment;
    private GoogleMap mMap;

    private MarkerOptions marker1, marker2;
    private Polyline polyline;
    List<MarkerOptions>  markerOptionsList = new ArrayList<>();

    public PsgMapsFragmentC() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//        Bundle bundle = this.getArguments();
//        Double latValueStart = Double.parseDouble(bundle.getString("latitude_start"));
//        Double lngValueStart = Double.parseDouble(bundle.getString("longitude_start"));
//        Double latValueEnd = Double.parseDouble(bundle.getString("latitude_end"));
//        Double lngValueEnd = Double.parseDouble(bundle.getString("longitude_end"));

        //marker1 = new MarkerOptions().position(new LatLng(latValueStart, lngValueStart)).title("Location1");
        //marker2 = new MarkerOptions().position(new LatLng(latValueEnd, lngValueEnd)).title("Location2");

        marker1 = new MarkerOptions().position(new LatLng(27.658143, 85.3199503)).title("Location1");
        marker2 = new MarkerOptions().position(new LatLng(27.667491, 85.3208583)).title("Location2");

        markerOptionsList.add(marker1);
        markerOptionsList.add(marker2);

        new FetchURL(this.getContext()).execute(getUrl(marker1.getPosition(), marker2.getPosition(), "driving"),"driving");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPsgMapsCBinding.inflate(inflater, container, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return binding.getRoot();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(marker1);
        mMap.addMarker(marker2);

        showAllMarkers();
    }

    private void showAllMarkers() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (MarkerOptions m : markerOptionsList){
            builder.include(m.getPosition());
        }

        LatLngBounds bounds = builder.build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.30);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        mMap.animateCamera(cameraUpdate);
    }

    private String getUrl (LatLng origin, LatLng dest, String directionMode){
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        String mode = "mode=" + directionMode;
        String parameter = str_origin + "&" + str_dest + "&" + mode;
        String format = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + format + "?"
                + parameter + "&key=AIzaSyD9zD8xlDBNQgSNfUX2UamzdA4jhW0kHzc";

        return url;
    }


    @Override
    public void onTaskDone(Object... values) {
        if(polyline != null)
            polyline.remove();

        polyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

}