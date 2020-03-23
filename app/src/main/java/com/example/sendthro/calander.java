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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class calander extends LinearLayout {

    ImageButton Nextbutton, Previousebutton;
    TextView currentDate;
    GridView calendarGridView ;

    private static final int MAX_CALENDAR_DAYES = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;

    List<Date> dates = new ArrayList<>();
    List<Events> eventsList = new ArrayList<>();

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calander, container, false );
    }

    public calander(Context context) {
        super(context);
    }

    public calander(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

}
