package com.example.sendthro;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.style.Theme_Holo_Light_Dialog_MinWidth;

public class CreateSmsScheduleActivity extends AppCompatActivity{
//        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.btnSetSchedule)
    Button btnSetSchedule;
    @BindView(R.id.textViewDate)
    TextView textViewDate;
    @BindView(R.id.textViewTime)
    TextView textViewTime;
//    @BindView(R.id.relativeLayoutSelectTime)
//    RelativeLayout relativeLayoutSelectTime;
//    @BindView(R.id.relativeLayoutSelectDate)
//    RelativeLayout relativeLayoutSelectDate;
    Calendar calendar;
    @BindView(R.id.editTextMessage)
    EditText editTextMessage;
    @BindView(R.id.editTextToRecipient)
    EditText editTextToRecipient;
    ImageView attachment;
    Button addto;


    private android.app.DatePickerDialog.OnDateSetListener mDateSetListener;
    private android.app.TimePickerDialog.OnTimeSetListener mTimeSetListener;

    private static final String TAG = "Newmessage";
    Integer REQUEST_CAMERA = 1;
    Integer SELECT_FILE = 0;
    private static final int PICK_CONTACT = 1;

    private static final int PERMISSIONS_REQUEST_SEND_SMS = 101;
    int mHour, mMinute, mYear, mMonth, mDay;
    SmsDatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmessage);
        ButterKnife.bind(this);
        calendar = Calendar.getInstance();

        addto = findViewById(R.id.addto);
        attachment = findViewById(R.id.attachment);

        databaseHelper = new SmsDatabaseHelper(this);


        //DateDialog
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                android.app.DatePickerDialog dialog = new android.app.DatePickerDialog(
                        CreateSmsScheduleActivity.this,
                        Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new android.app.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                textViewDate.setText(date);
            }
        };

        //TimeDialog
        textViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR);
                int minute = cal.get(Calendar.MINUTE);

                android.app.TimePickerDialog dialog = new android.app.TimePickerDialog(
                        CreateSmsScheduleActivity.this,
                        Theme_Holo_Light_Dialog_MinWidth,
                        (android.app.TimePickerDialog.OnTimeSetListener) mTimeSetListener,
                        hour,minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeSetListener = new android.app.TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker TimePicker, int hour, int minute) {
                Log.d(TAG, "onTimeSet: hh:mm " + hour + ":" + minute );

                String Time = hour + ":" + minute ;
                textViewTime.setText(Time);
            }
        };

        //Attachment
        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        addto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it= new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);

                startActivityForResult(it, PICK_CONTACT);
            }
        });

    }

    private void selectImage() {
        final CharSequence[] items = {"camera", "gallery", "cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateSmsScheduleActivity.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[i].equals("gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "select File"), SELECT_FILE);
                } else if (items[i].equals("cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult (int requestCode, int resultCode , Intent data){
        super.onActivityResult( requestCode,  resultCode ,  data);
        if (requestCode== Activity.RESULT_OK){
            if(requestCode==REQUEST_CAMERA){
                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                attachment.setImageBitmap(bmp);
            } else if (requestCode==SELECT_FILE){
                Uri selsctedImageUri = data.getData();
                attachment.setImageURI(selsctedImageUri);
            }
        }
    }


    @OnClick(R.id.btnSetSchedule)
    public void setSchedule() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_REQUEST_SEND_SMS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method

        } else {
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
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this, SmsScheduler.class));
        } else {
            Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
        }
    }


