package com.example.sendthro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import static android.R.style.Theme_Holo_Light_Dialog_MinWidth;

public class Newmessage extends AppCompatActivity {

        private static final String TAG = "Newmessage";

        private TextView mDisplayDate;
        private DatePickerDialog.OnDateSetListener mDateSetListener;
        private TextView mDisplayTime;
        private TimePickerDialog.OnTimeSetListener mTimeSetListener;
        private static final int PICK_CONTACT = 1;
        ImageView attachment;
        Integer REQUEST_CAMERA = 1;
        Integer SELECT_FILE = 0;
        Button addto;
        EditText toText;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_newmessage);
            mDisplayDate = (TextView) findViewById(R.id.textViewDate);//date
            mDisplayTime = (TextView) findViewById(R.id.textViewTime);//time
            attachment = (ImageView) findViewById(R.id.attachment);
            addto = (Button) findViewById(R.id.addto);
            toText = (EditText) findViewById(R.id.editTextToRecipient);//to edit

            mDisplayDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            Newmessage.this,
                            Theme_Holo_Light_Dialog_MinWidth,
                            mDateSetListener,
                            year, month, day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });

            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;
                    Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                    String date = month + "/" + day + "/" + year;
                    mDisplayDate.setText(date);
                }
            };
            mDisplayTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int hour = cal.get(Calendar.HOUR);
                    int minute = cal.get(Calendar.MINUTE);

                    TimePickerDialog dialog = new TimePickerDialog(
                            Newmessage.this, Theme_Holo_Light_Dialog_MinWidth,
                            (TimePickerDialog.OnTimeSetListener) mTimeSetListener,
                            hour,minute, true);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });

            mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker TimePicker, int hour, int minute) {
                    Log.d(TAG, "onTimeSet: hh:mm " + hour + ":" + minute );

                    String Time = hour + ":" + minute ;
                    mDisplayTime.setText(Time);
                }
            };
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
            AlertDialog.Builder builder = new AlertDialog.Builder(Newmessage.this);
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

    }