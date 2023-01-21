package com.radiant.etrip.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.radiant.etrip.databinding.FragmentCarbonTrackerDetailsBinding;

public class CarbonTrackerDetailsFragment extends Fragment {

    FragmentCarbonTrackerDetailsBinding binding;

    public CarbonTrackerDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle!=null) {
            binding.detailDest.setText(bundle.getString("destination"));
            binding.detailOrigin.setText(bundle.getString("origin"));
            binding.detailDistance.setText(bundle.getString("distance"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCarbonTrackerDetailsBinding.inflate(inflater, container, false);

        return(binding.getRoot());
    }
}