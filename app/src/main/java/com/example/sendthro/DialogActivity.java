package com.example.sendthro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DialogActivity extends Activity {

    Button schedule , Ok ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        schedule = findViewById(R.id.btnSchedule);
        Ok = findViewById(R.id.btnOk);

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent schedule = new Intent(DialogActivity.this, CreateSmsScheduleActivity.class);
                startActivity(schedule);
            }
        });
    }
}