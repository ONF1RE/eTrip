package com.radiant.etrip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.radiant.etrip.databinding.ActivityMainBinding;
import com.radiant.etrip.fragment.CarbonTrackerFragment;
import com.radiant.etrip.fragment.HomeFragment;
import com.radiant.etrip.fragment.DriversMapsFragment;
import com.radiant.etrip.fragment.ProfileFragment;
import com.radiant.etrip.fragment.PsgMapsFragmentB;
import com.radiant.etrip.fragment.RewardFragment;
import com.radiant.etrip.fragment.RidesFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ReplaceFragment(new RidesFragment());

        binding.navBottomBar.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.rides:
                        ReplaceFragment(new RidesFragment());
                        break;
                    case R.id.tracker:
                        ReplaceFragment(new CarbonTrackerFragment());
                        break;
                    case R.id.profile:
                        ReplaceFragment(new ProfileFragment());
                        break;
                }
            }
        });
    }

    private void ReplaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}