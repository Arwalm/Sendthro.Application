package com.example.sendthro;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

public class ChangePassword extends FragmentActivity {
    EditText newpass;
    Button button;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        settings set = new settings();


        auth = FirebaseAuth.getInstance();

        button = findViewById(R.id.button);
        newpass = findViewById(R.id.newpass);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    user.updatePassword(newpass.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(ChangePassword.this, "your psaaword successFully changed!", Toast.LENGTH_SHORT).show();
//                                        transaction.replace(R.id.framlayout,set);
//                                        transaction.commit();
                                            if ( getFragmentManager().getBackStackEntryCount() > 0)
                                            {
                                                getFragmentManager().popBackStack();
                                                return;
                                            }
                                            ChangePassword.super.onBackPressed();

                                        } else {
                                            Toast.makeText(ChangePassword.this, "Error! password did not change" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                        if ( getFragmentManager().getBackStackEntryCount() > 0)
                                        {
                                            getFragmentManager().popBackStack();
                                            return;
                                        }
                                        ChangePassword.super.onBackPressed();
                                        }
                                    }


//                                    else {
//                                        Toast.makeText(ChangePassword.this, "Error! password did not change" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                        transaction.replace(R.id.framlayout,set);
//                                        transaction.commit();
//                                    }

                            });
                }

            }
        });
    }
}