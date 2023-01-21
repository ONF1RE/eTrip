package com.radiant.etrip.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.radiant.etrip.LoginActivity;
import com.radiant.etrip.R;
import com.radiant.etrip.databinding.FragmentEditProfileBinding;

public class EditProfileFragment extends Fragment {

    FragmentEditProfileBinding binding;
    String name, email, username, password;
    DatabaseReference reference;

    public EditProfileFragment() {
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
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);

        username = LoginActivity.getUsername();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        Query userDatabase = reference.orderByChild("username").equalTo(username);

        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.child(username).child("name").getValue(String.class);
                email = snapshot.child(username).child("email").getValue(String.class);
                password = snapshot.child(username).child("password").getValue(String.class);

                binding.textUsername.setText(username);
                binding.editName.setText(name);
                binding.editEmail.setText(email);
                binding.editPswd.setText(password);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.textUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Username cannot be changed", Toast.LENGTH_SHORT).show();
            }
        });

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNameChanged() || isEmailChanged() || isPasswordChanged()){
                    Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(),"No Changes Found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return binding.getRoot();
    }

    public boolean isNameChanged() {
        if(!name.equals(binding.editName.getText().toString())){
            reference.child(username).child("name").setValue(binding.editName.getText().toString());
            name = binding.editName.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmailChanged() {
        if(!email.equals(binding.editEmail.getText().toString())){
            reference.child(username).child("email").setValue(binding.editEmail.getText().toString());
            email = binding.editEmail.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    public boolean isPasswordChanged() {
        if(!password.equals(binding.editPswd.getText().toString())) {
            reference.child(username).child("password").setValue(binding.editPswd.getText().toString());
            password = binding.editPswd.getText().toString();
            return true;
        } else {
            return false;
        }
    }
}