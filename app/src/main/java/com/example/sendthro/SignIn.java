package com.example.sendthro;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {

    @NotEmpty
    @Email
    @Pattern(regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    private EditText Email;

    @NotEmpty
    @Password
    @Pattern(regex = "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}") //"^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
    private EditText Password;

    Button signin;
    TextView SignUptxt;
    private FirebaseAuth mAuth;
    private Validator validator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        signin = findViewById(R.id.signin);
        SignUptxt = findViewById(R.id.SignUptxt);
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null ){
            Intent SignIn = new Intent(SignIn.this, HomePage.class);
            startActivity(SignIn);
            finish();
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString().trim();
                String pass = Password.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Email.setError("Email Required!");
                }

                if(TextUtils.isEmpty(pass)){
                    Password.setError("Password Required!");
                }

                if(pass.length() < 6){
                    Password.setError("Password too short");
                    return;
                }

                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignIn.this, "Sign In successfully!", Toast.LENGTH_SHORT).show();
                            Intent SignIn = new Intent(SignIn.this, HomePage.class);
                            startActivity(SignIn);
                            finish();
                        }else{
                            Toast.makeText(SignIn.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }

        });

        SignUptxt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent change = new Intent(SignIn.this, SignUp.class);
                startActivity(change);
                finish();
            }
        });
    }
}
