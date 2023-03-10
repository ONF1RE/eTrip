package com.radiant.etrip.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.radiant.etrip.DirectionDistance;
import com.radiant.etrip.R;
import com.radiant.etrip.databinding.FragmentPsgMapsBBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PsgMapsFragmentB extends Fragment implements OnMapReadyCallback {

    FragmentPsgMapsBBinding binding;
    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker marker;
    private MarkerOptions markerOptions;
    String location;

    public PsgMapsFragmentB() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPsgMapsBBinding.inflate(inflater, container, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapInitialize();

        Bundle bundlePrev = this.getArguments();
        String latValueStart = bundlePrev.getString("latitude_start");
        String lngValueStart = bundlePrev.getString("longitude_start");

        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String latValueEnd = binding.latitude.getText().toString();
                String longValueEnd = binding.longitude.getText().toString();

                Intent intent = new Intent(getActivity(), DirectionDistance.class);
                intent.putExtra("latitude_start", latValueStart);
                intent.putExtra("longitude_start", lngValueStart);
                intent.putExtra("latitude_end", latValueEnd);
                intent.putExtra("longitude_end", longValueEnd);
                intent.putExtra("name_dest", location);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }

    private void mapInitialize(){
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(16);
        locationRequest.setFastestInterval(3000);

        binding.searchEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER){
                    goToSearchLocation();
                    binding.next.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
    }

    private void goToSearchLocation(){
        String searchLocation = binding.searchEdt.getText().toString();

        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchLocation, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list.size() > 0){
            Address address = list.get(0);
            location = address.getAdminArea();
            double latitude = address.getLatitude();
            double longitude = address.getLongitude();
            goToLatLng(latitude, longitude);

            if (marker != null){
                marker.remove();
            }
            markerOptions = new MarkerOptions();
            markerOptions.title(location);

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            markerOptions.position(new LatLng(latitude, longitude));
            markerOptions.draggable(true);
            marker = mMap.addMarker(markerOptions);
        }
    }

    private void goToLatLng(double latitude, double longitude){
        LatLng latLng = new LatLng(latitude, longitude);
        binding.latitude.setText((String.valueOf(latLng.latitude)));
        binding.longitude.setText((String.valueOf(latLng.longitude)));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 17f);
        mMap.animateCamera(update);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mMap.setMyLocationEnabled(true);

                        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(@NonNull LatLng latLng) {
                                int LayoutSearch = binding.layoutSearch.getVisibility();
                                if (LayoutSearch == View.VISIBLE){
                                    binding.layoutSearch.setVisibility(View.GONE);
                                }else{
                                    if (LayoutSearch == View.GONE) {
                                        binding.layoutSearch.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        });

                        fusedLocationProviderClient.getLastLocation().addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                            }
                        });

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(getContext(), "Permission"+
                                permissionDeniedResponse.getPermissionName() + "" + "was denied!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

        if (mMap != null){
            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDrag(@NonNull Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(@NonNull Marker marker) {
                    Geocoder geocoder = new Geocoder(getContext());
                    List<Address> list = null;
                    try{
                        LatLng markerPosition = marker.getPosition();
                        list = geocoder.getFromLocation(markerPosition.latitude, markerPosition.longitude, 1);
                        binding.latitude.setText((String.valueOf(markerPosition.latitude)));
                        binding.longitude.setText((String.valueOf(markerPosition.longitude)));
                        Address address = list.get(0);
                        marker.setTitle(address.getAdminArea());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    binding.next.setVisibility(View.VISIBLE);
                }

                @Override
                public void onMarkerDragStart(@NonNull Marker marker) {

                }
            });
        }
    }
}