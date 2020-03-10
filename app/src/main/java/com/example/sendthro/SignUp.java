package com.example.sendthro;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CheckBox;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import java.util.List;

public class SignUp extends AppCompatActivity implements Validator.ValidationListener {

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
    }

    private void initView() {
        Usernametxt = (EditText) findViewById(R.id.Usernametxt);
        PhoneNumbertxt = (EditText) findViewById(R.id.PhoneNumbertxt);
        Emailtxt = (EditText) findViewById(R.id.Emailtxt);
        Passwordtxt = (EditText) findViewById(R.id.Passwordtxt);
        checkBoxAgree = findViewById(R.id.checkBoxAgree);
        signupbtn = (Button) findViewById(R.id.signupbtn);
        skipbtn = (Button) findViewById(R.id.skipbtn);

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
    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Sign Up successfully!", Toast.LENGTH_SHORT).show();

        Intent SignUp = new Intent(SignUp.this, HomePage.class);
                startActivity(SignUp);
                finish();
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

}
