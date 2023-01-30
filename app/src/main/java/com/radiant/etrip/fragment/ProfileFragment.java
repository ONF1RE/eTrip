package com.radiant.etrip.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.radiant.etrip.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    DatabaseReference reference;
    String username, name, email, password;

    FragmentProfileBinding binding;

    public ProfileFragment() {
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

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        binding.titleUsername.setText(username);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email = snapshot.child("email").getValue(String.class);
                name = snapshot.child("name").getValue(String.class);
                password = snapshot.child("password").getValue(String.class);

                binding.email.setText(email);
                binding.name.setText(name);
                binding.password.setText(password);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditProfileFragment();
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment)
                        .addToBackStack("name")
                        .setReorderingAllowed(true)
                        .commit();
            }
        });

        binding.deleteButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new DeleteProfileFragment();
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment)
                        .addToBackStack("name")
                        .setReorderingAllowed(true)
                        .commit();
            }
        }));

        return binding.getRoot();
    }


}