package com.radiant.etrip.fragment;

import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radiant.etrip.HelperDriver;
import com.radiant.etrip.LoginActivity;
import com.radiant.etrip.R;
import com.radiant.etrip.databinding.FragmentAddLocationBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddLocationFragment extends Fragment {

    FragmentAddLocationBinding binding;
    DatabaseReference reference;
    String username, destination;
    double  distance;
    double destLatitude, destLongitude;
    ProgressDialog progressDialog;

    public AddLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        progressDialog = new ProgressDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddLocationBinding.inflate(inflater, container, false);
        username = LoginActivity.getUsername();

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Creating...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        Bundle bundle = this.getArguments();
        String latValue = bundle.getString("latitude");
        String lngValue = bundle.getString("longitude");
        binding.latitude2.setText(latValue);
        binding.longitude2.setText(lngValue);

        Spinner spinner = binding.spinner;
        String[] locations = {"Kuala Lumpur", "Selangor", "Kedah", "Penang", "Malacca", "Negeri Sembilan", "Johor", "Terengganu", "Kelantan", "Pahang", "Perak", "Perlis", "Labuan", "Putrajaya"};
        ArrayList<String> locationList = new ArrayList<>(Arrays.asList(locations));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, locationList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                destination = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });


        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the distance
                Geocoder geocoder = new Geocoder(getContext());
                List<Address> list = new ArrayList<>();
                try{
                    list = geocoder.getFromLocationName(destination, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (list.size() > 0){
                    Address address = list.get(0);
                    destLatitude = address.getLatitude();
                    destLongitude = address.getLongitude();
                }
                LatLng latLngDestination = new LatLng(destLatitude, destLongitude);

                distance = getDistanceBetween(new LatLng(Double.parseDouble(latValue),Double.parseDouble(lngValue)),
                        latLngDestination);

                double carbonSaved = CarbonSaved(distance);
                int points = PointAccumulator(carbonSaved);

                progressDialog.show();
                HelperDriver helperDriver = new HelperDriver();
                helperDriver.setLatitude(Double.parseDouble(latValue));
                helperDriver.setLongitude(Double.parseDouble(lngValue));
                helperDriver.setDestination(destination);
                helperDriver.setDate(binding.date.getText().toString().trim());
                helperDriver.setTime(binding.time.getText().toString().trim());
                helperDriver.setCarType(binding.carType.getText().toString().trim());
                helperDriver.setCarPlate(binding.carPlate.getText().toString().trim());
                helperDriver.setSeat(binding.seat.getText().toString().trim());
                helperDriver.setDistance(distance);
                helperDriver.setCarbonSaved(carbonSaved);
                helperDriver.setPoints(points);

                reference.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("pointsAccumulator").exists()) {
                            int pointsFromDB = snapshot.child("pointsAccumulator").getValue(int.class);
                            pointsFromDB = pointsFromDB + points;
                            reference.child(username).child("pointsAccumulator").setValue(pointsFromDB);
                        } else {
                            reference.child(username).child("pointsAccumulator").setValue(points);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                reference.child(username).child("Drivers").push().setValue(helperDriver).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();

                        Fragment fragment = new RidesFragment();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, fragment).commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed" + e.getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });

        return binding.getRoot();
    }

    public Double getDistanceBetween(LatLng origin, LatLng dest) {
        if (origin == null || dest == null)
            return null;
        float[] result = new float[1];
        Location.distanceBetween(origin.latitude, origin.longitude,
                dest.latitude, dest.longitude, result);

        return (double) (result[0]/1000);
    }

    public double CarbonSaved(double distance) {
        double carbonSavedPerKm = 0.192; // 0.192kg of carbon saved per kilometer travelled
        double carbonSaved = distance * carbonSavedPerKm;
        return carbonSaved;
    }

    public int PointAccumulator(double carbonSaved) {
        int pointPerCarbonSaved = 2; // 2 points per kg of carbon saved
        int points = (int)carbonSaved * pointPerCarbonSaved;
        return points;
    }
}