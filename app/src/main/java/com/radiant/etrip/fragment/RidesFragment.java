package com.radiant.etrip.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.radiant.etrip.R;
import com.radiant.etrip.databinding.FragmentRidesBinding;

public class RidesFragment extends Fragment {

    FragmentRidesBinding binding;

    public RidesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRidesBinding.inflate(inflater, container, false);

        binding.carpooler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PsgMapsFragmentA();
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment)
                        .addToBackStack("name")
                        .setReorderingAllowed(true)
                        .commit();
            }
        });

        binding.driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new DriversMapsFragment();
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
}