package com.example.orkunsoylu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.orkunsoylu.bringit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends Activity {
    private EditText emailText,passwordText,firstNameText,lastNameText,countryText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        emailText = (EditText) findViewById(R.id.signUpEmailText);
        passwordText = (EditText) findViewById(R.id.signUpPasswordText);
        firstNameText = (EditText) findViewById(R.id.signUpFirstNameText);
        lastNameText = (EditText) findViewById(R.id.signUpLastNameText);
        countryText = (EditText) findViewById(R.id.signUpCountryText);

        countryText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    final String tempEmail = emailText.getText().toString();
                    final String tempPassword = passwordText.getText().toString();
                    mAuth.createUserWithEmailAndPassword(tempEmail, tempPassword)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference databaseReference = database.getReference("users");
                                        Intent intent = new Intent();
                                        intent.putExtra("EMAIL",tempEmail);
                                        intent.putExtra("PASS",tempPassword);
                                        intent.putExtra("FNAME",firstNameText.getText().toString());
                                        intent.putExtra("LNAME",lastNameText.getText().toString());
                                        intent.putExtra("COUNTRY",countryText.getText().toString());
                                        setResult(Activity.RESULT_OK,intent);
                                        finish();
                                    }
                                }
                            });
                }
                return false;
            }
        });
    }
}
