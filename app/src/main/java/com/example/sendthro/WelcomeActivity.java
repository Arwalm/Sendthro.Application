package com.example.sendthro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    Button signinbtn, signupbtn, skipbtn, signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        signinbtn = (Button) findViewById(R.id.signinbtn);
        signupbtn = (Button) findViewById(R.id.signupbtn);
        skipbtn = (Button) findViewById(R.id.skipbtn);
        signout = findViewById(R.id.signout);

        signinbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent SignIn = new Intent(WelcomeActivity.this, SignIn.class);
                startActivity(SignIn);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent SignUp = new Intent(WelcomeActivity.this, SignUp.class);
                startActivity(SignUp);
            }
        });


        skipbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent Skip = new Intent(WelcomeActivity.this, HomePage.class);
                startActivity(Skip);
                finish();
            }
        });
    }

    public void SignOut (View view) {
        FirebaseAuth.getInstance().signOut();
        Intent signoutint = new Intent(WelcomeActivity.this, HomePage.class);
        startActivity(signoutint);
        finish();
    }
}
