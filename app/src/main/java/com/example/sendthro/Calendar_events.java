package com.example.sendthro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Calendar_events extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calender_events, container, false);


        getFragmentManager().beginTransaction().replace(R.id.container, new CalendarFragment()).commit();


        BottomNavigationView topnav = v.findViewById(R.id.nav_calendar);
        topnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedF = null;

                switch (item.getItemId()){
                    case R.id.viewcalendar:
                        selectedF = new CalendarFragment();
                        break;

                    case R.id.viewevents:
                        selectedF = new EventsFragment();
                        break;
                }

                getFragmentManager().beginTransaction().replace(R.id.container, selectedF).commit();

                return true;
            }

    });
        return v;

    }
}


