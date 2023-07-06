package com.example.loginregister;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.loginregister.databinding.ActivityMaintainerBinding;

public class maintainer extends AppCompatActivity {

    private ActivityMaintainerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMaintainerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_gallery, R.id.navigation_notifications, R.id.nav_slideshow)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_maintainer);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}