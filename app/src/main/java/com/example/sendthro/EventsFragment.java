package com.example.sendthro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class EventsFragment extends Fragment {

    RelativeLayout user_events;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.eventsfragment, container, false);


        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new scheduledmsg()).commit();

        user_events = v.findViewById(R.id.user_events);

        user_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent user = new Intent (getActivity(), UserEventsActivity.class);
                startActivity(user);
            }
        });

        return v;
    }

}