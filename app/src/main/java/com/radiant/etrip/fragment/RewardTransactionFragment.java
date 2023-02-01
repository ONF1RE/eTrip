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
import com.radiant.etrip.AdapterRecentCarpooler;
import com.radiant.etrip.AdapterRecentDriver;
import com.radiant.etrip.AdapterRewardTransaction;
import com.radiant.etrip.HelperCarpooler;
import com.radiant.etrip.HelperDriver;
import com.radiant.etrip.HelperReward;
import com.radiant.etrip.LoginActivity;
import com.radiant.etrip.databinding.FragmentRecentRidesBinding;
import com.radiant.etrip.databinding.FragmentRewardTransactionBinding;

import java.util.ArrayList;

public class RewardTransactionFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference reference;
    AdapterRewardTransaction adapterRewardTransaction;
    ArrayList<HelperReward> list;;
    String username;

    FragmentRewardTransactionBinding binding;

    public RewardTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = LoginActivity.getUsername();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(username).child("transactionHistory");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRewardTransactionBinding.inflate(inflater, container, false);

        recyclerView = binding.rewardTransactionList;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        adapterRewardTransaction = new AdapterRewardTransaction(getContext(), list);
        recyclerView.setAdapter(adapterRewardTransaction);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HelperReward helperReward = dataSnapshot.getValue(HelperReward.class);
                    list.add(helperReward);
                }
                adapterRewardTransaction.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}