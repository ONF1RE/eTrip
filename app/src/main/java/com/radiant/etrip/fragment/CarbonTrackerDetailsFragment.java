package com.radiant.etrip.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radiant.etrip.AdapterCarbonCarpooler;
import com.radiant.etrip.AdapterCarbonDriver;
import com.radiant.etrip.AdapterRecentCarpooler;
import com.radiant.etrip.AdapterRecentDriver;
import com.radiant.etrip.HelperCarpooler;
import com.radiant.etrip.HelperDriver;
import com.radiant.etrip.LoginActivity;
import com.radiant.etrip.databinding.FragmentCarbonTrackerDetailsBinding;
import com.radiant.etrip.databinding.FragmentRecentRidesBinding;

import java.util.ArrayList;

public class CarbonTrackerDetailsFragment extends Fragment {

    RecyclerView recyclerViewCarpooler, recyclerViewDriver;
    DatabaseReference referenceCarpooler, referenceDriver;
    AdapterCarbonCarpooler adapterCarbonCarpooler;
    ArrayList<HelperCarpooler> listCarpooler;
    AdapterCarbonDriver adapterCarbonDriver;
    ArrayList<HelperDriver> listDriver;
    String username;

    FragmentCarbonTrackerDetailsBinding binding;

    public CarbonTrackerDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = LoginActivity.getUsername();
        referenceCarpooler = FirebaseDatabase.getInstance().getReference("Users").child(username).child("Carpools");
        referenceDriver = FirebaseDatabase.getInstance().getReference("Users").child(username).child("Drivers");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCarbonTrackerDetailsBinding.inflate(inflater, container, false);
        CarpoolerActivity();
        DriverActivity();
        return binding.getRoot();
    }

    private void CarpoolerActivity() {
        recyclerViewCarpooler = binding.carpoolList;

        recyclerViewCarpooler.setHasFixedSize(true);
        recyclerViewCarpooler.setLayoutManager(new LinearLayoutManager(getContext()));

        listCarpooler = new ArrayList<>();
        adapterCarbonCarpooler = new AdapterCarbonCarpooler(getContext(), listCarpooler);
        recyclerViewCarpooler.setAdapter(adapterCarbonCarpooler);

        referenceCarpooler.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HelperCarpooler helperCarpooler = dataSnapshot.getValue(HelperCarpooler.class);
                    listCarpooler.add(helperCarpooler);
                }
                adapterCarbonCarpooler.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void DriverActivity() {
        recyclerViewDriver = binding.driverList;

        recyclerViewDriver.setHasFixedSize(true);
        recyclerViewDriver.setLayoutManager(new LinearLayoutManager(getContext()));

        listDriver = new ArrayList<>();
        adapterCarbonDriver = new AdapterCarbonDriver(getContext(), listDriver);
        recyclerViewDriver.setAdapter(adapterCarbonDriver);

        referenceDriver.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HelperDriver helperDriver = dataSnapshot.getValue(HelperDriver.class);
                    listDriver.add(helperDriver);
                }
                adapterCarbonDriver.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}