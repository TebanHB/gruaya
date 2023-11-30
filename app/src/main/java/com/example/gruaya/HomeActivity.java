package com.example.gruaya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import com.example.gruaya.databinding.ActivityHomeBinding;
import android.view.MenuItem;
import com.example.gruaya.HomeFragment;
import com.example.gruaya.CarsFragment;
import com.example.gruaya.ServiceFragment;
import com.example.gruaya.ProfileFragment;
import com.example.gruaya.R;
public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    String token,tokenf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("token")) {
            token = intent.getStringExtra("token");
            tokenf = intent.getStringExtra("tokenf");
        }
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new ProfileFragment(),token,tokenf);
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment(),token, tokenf);
            } else if (itemId == R.id.cars) {
                replaceFragment(new CarsFragment(),token, tokenf);
            } else if (itemId == R.id.profile) {
                replaceFragment(new ProfileFragment(),token, tokenf);
            } else if (itemId == R.id.service) {
                replaceFragment(new ServiceFragment(),token, tokenf);
            }

            return true;
        });
        
    }
    private void replaceFragment(Fragment fragment, String token, String tokenf){
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        bundle.putString("tokenf", tokenf);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}