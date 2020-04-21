package com.example.sendthro;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class HomePage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mAuth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.bnview);
        bottomNavigationView.setOnNavigationItemSelectedListener(NavLi);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new scheduledmsg()).commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener NavLi =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    Menu menu = bottomNavigationView.getMenu();

                    switch (item.getItemId()){
                        case R.id.nav_time:
                            selectedFragment = new scheduledmsg();
                            menu.getItem(0).setTitle("Scheduled");
                            menu.getItem(1).setTitle(null);
                            menu.getItem(2).setTitle(null);
                            break;


                        case R.id.nav_calendar:
                            selectedFragment = new Calendar_events();
                            menu.getItem(0).setTitle(null);
                            menu.getItem(1).setTitle("Calendar");
                            menu.getItem(2).setTitle(null);
                            break;

                        case R.id.nav_setting:
                            selectedFragment = new settings();
                            menu.getItem(0).setTitle(null);
                            menu.getItem(1).setTitle(null);
                            menu.getItem(2).setTitle("Settings");
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}
