package com.example.sendthro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.BottomNavigationViewKt;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton4);
        FloatingActionButton smsfab = (FloatingActionButton) findViewById(R.id.smsfab);
        FloatingActionButton wtsfab = (FloatingActionButton) findViewById(R.id.wtsfab);
        FloatingActionButton emailfab = (FloatingActionButton) findViewById(R.id.emailfab);

        final RelativeLayout wtslayout = (RelativeLayout)  findViewById(R.id.wtslayout);
        final RelativeLayout smslayout = (RelativeLayout)  findViewById(R.id.smslayout);
        final RelativeLayout emaillayout = (RelativeLayout)  findViewById(R.id.emaillayout);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wtslayout.getVisibility() == View.VISIBLE && smslayout.getVisibility() == View.VISIBLE
                        && emaillayout.getVisibility() == View.VISIBLE){

                    wtslayout.setVisibility(View.GONE);
                    smslayout.setVisibility(View.GONE);
                    emaillayout.setVisibility(View.GONE);
                } else {
                    wtslayout.setVisibility(View.VISIBLE);
                    smslayout.setVisibility(View.VISIBLE);
                    emaillayout.setVisibility(View.VISIBLE);
                }
            }
        });


        smsfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Do = new Intent(HomePage.this, Newmessage.class);
                startActivity(Do);
            }
        });


        wtsfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Do = new Intent(HomePage.this, Newmessage.class);
                startActivity(Do);
            }
        });


        emailfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Do = new Intent(HomePage.this, Newmessage.class);
                startActivity(Do);
            }
        });


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

                        case R.id.nav_archive:
                            selectedFragment = new archivedmsg();
                            break;

                        case R.id.nav_calendar:
                            selectedFragment = new calander();
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