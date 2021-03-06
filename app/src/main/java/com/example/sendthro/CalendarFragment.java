package com.example.sendthro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class CalendarFragment extends Fragment {

    MaterialCalendarView materialCalendarView;
    TextView newEvent;
    ImageView plussign;
    RelativeLayout new_event;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calendafragment, container, false);

        materialCalendarView = v.findViewById(R.id.calendarView);

        newEvent = v.findViewById(R.id.newEvent);
        plussign = v.findViewById(R.id.plussign);
        new_event = v.findViewById(R.id.new_event);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(CalendarDay.from(1900, 1, 1))
                .setMaximumDate(CalendarDay.from(2100, 12, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.setDateSelected(CalendarDay.today(), true);


        //New Event Creation
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Intent newevent = new Intent(getActivity(), AddReminderActivity.class);
                startActivity(newevent);
            }
        });

        new_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create_event = new Intent(getActivity(), AddReminderActivity.class);
                startActivity(create_event);
            }
        });

        return v;

    }

}

