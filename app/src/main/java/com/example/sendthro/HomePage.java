package com.example.sendthro;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class HomePage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bnview);
        bottomNavigationView.setOnNavigationItemSelectedListener(NavLi);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new scheduledmsg()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener NavLi =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;


                    switch (item.getItemId()){
                        case R.id.nav_time:
                            selectedFragment = new scheduledmsg();
                            break;

                        case R.id.nav_calendar:
                            selectedFragment = new Calendar_events();
                            break;

                        case R.id.nav_setting:
                            selectedFragment = new settings();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
