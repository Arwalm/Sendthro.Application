package com.example.sendthro;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WhatsAppScheduler extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_app_scheduler);
        mAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        if (mAuth.getCurrentUser() == null) {
            Intent NewUSer = new Intent(WhatsAppScheduler.this, MainActivity.class);
            startActivity(NewUSer);
        } else {
            Intent  Do  = new Intent(WhatsAppScheduler.this, CreateWhatsMsg.class);
            startActivity( Do );
        }
    }}