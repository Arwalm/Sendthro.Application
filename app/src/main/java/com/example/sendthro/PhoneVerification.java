package com.example.sendthro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

public class PhoneVerification extends AppCompatActivity {

    PinEntryEditText pinEntry;
    TextView Resend;
    Button verifybtn;

    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationStateChangedCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendingToken;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        pinEntry = (PinEntryEditText) findViewById(R.id.txt_pin_entry);
        Resend = (TextView) findViewById(R.id.resend);
        verifybtn = (Button) findViewById(R.id.verifybtn);
        mAuth = FirebaseAuth.getInstance();

        verifybtn.setEnabled(false);
        Resend.setEnabled(false);

    }
}

//        if (pinEntry != null) {
//            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
//                @Override
//                public void onPinEntered(CharSequence str) {
//                    if (str.toString().equals("1234")) {
//                        Toast.makeText(PhoneVerification.this, "SUCCESS", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(PhoneVerification.this, "FAIL", Toast.LENGTH_SHORT).show();
//                        pinEntry.setText(null);
//                    }
//                }
//            });
//        }