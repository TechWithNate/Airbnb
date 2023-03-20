package com.tech.airbnb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.tech.airbnb.Fragments.HomeFragment;
import com.tech.airbnb.Fragments.InboxFragment;
import com.tech.airbnb.Fragments.ProfileFragment;
import com.tech.airbnb.Fragments.TripsFragment;
import com.tech.airbnb.Fragments.WishlistFragment;

public class Home extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;


    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentTransaction(new HomeFragment());

        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.explore_menu:
                    fragmentTransaction(new HomeFragment());
                    break;
                case R.id.wish_menu:
                    fragmentTransaction(new WishlistFragment());
                    break;
                case R.id.trips_menu:
                    fragmentTransaction(new TripsFragment());
                    break;
                case R.id.inbox_menu:
                    fragmentTransaction(new InboxFragment());
                    break;
                case R.id.profile_menu:
                    fragmentTransaction(new ProfileFragment());
                    break;
                default:
                    break;
            }

            return true;
        });
    }

    private void fragmentTransaction(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

}