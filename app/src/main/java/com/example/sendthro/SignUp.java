package com.example.sendthro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity implements Validator.ValidationListener {

    public static final String TAG = "TAG";
    @NotEmpty
    @Length(min = 5, max = 15)
    private EditText Usernametxt;

    @NotEmpty
    @Pattern(regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$")
    private EditText PhoneNumbertxt;

    @NotEmpty
    @Email
    @Pattern(regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    private EditText Emailtxt;

    @NotEmpty
    @Password
    @Pattern(regex = "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}") //"^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
    private EditText Passwordtxt;

    @Checked
    private CheckBox checkBoxAgree;

    private Button signupbtn, skipbtn;

    private Validator validator;

    TextView SignIntxt;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    String userID;


    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendingToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initView();
        validator = new Validator(this);
        validator.setValidationListener(this);

        skipbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent Skip = new Intent(SignUp.this, HomePage.class);
                startActivity(Skip);
                finish();
            }
        });

        SignIntxt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent change = new Intent(SignUp.this, SignIn.class);
                startActivity(change);
                finish();
            }
        });
    }

    private void initView() {
        Usernametxt = (EditText) findViewById(R.id.Usernametxt);
        PhoneNumbertxt = (EditText) findViewById(R.id.PhoneNumbertxt);
        Emailtxt = (EditText) findViewById(R.id.Emailtxt);
        Passwordtxt = (EditText) findViewById(R.id.Passwordtxt);
        checkBoxAgree = findViewById(R.id.checkBoxAgree);
        signupbtn = (Button) findViewById(R.id.signupbtn);
        skipbtn = (Button) findViewById(R.id.skipbtn);
        SignIntxt = (TextView) findViewById(R.id.SignIntxt);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(mAuth.getCurrentUser() != null ){
            Intent SignUp = new Intent(SignUp.this, HomePage.class);
            startActivity(SignUp);
            finish();
        }

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupbtn_onClick(view);
            }
        });
    }

    private void signupbtn_onClick(View view) {
        validator.validate();
        String username = Usernametxt.getText().toString();
        if (username.equalsIgnoreCase("pmk")) {
            Usernametxt.setError(getText(R.string.username_already_exists));
        }
//        String mobile = PhoneNumbertxt.getText().toString().trim();
//
//        if(mobile.isEmpty() || mobile.length() < 10){
//            PhoneNumbertxt.setError("Enter a valid Phone Number Please!");
//            PhoneNumbertxt.requestFocus();
//            return;
//        }
//        Intent intent = new Intent(SignUp.this, PhoneVerification.class);
//        intent.putExtra("mobile", mobile);
//        startActivity(intent);
    }

    @Override
    public void onValidationSucceeded() {
        final String email = Emailtxt.getText().toString().trim();
        final String pass = Passwordtxt.getText().toString().trim();
        final String Username = Usernametxt.getText().toString();
        final String phone = PhoneNumbertxt.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "Sign Up successfully!", Toast.LENGTH_SHORT).show();
                    userID = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(userID);
                    final Map<String,Object> user = new HashMap<>();
                    user.put("Username",Username);
                    user.put("Phone Number",phone);
                    user.put("Email Address",email);
                    //user.put("Password",pass);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: user profile is created for " + userID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: " + e.toString());

                        }
                    });

                    Intent SignUp = new Intent(SignUp.this, HomePage.class);
                    startActivity(SignUp);
                    finish();

                }else{
                    Toast.makeText(SignUp.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void sendCode(View view){
        String phonenumber = PhoneNumbertxt.getText().toString();

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks);
    }

    private void setUpVerificatonCallbacks(){
        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        if ( e instanceof FirebaseAuthInvalidCredentialsException){
                            Log.d(TAG , "Invalid Credential: " + e.getLocalizedMessage());
                        }else if(e instanceof FirebaseTooManyRequestsException){
                            Log.d(TAG, "SMS Quota exceeded.");
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token){
                        phoneVerificationId = verificationId;
                        resendingToken = token;

                    }
                };
    }

    public void verifyCode(View view){
//        String code = codetext.getText().toString();
//
//        PhoneAuthCredential credential =
//                PhoneAuthProvider.getCredential(phoneVerificationId, code);
//        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential (PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this,  new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            signoutButton.setEnabled(true);
//                            codeText.setText("");
//                            statusText.setText("Signed In");
//                            resendButton.setEnabled(false);
//                            verifyButton.setEnabled(false);
                            FirebaseUser user = task.getResult().getUser();

                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    public void resendCode(View view) {

        String phoneNumber = PhoneNumbertxt.getText().toString();

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks);
               // resendToken);
    }

}