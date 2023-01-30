package com.radiant.etrip.fragment;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radiant.etrip.HelperReward;
import com.radiant.etrip.LoginActivity;
import com.radiant.etrip.R;
import com.radiant.etrip.databinding.FragmentRewardBinding;

import java.util.Calendar;

public class RewardFragment extends Fragment {

    FragmentRewardBinding binding;
    String username, currentDate, reward;
    DatabaseReference reference;
    int points = 0;

    public RewardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        username = LoginActivity.getUsername();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(username);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRewardBinding.inflate(inflater, container, false);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("pointsAccumulator").exists()) {
                    points = snapshot.child("pointsAccumulator").getValue(int.class);
                    binding.pointAccumulator.setText(points + "Pts");
                } else {
                    binding.pointAccumulator.setText(points + "Pts");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        binding.cashRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = 500;

                if(points >= price) {
                    points = points - price;
                    currentDate = getCurrentDate();
                    reward = binding.cashText.getText().toString().trim();

                    HelperReward helperReward = new HelperReward();
                    helperReward.setSpent(price);
                    helperReward.setReward(reward);
                    helperReward.setDate(currentDate);
                    helperReward.setBalance(points);

                    reference.child("pointsAccumulator").setValue(points);
                    reference.child("transactionHistory").push().setValue(helperReward);
                    Toast.makeText(getContext(), "Successfully redeemed, view it in transaction history", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Unable to redeem due to insufficient points", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.travelRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = 500;

                if(points >= price) {
                    points = points - price;
                    currentDate = getCurrentDate();
                    reward = binding.travelText.getText().toString().trim();

                    HelperReward helperReward = new HelperReward();
                    helperReward.setSpent(price);
                    helperReward.setReward(reward);
                    helperReward.setDate(currentDate);
                    helperReward.setBalance(points);

                    reference.child("pointsAccumulator").setValue(points);
                    reference.child("transactionHistory").push().setValue(helperReward);
                    Toast.makeText(getContext(), "Successfully redeemed, view it in transaction history", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Unable to redeem due to insufficient points", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.movieRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = 400;

                if(points >= price) {
                    points = points - price;
                    currentDate = getCurrentDate();
                    reward = binding.movieText.getText().toString().trim();

                    HelperReward helperReward = new HelperReward();
                    helperReward.setSpent(price);
                    helperReward.setReward(reward);
                    helperReward.setDate(currentDate);
                    helperReward.setBalance(points);

                    reference.child("pointsAccumulator").setValue(points);
                    reference.child("transactionHistory").push().setValue(helperReward);
                    Toast.makeText(getContext(), "Successfully redeemed, view it in transaction history", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Unable to redeem due to insufficient points", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.themeparkRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = 800;

                if(points >= price) {
                    points = points - price;
                    currentDate = getCurrentDate();
                    reward = binding.themeparkText.getText().toString().trim();

                    HelperReward helperReward = new HelperReward();
                    helperReward.setSpent(price);
                    helperReward.setReward(reward);
                    helperReward.setDate(currentDate);
                    helperReward.setBalance(points);

                    reference.child("pointsAccumulator").setValue(points);
                    reference.child("transactionHistory").push().setValue(helperReward);
                    Toast.makeText(getContext(), "Successfully redeemed, view it in transaction history", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Unable to redeem due to insufficient points", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.bottleRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = 300;

                if(points >= price) {
                    points = points - price;
                    currentDate = getCurrentDate();
                    reward = binding.bottleText.getText().toString().trim();

                    HelperReward helperReward = new HelperReward();
                    helperReward.setSpent(price);
                    helperReward.setReward(reward);
                    helperReward.setDate(currentDate);
                    helperReward.setBalance(points);

                    reference.child("pointsAccumulator").setValue(points);
                    reference.child("transactionHistory").push().setValue(helperReward);
                    Toast.makeText(getContext(), "Successfully redeemed, view it in transaction history", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Unable to redeem due to insufficient points", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.powerbankRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = 600;

                if(points >= price) {
                    points = points - price;
                    currentDate = getCurrentDate();
                    reward = binding.powerbankText.getText().toString().trim();

                    HelperReward helperReward = new HelperReward();
                    helperReward.setSpent(price);
                    helperReward.setReward(reward);
                    helperReward.setDate(currentDate);
                    helperReward.setBalance(points);

                    reference.child("pointsAccumulator").setValue(points);
                    reference.child("transactionHistory").push().setValue(helperReward);
                    Toast.makeText(getContext(), "Successfully redeemed, view it in transaction history", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Unable to redeem due to insufficient points", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RewardTransactionFragment();
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment)
                        .addToBackStack("name")
                        .setReorderingAllowed(true)
                        .commit();
            }
        });

        return binding.getRoot();
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(calendar.getTime());

        return currentDate;
    }
}