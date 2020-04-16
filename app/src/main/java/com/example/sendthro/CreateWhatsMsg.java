package com.example.sendthro;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;


import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateWhatsMsg  extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.btnSetSchedule)
    Button btnSetSchedule;
    @BindView(R.id.textViewDate)
    TextView textViewDate;
    @BindView(R.id.textViewTime)
    TextView textViewTime;
    @BindView(R.id.relativeLayoutSelectTime)
    RelativeLayout relativeLayoutSelectTime;
    @BindView(R.id.relativeLayoutSelectDate)
    RelativeLayout relativeLayoutSelectDate;
    Calendar calendar;
    @BindView(R.id.editTextMessage)
    EditText editTextMessage;
    @BindView(R.id.editTextToRecipient)
    EditText editTextToRecipient;
    AlarmManager alarmManager;
    Button addto;

    int mHour, mMinute, mYear, mMonth, mDay;
    SmsDatabaseHelper databaseHelper;
    private static final int PICK_CONTACT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_whats_msg);
        ButterKnife.bind(this);
        calendar = Calendar.getInstance();

        btnSetSchedule = findViewById(R.id.btnSetSchedule);
        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);
        relativeLayoutSelectTime = findViewById(R.id.relativeLayoutSelectTime);
        relativeLayoutSelectDate = findViewById(R.id.relativeLayoutSelectDate);
        editTextMessage = findViewById(R.id.editTextMessage);
        editTextToRecipient = findViewById(R.id.editTextToRecipient);
        addto = findViewById(R.id.addto);

        databaseHelper = new SmsDatabaseHelper(this);

        addto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

                startActivityForResult(it, PICK_CONTACT);
            }
        });
    }

    @OnClick(R.id.btnSetSchedule)
    public void setSchedule(View view) {

        String message = editTextMessage.getText().toString();
        String contactNumber = editTextToRecipient.getText().toString();


        Bundle bundle = new Bundle();
        bundle.putString("number", contactNumber);
        bundle.putString("message", message);

        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        intent.putExtras(bundle);
        String actionUri = "com.whatsapp";
        intent.setAction(actionUri);

        int _id = (int) System.currentTimeMillis();
        //Long time = new GregorianCalendar().getTimeInMillis() + 60 * 1000;
        calendar.set(mYear, mMonth, mDay, mHour, mMinute);

        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, _id, intent, 0);

        Intent myIntent = new Intent(CreateWhatsMsg.this, AlarmReceiver.class);
        //
        PendingIntent pendingIntent = PendingIntent.getBroadcast(CreateWhatsMsg.this, 0, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent1);

        Toast.makeText(this, "Scheduled", Toast.LENGTH_LONG).show();

        if (databaseHelper.addSms(_id, contactNumber, message, textViewTime.getText().toString(),
                textViewDate.getText().toString(), (int) calendar.getTimeInMillis())) {
            Toast.makeText(this, "added to db", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this,WhatsAppScheduler.class));
        } else {
            Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.relativeLayoutSelectDate)
    public void getDate() {
        // initialize
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, mYear, mMonth, mDay);
        datePickerDialog.show(getFragmentManager(), "datePickerDialog");
    }

    @OnClick(R.id.relativeLayoutSelectTime)
    public void getTime() {
        // initialize
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        com.wdullaer.materialdatetimepicker.time.TimePickerDialog timePickerDialog = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(this, mHour, mMinute, DateFormat.is24HourFormat(this));
        timePickerDialog.show(getFragmentManager(), "TimePickerDialog");
    }



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR, hourOfDay);

        calendar.set(Calendar.AM_PM, Calendar.AM);

        String str;
        if (calendar.get(Calendar.AM_PM) == Calendar.AM)
            str = "AM";
        else
            str = "PM";

        String hours;
        if (calendar.get(Calendar.HOUR) > 9) {
            hours = String.valueOf(calendar.get(Calendar.HOUR));
        } else {
            hours = "0" + String.valueOf(calendar.get(Calendar.HOUR));
        }

        String minutes;
        if (minute > 9) {
            minutes = String.valueOf(minute);
        } else {
            minutes = "0" + String.valueOf(minute);
        }
        if (hours.equalsIgnoreCase("00")) {
            hours = "12";
        }

        String selectedTime = hours + ":" + minutes
                + "  " + str;

        SpannableString ss2 = new SpannableString(selectedTime);
        ss2.setSpan(new RelativeSizeSpan(1.5f), 0, 5, 0);

        textViewTime.setText(ss2);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR, hourOfDay);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mYear = year;
        mMonth = monthOfYear;
        mDay = dayOfMonth;
        calendar.add(Calendar.DATE, 0);

        final String[] MONTHS = {"Jan", "Feb", "Mar",
                "Apr", "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec"};
        String mon = MONTHS[monthOfYear];

        String selectedDate = dayOfMonth + "  " + mon
                + " " + year;

        SpannableString ss1 = new SpannableString(selectedDate);
        ss1.setSpan(new RelativeSizeSpan(1.5f), 0, 2, 0); // set
        textViewDate.setText(ss1);

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
