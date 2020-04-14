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

public class SaudiEvents extends AppCompatActivity {

    ImageView Notification_On;
    private PendingIntent alarmIntent;
    private AlarmManager alarmManager;
    boolean isPlay = false;

    ListView listView;
    String mTitle[] = {"Ramadan", "Eid AlFitr", "Arafat", "Eid AlAdha", "Ashura", "Saudi National Day"};
    String mDate[] = {"24/4/2020", "24/5/2020", "30/6/2020", "1/7/2020", "29/8/2020", "23/9/2020"};
    int year;
    private String mActive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saudi_events);

        listView = findViewById(R.id.list);
//        Notification_Off = findViewById(R.id.Notification_Off);
//
//        mActive = "true";
//        // Setup up active buttons
//        if (mActive.equals("false")) {
//            Notification_Off.setVisibility(View.VISIBLE);
//            Notification_On.setVisibility(View.GONE);
//
//        } else if (mActive.equals("true")) {
//            Notification_Off.setVisibility(View.GONE);
//            Notification_On.setVisibility(View.VISIBLE);
//        }

        MyAdapter adapter = new MyAdapter(this, mTitle, mDate);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 ){
                    Toast.makeText(SaudiEvents.this, "Ramadan Kareem", Toast.LENGTH_SHORT).show();
                }
                if (position == 1 ){
                    Toast.makeText(SaudiEvents.this, "Eid Fitr Mubarak", Toast.LENGTH_SHORT).show();
                }
                if (position == 2 ){
                    Toast.makeText(SaudiEvents.this, "May Allah Accept it From You", Toast.LENGTH_SHORT).show();
                }
                if (position == 3 ){
                    Toast.makeText(SaudiEvents.this, "Eid Adha Mubarak", Toast.LENGTH_SHORT).show();
                }
                if (position == 4 ){
                    Toast.makeText(SaudiEvents.this, "May Allah Accept it From You", Toast.LENGTH_SHORT).show();
                }
                else if (position == 5 ){
                    Toast.makeText(SaudiEvents.this, "Happy National Day!", Toast.LENGTH_SHORT).show();
                }
            }
        });

                Calendar testCalendar = Calendar.getInstance();
                testCalendar.set(Calendar.HOUR_OF_DAY, 8);
                testCalendar.set(Calendar.MINUTE, 30);
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

                Notification_On = findViewById(R.id.Notification_On);
                Notification_On.setBackgroundResource(R.drawable.ic_notificationon);

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


//        Notification_Off.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Notification_On = findViewById(R.id.Notification_On);
//                Notification_Off = findViewById(R.id.Notification_Off);
//
//                Notification_On.setVisibility(View.GONE);
//                Notification_Off.setVisibility(View.VISIBLE);
//                mActive = "false";
//
//                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//                if (alarmManager != null) {
//                    alarmManager.cancel(alarmIntent);
//                }
//            }
//        });
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rdate[];

        MyAdapter(Context c, String title[], String date[]) {
            super(c, R.layout.saudievents_row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rdate = date;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.saudievents_row, parent, false);
            TextView EventTitle = row.findViewById(R.id.textView1);
            TextView EventDate = row.findViewById(R.id.textView2);

            EventTitle.setText(rTitle[position]);
            EventDate.setText(rdate[position]);

            return row;
        }
    }
}

