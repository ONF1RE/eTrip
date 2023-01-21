package com.radiant.etrip;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radiant.etrip.databinding.ActivityDirectionDistanceBinding;
import com.radiant.etrip.direction.FetchURL;
import com.radiant.etrip.direction.TaskLoadedCallback;
import com.radiant.etrip.fragment.PsgMapsFragmentD;
import com.radiant.etrip.fragment.RidesFragment;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DirectionDistance extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    ActivityDirectionDistanceBinding binding;
    private GoogleMap mMap;
    private MarkerOptions markerOptions;
    private Marker marker;
    private Polyline polyline;

    LatLng origin, dest;
    List<MarkerOptions> markerOptionsList = new ArrayList<>();
    List<Model> modelList = new ArrayList<>();

    String startPoint, endPoint, date, time, carType, seat;
    Double distance;

    FirebaseDatabase database;
    DatabaseReference referenceDriver, referenceCarpooler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDirectionDistanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String username = LoginActivity.getUsername();
        database = FirebaseDatabase.getInstance();
        referenceDriver = FirebaseDatabase.getInstance().getReference("Drivers");
        referenceCarpooler = FirebaseDatabase.getInstance().getReference("Users");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Double latValueStart = Double.parseDouble(bundle.getString("latitude_start"));
        Double lngValueStart = Double.parseDouble(bundle.getString("longitude_start"));
        Double latValueEnd = Double.parseDouble(bundle.getString("latitude_end"));
        Double lngValueEnd = Double.parseDouble(bundle.getString("longitude_end"));

        origin = new LatLng(latValueStart, lngValueStart);
        dest = new LatLng(latValueEnd, lngValueEnd);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        new FetchURL(DirectionDistance.this).execute(getUrl(origin, dest, "driving"),"driving");

        distance = getDistanceBetween(origin, dest);

        DecimalFormat df = new DecimalFormat("###.####");
        binding.distance.setText(String.valueOf(df.format(distance)) + " km");

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get start point
                Geocoder geocoder = new Geocoder(DirectionDistance.this);
                List<Address> list = new ArrayList<>();

                try {
                    list = geocoder.getFromLocation(latValueStart, lngValueStart, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startPoint = list.get(0).getAdminArea();

                // store into Firebase
                HelperCarpooler helperCarpooler = new HelperCarpooler(startPoint, endPoint, date, time, carType, distance);
                referenceCarpooler.child(username).child("Carpools").push().setValue(helperCarpooler).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(DirectionDistance.this, "Carpool Request Received", Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.activity_main);
                        ReplaceFragment(new RidesFragment());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DirectionDistance.this, "Failed" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void ReplaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        referenceDriver.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    modelList.add(model);
                    LatLng latLng = new LatLng(model.getLatitude(), model.getLongitude());
                    markerOptions = new MarkerOptions();
                    markerOptions.position(latLng)
                            .icon(bitmapDescriptorFromVector(DirectionDistance.this, R.drawable.drivers_24));
                    marker = mMap.addMarker(markerOptions);
                    markerOptionsList.add(markerOptions);
                }
                showAllMarkers();

                // adding on click listener to marker of google maps.
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        binding.layoutInfo.setVisibility(View.VISIBLE);
                        Toast.makeText(DirectionDistance.this, "Please wait...", Toast.LENGTH_SHORT).show();

                        for(Model m : modelList){
                            if(marker.getPosition().latitude == m.getLatitude()){
                                endPoint = m.getDestination();
                                date = m.getDate();
                                time = m.getTime();
                                carType = m.getCarType();
                                seat = m.getSeat();

                                binding.destination.setText(endPoint);
                                binding.date.setText(date);
                                binding.time.setText(time);
                                binding.carType.setText(carType);
                                binding.seat.setText(seat);

                                binding.continueBtn.setVisibility(View.VISIBLE);
                            }
                        }

                        Toast.makeText(DirectionDistance.this, "Successful", Toast.LENGTH_SHORT).show();

                        return false;
                    }
                });

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        int LayoutSearch = binding.layoutInfo.getVisibility();
                        if (LayoutSearch == View.VISIBLE){
                            binding.layoutInfo.setVisibility(View.GONE);
                        }else{
                            if (LayoutSearch == View.GONE) {
                                binding.layoutInfo.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public Double getDistanceBetween(LatLng origin, LatLng dest) {
        if (origin == null || dest == null)
            return null;
        float[] result = new float[1];
        Location.distanceBetween(origin.latitude, origin.longitude,
                dest.latitude, dest.longitude, result);

        return (double) (result[0]/1000);
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

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onTaskDone(Object... values) {
        if(polyline != null)
            polyline.remove();

        polyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}