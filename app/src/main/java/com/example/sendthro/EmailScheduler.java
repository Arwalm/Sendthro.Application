package com.example.sendthro;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailScheduler extends AppCompatActivity {

    @BindView(R.id.emailListView)
    public ListView emailListView;
    @BindView(R.id.textViewNoSchedule)
    TextView textViewNoSchedule;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private FirebaseAuth mAuth;
    private EmailDatabaseHelper databaseHelper;
    private List<Email> emails = new ArrayList<Email>();
    private EmailArrayAdapter emailArrayAdapter;
    int position = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_scheduler);

        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        databaseHelper = new EmailDatabaseHelper(this);
        registerForContextMenu(emailListView);
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

        if (mAuth.getCurrentUser() == null) {
            Intent NewUSer = new Intent(EmailScheduler.this, MainActivity.class);
            startActivity(NewUSer);
        } else {
            Intent intent = new Intent(this, CreateEmailScheduleActivity.class);
            startActivity(intent);

        }}

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
        MenuInflater inflater = getMenuInflater();
        menu.setHeaderTitle("Select what you wanna do");
        inflater.inflate(R.menu.menu_del, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        dialog.setTitle("Delete!");
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.setCancelable(false);

        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (databaseHelper.deleteById(emails.get(position).getId())) {
                    Toast.makeText(getApplicationContext(), "Email Schedule Deleted", Toast.LENGTH_SHORT).show();
                    emails.remove(position);
                    emailArrayAdapter.notifyDataSetChanged();
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
}
