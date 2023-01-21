package com.radiant.etrip.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.radiant.etrip.Model;
import com.radiant.etrip.R;
import com.radiant.etrip.databinding.FragmentAddLocationBinding;
import com.radiant.etrip.databinding.FragmentProfileBinding;

public class AddLocationFragment extends Fragment {

    FragmentAddLocationBinding binding;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;

    public AddLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddLocationBinding.inflate(inflater, container, false);

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

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                Model post = new Model();
                post.setLatitude(Double.parseDouble(binding.latitude2.getText().toString().trim()));
                post.setLongitude(Double.parseDouble(binding.longitude2.getText().toString().trim()));
                post.setDestination(binding.destination.getText().toString().trim());
                post.setDate(binding.date.getText().toString().trim());
                post.setTime(binding.time.getText().toString().trim());
                post.setCarType(binding.carType.getText().toString().trim());
                post.setSeat(binding.seat.getText().toString().trim());

                firebaseDatabase.getReference().child("Drivers").push().setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();

                        Fragment fragment = new HomeFragment();
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
}