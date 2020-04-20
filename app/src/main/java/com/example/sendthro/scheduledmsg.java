package com.example.sendthro;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class scheduledmsg extends Fragment {


    RelativeLayout sms, email ;
    private static Context context;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scheduledmsg, container, false );

        sms = view.findViewById(R.id.sms);
        email = view.findViewById(R.id.email);


        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton4);
        FloatingActionButton smsfab = (FloatingActionButton) view.findViewById(R.id.smsfab);
        FloatingActionButton emailfab = (FloatingActionButton) view.findViewById(R.id.emailfab);

        final RelativeLayout smslayout = (RelativeLayout) view.findViewById(R.id.smslayout);
        final RelativeLayout emaillayout = (RelativeLayout) view.findViewById(R.id.emaillayout);


        smslayout.setVisibility(View.INVISIBLE);
        emaillayout.setVisibility(View.INVISIBLE);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (smslayout.getVisibility() == View.VISIBLE
                        && emaillayout.getVisibility() == View.VISIBLE) {

                    smslayout.setVisibility(View.GONE);
                    emaillayout.setVisibility(View.GONE);

                } else {
                    smslayout.setVisibility(View.VISIBLE);
                    emaillayout.setVisibility(View.VISIBLE);
                }
            }
        });

        smsfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() == null) {
                    Intent NewUSer = new Intent(getActivity(), MainActivity.class);
                    startActivity(NewUSer);
                } else {
                    Intent Do = new Intent(getActivity(), CreateSmsScheduleActivity.class);
                    startActivity(Do);
                }
            }
        });



        emailfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() == null) {
                    Intent NewUSer = new Intent(getActivity(), MainActivity.class);
                    startActivity(NewUSer);
                } else {
                    Intent Do = new Intent(getActivity(), CreateEmailScheduleActivity.class);
                    startActivity(Do);
                }
            }
        });


        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(getActivity(), SmsScheduler.class);
                startActivity(smsIntent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(getActivity(), EmailScheduler.class);
                startActivity(emailIntent);
            }
        });


        return view;
    }

    public static Context getAppContext() {
        return scheduledmsg.context;
    }

}

