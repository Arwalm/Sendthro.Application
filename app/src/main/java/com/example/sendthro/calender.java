package com.example.sendthro;

import android.content.Context;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class calender extends LinearLayout {

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


    public calender(Context context) {
        super(context);
    }

    public calender(Context context, @Nullable AttributeSet attrs) {
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


    public calender(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

    }
}
