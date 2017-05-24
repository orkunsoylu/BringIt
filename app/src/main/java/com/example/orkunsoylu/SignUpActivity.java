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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends Activity {
    private EditText emailText, passwordText, firstNameText, lastNameText, countryText;
    private FirebaseAuth mAuth;
    private String userID;
    private FirebaseUser firebaseUser;
    private DatabaseReference userReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userID = getIntent().getStringExtra("USER_ID");

        mAuth = FirebaseAuth.getInstance();

        emailText = (EditText) findViewById(R.id.signUpEmailText);
        passwordText = (EditText) findViewById(R.id.signUpPasswordText);
        firstNameText = (EditText) findViewById(R.id.signUpFirstNameText);
        lastNameText = (EditText) findViewById(R.id.signUpLastNameText);
        countryText = (EditText) findViewById(R.id.signUpCountryText);

        if (!userID.equals("0")){
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            userReference = FirebaseDatabase.getInstance().getReference("users").child(userID);
            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    emailText.setText(firebaseUser.getEmail());
                    firstNameText.setText((String) dataSnapshot.child("first_name").getValue());
                    lastNameText.setText((String) dataSnapshot.child("last_name").getValue());
                    countryText.setText((String) dataSnapshot.child("country").getValue());
                }
                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
        }

        countryText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (userID.equals("0")) {
                        final String tempEmail = emailText.getText().toString();
                        final String tempPassword = passwordText.getText().toString();
                        mAuth.createUserWithEmailAndPassword(tempEmail, tempPassword)
                                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent();
                                            intent.putExtra("EMAIL", tempEmail);
                                            intent.putExtra("PASS", tempPassword);
                                            intent.putExtra("FNAME", firstNameText.getText().toString());
                                            intent.putExtra("LNAME", lastNameText.getText().toString());
                                            intent.putExtra("COUNTRY", countryText.getText().toString());
                                            setResult(Activity.RESULT_OK, intent);
                                            finish();
                                        }
                                    }
                                });
                    } else {
                        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        userReference = FirebaseDatabase.getInstance().getReference("users").child(userID);
                        if (!firstNameText.getText().toString().equals("")) {
                            userReference.child("first_name").setValue(firstNameText.getText().toString());
                        }
                        if (!lastNameText.getText().toString().equals("")) {
                            userReference.child("last_name").setValue(lastNameText.getText().toString());
                        }
                        if (!countryText.getText().toString().equals("")) {
                            userReference.child("country").setValue(countryText.getText().toString());
                        }
                        if (!emailText.getText().toString().equals("")) {
                            firebaseUser.updateEmail(emailText.getText().toString());
                        }
                        if (!passwordText.getText().toString().equals("")) {
                            firebaseUser.updatePassword(passwordText.getText().toString());
                        }
                        Intent intent = new Intent();
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                }
                return false;
            }
        });

    }
}
