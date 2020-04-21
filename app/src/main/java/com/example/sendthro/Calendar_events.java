package com.example.sendthro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Calendar_events extends Fragment{

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calender_events, container, false);

        mAuth = FirebaseAuth.getInstance();

//        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton4);
//        FloatingActionButton smsfab = (FloatingActionButton) view.findViewById(R.id.smsfab);
//        FloatingActionButton wtsfab = (FloatingActionButton) view.findViewById(R.id.wtsfab);
//        FloatingActionButton emailfab = (FloatingActionButton) view.findViewById(R.id.emailfab);
//
//        final RelativeLayout wtslayout = (RelativeLayout) view.findViewById(R.id.wtslayout);
//        final RelativeLayout smslayout = (RelativeLayout) view.findViewById(R.id.smslayout);
//        final RelativeLayout emaillayout = (RelativeLayout) view.findViewById(R.id.emaillayout);
//
//
//        smslayout.setVisibility(View.INVISIBLE);
//        emaillayout.setVisibility(View.INVISIBLE);
//        wtslayout.setVisibility(View.INVISIBLE);
//
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (wtslayout.getVisibility() == View.VISIBLE && smslayout.getVisibility() == View.VISIBLE
//                        && emaillayout.getVisibility() == View.VISIBLE) {
//
//                    wtslayout.setVisibility(View.GONE);
//                    smslayout.setVisibility(View.GONE);
//                    emaillayout.setVisibility(View.GONE);
//
//                } else {
//                    wtslayout.setVisibility(View.VISIBLE);
//                    smslayout.setVisibility(View.VISIBLE);
//                    emaillayout.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//
//        smsfab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mAuth.getCurrentUser() == null) {
//                    Intent NewUSer = new Intent(getActivity(), MainActivity.class);
//                    startActivity(NewUSer);
//                } else {
//                    Intent Do = new Intent(getActivity(), CreateSmsScheduleActivity.class);
//                    startActivity(Do);
//                }
//            }
//        });
//
//
//        wtsfab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mAuth.getCurrentUser() == null) {
//                    Intent NewUSer = new Intent(getActivity(), MainActivity.class);
//                    startActivity(NewUSer);
//                } else {
//                    Intent Do = new Intent(getActivity(), Newmessage.class);
//                    startActivity(Do);
//                }
//            }
//        });
//
//
//        emailfab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mAuth.getCurrentUser() == null) {
//                    Intent NewUSer = new Intent(getActivity(), MainActivity.class);
//                    startActivity(NewUSer);
//                } else {
//                    Intent Do = new Intent(getActivity(), Newmessage.class);
//                    startActivity(Do);
//                }
//            }
//        });


        getFragmentManager().beginTransaction().replace(R.id.container, new CalendarFragment()).commit();


        BottomNavigationView topnav = view.findViewById(R.id.nav_calendar);
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
        return view;

    }
}


