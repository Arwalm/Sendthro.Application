package com.example.sendthro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class calenderActivity extends LinearLayout {


    ImageButton Nextbutton, Previousebutton;
    TextView CurrentDate;
    GridView calendarGridView ;

    private static final int MAX_CALENDAR_DAYES = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy",Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);

    List<Date> dates = new ArrayList<>();
    List<Events> eventsList = new ArrayList<>();


    public calenderActivity(Context context) {
        super(context);
    }

    public calenderActivity(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        IntializeLayout();

        Previousebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                SetUpCalender();
            }
        });

        Nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, 1);
                SetUpCalender();
            }
        });
    }


    public calenderActivity(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void IntializeLayout(){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_calander, this);
        Nextbutton = view.findViewById(R.id.nextButton);
        Previousebutton = view.findViewById(R.id.previousButton);
        CurrentDate = view.findViewById(R.id.currentDate);
        calendarGridView = view.findViewById(R.id.calendarGridView);

    }

    private void SetUpCalender(){
        String currentDate = dateFormat.format(calendar.getTime());
        CurrentDate.setText(currentDate);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bnview);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()){
                    case R.id.nav_time:
                        Intent N1 = new Intent(getContext(), ScheduledmsgActivity.class);
                        getContext().startActivity(N1);
                        break;

                    case R.id.nav_archive:
                        Intent N2 = new Intent(getContext(), archivedmsgActivity.class);
                        getContext().startActivity(N2);
                        break;

                    case R.id.nav_calendar:
                        Intent N3 = new Intent(getContext(), calenderActivity.class);
                        getContext().startActivity(N3);
                        break;

                    case R.id.nav_setting:
                        Intent N4 = new Intent(getContext(), settingsActivity.class);
                        getContext().startActivity(N4);
                        break;
                }

                return false;
            }
        });
    }
}
