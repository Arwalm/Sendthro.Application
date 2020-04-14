package com.example.sendthro;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class QUEvents extends AppCompatActivity {

    ImageView Notification_On;
    private PendingIntent alarmIntent;
    private AlarmManager alarmManager;
    boolean isPlay = false;
    int year;

    ListView listView;
    String mTitle[] = {"Postponement of The Semester", "Requests to Amending Schedules", "Beginning of The Semester", "Withdrawal From a Course",
                        "Transfer From Inside and Outside The University", "Beginning of Final Exams" , "End-year break for students",
                        "GPA Announcement" ,  "Distribution of Graduation Documents"};

    String mDate[] = {"05/01/2020-18/01/2020", "24/11/2019-11/01/2020", "19/01/2020", "09/02/2020-07/02/2020",
                       "01/03/2020-26/03/2020", "03/05/2020-14/05/2020" , "14/05/2020" ,
                       "17/05/2020", "14/06/2020" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_u_events);

        listView = findViewById(R.id.list);
        Notification_On = findViewById(R.id.Notification_On);


        QuAdapter adapter = new QuAdapter(this, mTitle, mDate);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(QUEvents.this, "Postponement of The Semester", Toast.LENGTH_SHORT).show();
                }
                if (position == 1) {
                    Toast.makeText(QUEvents.this, "Requests to Amending Schedules", Toast.LENGTH_SHORT).show();
                }
                if (position == 2) {
                    Toast.makeText(QUEvents.this, "Beginning of The Semester", Toast.LENGTH_SHORT).show();
                }
                if (position == 3) {
                    Toast.makeText(QUEvents.this, "Withdrawal From a Course", Toast.LENGTH_SHORT).show();
                }
                if (position == 4) {
                    Toast.makeText(QUEvents.this, "Transfer From Inside and Outside The University", Toast.LENGTH_SHORT).show();
                }
                if (position == 5) {
                    Toast.makeText(QUEvents.this, "Beginning of Final Exams", Toast.LENGTH_SHORT).show();
                }
                if (position == 6) {
                    Toast.makeText(QUEvents.this, "End-year break for students", Toast.LENGTH_SHORT).show();
                }
                if (position == 7) {
                    Toast.makeText(QUEvents.this, "GPA Announcement", Toast.LENGTH_SHORT).show();
                }
                else if (position == 8) {
                    Toast.makeText(QUEvents.this, "Distribution of Graduation Documents", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Calendar testCalendar = Calendar.getInstance();
        testCalendar.set(Calendar.HOUR_OF_DAY, 11);
        testCalendar.set(Calendar.MINUTE, 51);
        testCalendar.set(Calendar.SECOND, 00);
        testCalendar.set(Calendar.MONTH, Calendar.APRIL);
        testCalendar.set(Calendar.DAY_OF_MONTH, 13);
        testCalendar.set(Calendar.YEAR, 2020);
        testCalendar.set(Calendar.AM_PM, Calendar.AM);
        testCalendar.setTimeInMillis(System.currentTimeMillis());
        year = testCalendar.get(Calendar.YEAR);
        testCalendar.set(Calendar.YEAR, year + 1);

        Intent intent1 = new Intent(getApplicationContext(), Notification_Reciver.class);

        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(),
                100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

//                alarmIntent = PendingIntent.getBroadcast(getApplicationContext(),
//                        0, intent1, 0);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, testCalendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY * 365, pendingIntent1);
//                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, testCalendar.getTimeInMillis(),
//                        0, alarmIntent);

        //           'Ramadan Event'               //
        Calendar RamadanEvent = Calendar.getInstance();
        RamadanEvent.set(Calendar.HOUR_OF_DAY, 00);
        RamadanEvent.set(Calendar.MINUTE, 00);
        RamadanEvent.set(Calendar.SECOND, 00);
        RamadanEvent.set(Calendar.MONTH, Calendar.APRIL);
        RamadanEvent.set(Calendar.DAY_OF_MONTH, 24);
        RamadanEvent.set(Calendar.YEAR, 2020);
        RamadanEvent.set(Calendar.AM_PM, Calendar.AM);
        RamadanEvent.setTimeInMillis(System.currentTimeMillis());
        year = RamadanEvent.get(Calendar.YEAR);
        RamadanEvent.set(Calendar.YEAR, year + 1);

        Intent intent = new Intent(getApplicationContext(), Notification_Reciver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(),
                0, intent, 0);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, RamadanEvent.getTimeInMillis(),
                0, alarmIntent);


       //             Notification Button                  //

        Notification_On = findViewById(R.id.Notification_On);
        //Notification_On.setBackgroundResource(R.drawable.ic_notificationon);

        Notification_On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isPlay){
                    v.setBackgroundResource(R.drawable.ic_notificationon);
                }
                else {
                    v.setBackgroundResource(R.drawable.ic_notificationofff);

                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    if (alarmManager != null) {
                        alarmManager.cancel(alarmIntent);
                    }
                }
                isPlay = !isPlay; // reverse
            }

        });
    }

    class QuAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rdate[];

        QuAdapter(Context c, String title[], String date[]) {
            super(c, R.layout.quevents_row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rdate = date;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.quevents_row, parent, false);
            TextView EventTitle = row.findViewById(R.id.textView1);
            TextView EventDate = row.findViewById(R.id.textView2);

            EventTitle.setText(rTitle[position]);
            EventDate.setText(rdate[position]);

            return row;
        }
    }
}

