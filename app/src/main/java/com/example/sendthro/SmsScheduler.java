package com.example.sendthro;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmsScheduler extends AppCompatActivity {


    @BindView(R.id.smsListView)
    public ListView smsListView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.textViewNoSchedule)
    TextView textViewNoSchedule;

    private List<Sms> smsArrayList = new ArrayList<Sms>();
    private SmsArrayAdapter smsArrayAdapter;
    SmsDatabaseHelper databaseHelper;
    int position = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_scheduler);

        ButterKnife.bind(this);

        databaseHelper = new SmsDatabaseHelper(this);
        registerForContextMenu(smsListView);
        fetchSms();
    }
    private void fetchSms() {
        smsArrayList = databaseHelper.getAll();
        if (smsArrayList.size() > 0) {
            smsListView.setVisibility(View.VISIBLE);
            textViewNoSchedule.setVisibility(View.GONE);
            Log.d("SMSARRAY", smsArrayList.get(0).toString());
        }
        smsArrayAdapter = new SmsArrayAdapter(this, smsArrayList);
        smsListView.setAdapter(smsArrayAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
        MenuInflater inflater = getMenuInflater();
        menu.setHeaderTitle("Select what you wanna do");
        inflater.inflate(R.menu.menu_edit, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                Intent intent = new Intent(this, SmsUpdateSchedulerActivity.class);
                intent.putExtra("sms", smsArrayList.get(position));
                startActivity(intent);
                return true;
            case R.id.menu_delete:
                deleteSchedule();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteSchedule() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure?");
        dialog.setTitle("Note delete");
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.setCancelable(false);

        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (databaseHelper.deleteById(smsArrayList.get(position).getId())) {
                    Toast.makeText(getApplicationContext(), "SMS Schedule Deleted", Toast.LENGTH_SHORT).show();
                    smsArrayList.remove(position);
                    smsArrayAdapter.notifyDataSetChanged();
                }
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        dialog.show();
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        Intent intent = new Intent(this, CreateSmsScheduleActivity.class);
        startActivity(intent);
    }
}

