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
import com.radiant.etrip.HelperCarpooler;
import com.radiant.etrip.LoginActivity;
import com.radiant.etrip.MyAdapter;
import com.radiant.etrip.databinding.FragmentCarbonTrackerBinding;

import java.util.ArrayList;

public class CarbonTrackerFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference reference;
    MyAdapter myAdapter;
    ArrayList<HelperCarpooler> list;
    String username;

    FragmentCarbonTrackerBinding binding;

    public CarbonTrackerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCarbonTrackerBinding.inflate(inflater, container, false);
        username = LoginActivity.getUsername();

        recyclerView = binding.carpoolList;
        reference = FirebaseDatabase.getInstance().getReference("Users").child(username).child("Carpools");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(getContext(), list);
        recyclerView.setAdapter(myAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HelperCarpooler helperCarpooler = dataSnapshot.getValue(HelperCarpooler.class);
                    list.add(helperCarpooler);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}