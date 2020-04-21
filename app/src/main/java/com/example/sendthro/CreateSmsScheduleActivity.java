package com.example.sendthro;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
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
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateSmsScheduleActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, Validator.ValidationListener {

    //@BindView(R.id.btnSetSchedule)
    Button btnSetSchedule;
    //@BindView(R.id.textViewDate)
    @NotEmpty
    TextView textViewDate;
    //@BindView(R.id.textViewTime)
    @NotEmpty
    TextView textViewTime;
    //@BindView(R.id.relativeLayoutSelectTime)
    RelativeLayout relativeLayoutSelectTime;
    //@BindView(R.id.relativeLayoutSelectDate)
    RelativeLayout relativeLayoutSelectDate;
    Calendar calendar;
    //@BindView(R.id.editTextMessage)
    @NotEmpty
    EditText editTextMessage;
    //@BindView(R.id.editTextToRecipient)
    @NotEmpty
    EditText editTextToRecipient;
    Button addto;
    EditText toText;

    private Validator validator;

    private static final int PERMISSIONS_REQUEST_SEND_SMS = 101;
    int mHour, mMinute, mYear, mMonth, mDay;
    SmsDatabaseHelper databaseHelper;

    private static final int PICK_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sms_schedule);
        ButterKnife.bind(this);
        calendar = Calendar.getInstance();

        initView();
        validator = new Validator(this);
        validator.setValidationListener(this);

        addto = findViewById(R.id.addto);

        relativeLayoutSelectTime = findViewById(R.id.relativeLayoutSelectTime);
        relativeLayoutSelectDate = findViewById(R.id.relativeLayoutSelectDate);

        databaseHelper = new SmsDatabaseHelper(this);

        addto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

                startActivityForResult(it, PICK_CONTACT);
            }
        });
    }

    private void initView() {
        btnSetSchedule = findViewById(R.id.btnSetSchedule);
        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);
        editTextMessage = findViewById(R.id.editTextMessage);
        editTextToRecipient = findViewById(R.id.editTextToRecipient);


        btnSetSchedule.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            btnSetSchedule_onClick(view);
        }
    });
}

    private void btnSetSchedule_onClick(View view) {
        validator.validate();
        }

    @Override
    public void onValidationSucceeded() {
        setSmsSchedule();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick(R.id.btnSetSchedule)
    public void setSchedule() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_REQUEST_SEND_SMS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
        else {
            setSmsSchedule();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                setSmsSchedule();
            } else {
                Toast.makeText(this, "Until you grant the permission, we cannot set schedule", Toast.LENGTH_SHORT).show();
            }
        }
    }
//
//    @OnClick(R.id.textViewDate)
//    public void testDate() {
//        if (textViewDate == null){
//            Toast.makeText(this, "Please Select Date", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @OnClick(R.id.textViewTime)
//    public void testTime() {
//        if (textViewTime == null){
//            Toast.makeText(this, "Please Select Time", Toast.LENGTH_LONG).show();
//        }
//    }

    private void setSmsSchedule() {
        String message = editTextMessage.getText().toString();
        String contactNumber = editTextToRecipient.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("number", contactNumber);
        bundle.putString("message", message);

        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        intent.putExtras(bundle);
        String actionUri = "com.scheduler.action.SMS_SEND";
        intent.setAction(actionUri);

        int _id = (int) System.currentTimeMillis();
        //Long time = new GregorianCalendar().getTimeInMillis() + 60 * 1000;
        calendar.set(mYear, mMonth, mDay, mHour, mMinute);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, _id, intent, 0);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "Scheduled", Toast.LENGTH_LONG).show();

        if (databaseHelper.addSms(_id, contactNumber, message, textViewTime.getText().toString(),
                textViewDate.getText().toString(), (int) calendar.getTimeInMillis())) {
            startActivity(new Intent(this, SmsScheduler.class));
            finish();
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

        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, mHour, mMinute, DateFormat.is24HourFormat(this));
        timePickerDialog.show(getFragmentManager(), "TimePickerDialog");
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

    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
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
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
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
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {

                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        String phoneNo = null ;

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);
                            phones.moveToFirst();
                            phoneNo = phones.getString(phones.getColumnIndex("data1"));

                            editTextToRecipient.setText(phoneNo);
                            System.out.println("number is:"+phoneNo);
                        }
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    }
                }
                break;
        }
    }
}