//    @OnClick(R.id.relativeLayoutSelectDate)
//    public void getDate() {
//        // initialize
//        mYear = calendar.get(Calendar.YEAR);
//        mMonth = calendar.get(Calendar.MONTH);
//        mDay = calendar.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, mYear, mMonth, mDay);
//        datePickerDialog.show(getFragmentManager(), "datePickerDialog");
//    }
//
//    @OnClick(R.id.relativeLayoutSelectTime)
//    public void getTime() {
//        // initialize
//        mHour = calendar.get(Calendar.HOUR_OF_DAY);
//        mMinute = calendar.get(Calendar.MINUTE);
//
//        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, mHour, mMinute, DateFormat.is24HourFormat(this));
//        timePickerDialog.show(getFragmentManager(), "TimePickerDialog");
//    }
//
//    @Override
//    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//        mYear = year;
//        mMonth = monthOfYear;
//        mDay = dayOfMonth;
//        calendar.add(Calendar.DATE, 0);
//
//        final String[] MONTHS = {"Jan", "Feb", "Mar",
//                "Apr", "May", "Jun", "Jul", "Aug",
//                "Sep", "Oct", "Nov", "Dec"};
//        String mon = MONTHS[monthOfYear];
//
//        String selectedDate = dayOfMonth + "  " + mon
//                + " " + year;
//
//        SpannableString ss1 = new SpannableString(selectedDate);
//        ss1.setSpan(new RelativeSizeSpan(1.5f), 0, 2, 0); // set
//        textViewDate.setText(ss1);
//
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.MONTH, monthOfYear);
//        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//    }
//
//    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
//        mHour = hourOfDay;
//        mMinute = minute;
//
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MINUTE, minute);
//        calendar.set(Calendar.HOUR, hourOfDay);
//
//        calendar.set(Calendar.AM_PM, Calendar.AM);
//
//        String str;
//        if (calendar.get(Calendar.AM_PM) == Calendar.AM)
//            str = "AM";
//        else
//            str = "PM";
//
//        String hours;
//        if (calendar.get(Calendar.HOUR) > 9) {
//            hours = String.valueOf(calendar.get(Calendar.HOUR));
//        } else {
//            hours = "0" + String.valueOf(calendar.get(Calendar.HOUR));
//        }
//
//        String minutes;
//        if (minute > 9) {
//            minutes = String.valueOf(minute);
//        } else {
//            minutes = "0" + String.valueOf(minute);
//        }
//        if (hours.equalsIgnoreCase("00")) {
//            hours = "12";
//        }
//
//        String selectedTime = hours + ":" + minutes
//                + "  " + str;
//
//        SpannableString ss2 = new SpannableString(selectedTime);
//        ss2.setSpan(new RelativeSizeSpan(1.5f), 0, 5, 0);
//
//        textViewTime.setText(ss2);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MINUTE, minute);
//        calendar.set(Calendar.HOUR, hourOfDay);
//    }
//
//    @Override
//    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//        mHour = hourOfDay;
//        mMinute = minute;
//
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MINUTE, minute);
//        calendar.set(Calendar.HOUR, hourOfDay);
//
//        calendar.set(Calendar.AM_PM, Calendar.AM);
//
//        String str;
//        if (calendar.get(Calendar.AM_PM) == Calendar.AM)
//            str = "AM";
//        else
//            str = "PM";
//
//        String hours;
//        if (calendar.get(Calendar.HOUR) > 9) {
//            hours = String.valueOf(calendar.get(Calendar.HOUR));
//        } else {
//            hours = "0" + String.valueOf(calendar.get(Calendar.HOUR));
//        }
//
//        String minutes;
//        if (minute > 9) {
//            minutes = String.valueOf(minute);
//        } else {
//            minutes = "0" + String.valueOf(minute);
//        }
//        if (hours.equalsIgnoreCase("00")) {
//            hours = "12";
//        }
//
//        String selectedTime = hours + ":" + minutes
//                + "  " + str;
//
//        SpannableString ss2 = new SpannableString(selectedTime);
//        ss2.setSpan(new RelativeSizeSpan(1.5f), 0, 5, 0);
//
//        textViewTime.setText(ss2);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MINUTE, minute);
//        calendar.set(Calendar.HOUR, hourOfDay);
//    }
    }


