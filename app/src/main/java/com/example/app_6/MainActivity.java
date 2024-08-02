package com.example.app_6;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.startServiceButton.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(MainActivity.this, TimeService.class);
            startService(serviceIntent);
        });

        binding.stopServiceButton.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(MainActivity.this, TimeService.class);
            stopService(serviceIntent);
        });
    }
}