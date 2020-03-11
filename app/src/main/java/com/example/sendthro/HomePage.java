package com.example.sendthro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    Button signout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        signout = findViewById(R.id.signout);

    }

    public void SignOut (View view){
        FirebaseAuth.getInstance().signOut();
        Intent SignOut = new Intent(HomePage.this, WelcomeActivity.class);
        startActivity(SignOut);
        finish();
    }
}
