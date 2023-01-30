package com.radiant.etrip.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radiant.etrip.LoginActivity;
import com.radiant.etrip.R;
import com.radiant.etrip.databinding.FragmentCarbonTrackerBinding;
import com.radiant.etrip.databinding.FragmentCarbonTrackerDetailsBinding;

public class CarbonTrackerFragment extends Fragment {

    FragmentCarbonTrackerBinding binding;
    String username;
    DatabaseReference referenceDriver, referencePassenger;
    double totalDistance, totalCarbonSaved, totalPoints;

    public CarbonTrackerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = LoginActivity.getUsername();
        referenceDriver = FirebaseDatabase.getInstance().getReference("Users").child(username).child("Drivers");
        referencePassenger = FirebaseDatabase.getInstance().getReference("Users").child(username).child("Carpools");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCarbonTrackerBinding.inflate(inflater, container, false);

        referenceDriver.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    double driverDistance = dataSnapshot.child("distance").getValue(double.class);
                    double driverCarbonSaved = dataSnapshot.child("carbonSaved").getValue(double.class);
                    int driverPoint = dataSnapshot.child("points").getValue(int.class);

                    totalDistance = totalDistance + driverDistance;
                    totalCarbonSaved = totalCarbonSaved + driverCarbonSaved;
                    totalPoints = totalPoints + driverPoint;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        referencePassenger.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    double passengerDistance = dataSnapshot.child("distance").getValue(double.class);
                    double passengerCarbonSaved = dataSnapshot.child("carbonSaved").getValue(double.class);
                    int passengerPoint = dataSnapshot.child("points").getValue(int.class);

                    totalDistance = totalDistance + passengerDistance;
                    totalCarbonSaved = totalCarbonSaved + passengerCarbonSaved;
                    totalPoints = totalPoints + passengerPoint;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        final Handler carbon_handler = new Handler();
        final Handler distance_handler = new Handler();
        final Handler point_handler = new Handler();

        carbon_handler.postDelayed(new Runnable() {
            int i = 0;
            @Override
            public void run() {
                if(i<=totalCarbonSaved) {
                    binding.progressText1.setText(""+i+" kg");
                    binding.progressBar1.setProgress(i);
                    i++;
                    carbon_handler.postDelayed(this,1);
                }else {
                    carbon_handler.removeCallbacks(this);
                }
            }
        }, 10);

        distance_handler.postDelayed(new Runnable() {
            int i = 0;
            @Override
            public void run() {
                if(i<=totalDistance) {
                    binding.progressText2.setText(""+i+" km");
                    binding.progressBar2.setProgress(i);
                    i++;
                    distance_handler.postDelayed(this,1);
                }else {
                    distance_handler.removeCallbacks(this);
                }
            }
        }, 10);

        point_handler.postDelayed(new Runnable() {
            int i = 0;
            @Override
            public void run() {
                if(i<=totalPoints) {
                    binding.progressText3.setText(""+i+" pts");
                    binding.progressBar3.setProgress(i);
                    i++;
                    point_handler.postDelayed(this,1);
                }else {
                    point_handler.removeCallbacks(this);
                }
            }
        }, 10);

        binding.history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CarbonTrackerDetailsFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.container, fragment)
                        .addToBackStack("name")
                        .setReorderingAllowed(true)
                        .commit();
            }
        });

        return(binding.getRoot());
    }
}