package com.example.sendthro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailSchedulerActivity extends AppCompatActivity {

    @BindView(R.id.emailListView)
    public ListView emailListView;
    @BindView(R.id.textViewNoSchedule)
    TextView textViewNoSchedule;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private EmailDatabaseHelper databaseHelper;
    private List<Email> emails = new ArrayList<Email>();
    private EmailArrayAdapter emailArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_scheduler);
        ButterKnife.bind(this);

        databaseHelper = new EmailDatabaseHelper(this);
        fetchEmail();
    }

    private void fetchEmail() {

        emails = databaseHelper.getAll();
        if (emails.size() > 0) {
            emailListView.setVisibility(View.VISIBLE);
            textViewNoSchedule.setVisibility(View.GONE);
        }
        emailArrayAdapter = new EmailArrayAdapter(this, emails);
        emailListView.setAdapter(emailArrayAdapter);
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        startActivity(new Intent(this, CreateEmailScheduleActivity.class));
    }
}
