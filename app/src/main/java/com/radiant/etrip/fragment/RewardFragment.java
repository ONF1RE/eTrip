package com.radiant.etrip.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.radiant.etrip.R;
import com.radiant.etrip.databinding.FragmentRewardBinding;

public class RewardFragment extends Fragment {

    FragmentRewardBinding binding;

    public RewardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRewardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}