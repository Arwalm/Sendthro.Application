package com.example.sendthro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class scheduledmsg extends Fragment {


    RelativeLayout sms, email ,whatsapp;
    private static Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scheduledmsg, container, false );

        sms = v.findViewById(R.id.sms);
        email = v.findViewById(R.id.email);
        whatsapp = v.findViewById(R.id.whatsapp);

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
                Intent emailIntent = new Intent(getActivity(), EmailSchedulerActivity.class);
                startActivity(emailIntent);
            }
        });

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent(getActivity(), WhatsAppScheduler.class);
                startActivity(whatsappIntent);
            }
        });

        return v;
    }

    public static Context getAppContext() {
        return scheduledmsg.context;
    }

}
