package com.example.sendthro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class settingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bnview);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()){
                    case R.id.nav_time:
                        Intent N1 = new Intent(settingsActivity.this, ScheduledmsgActivity.class);
                        startActivity(N1);
                        break;

                    case R.id.nav_archive:
                        Intent N2 = new Intent(settingsActivity.this, archivedmsgActivity.class);
                        startActivity(N2);
                        break;

                    case R.id.nav_calendar:
                        Intent N3 = new Intent(settingsActivity.this, calenderActivity.class);
                        startActivity(N3);
                        break;

                    case R.id.nav_setting:
                        Intent N4 = new Intent(settingsActivity.this, settingsActivity.class);
                        startActivity(N4);
                        break;
                }

                return false;
            }
        });
    }
}
