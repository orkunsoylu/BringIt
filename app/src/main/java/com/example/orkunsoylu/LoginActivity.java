package com.example.orkunsoylu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.orkunsoylu.bringit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends Activity implements View.OnClickListener{
    private EditText emailText,passwordText;
    private Button signUpButton,skipButton;
    private FirebaseAuth mAuth;
    static final int SIGN_UP_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        emailText =(EditText) findViewById(R.id.loginEmailText);
        passwordText = (EditText) findViewById(R.id.loginPasswordText);
        passwordText.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String tempEmail = emailText.getText().toString();
                    String tempPassword = passwordText.getText().toString();
                    mAuth.signInWithEmailAndPassword(tempEmail, tempPassword)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        intent.putExtra("USER_ID",firebaseUser.getUid());
                                        startActivity(intent);
                                    }
                                }
                            });
                }
                return false;
            }
        });

        signUpButton = (Button) findViewById(R.id.loginSignUpButton);
        signUpButton.setOnClickListener(this);
        skipButton = (Button) findViewById(R.id.loginSkipButton);
        skipButton.setOnClickListener(this);

    }

    public void onClick(View v){
        if (v.getId() == skipButton.getId()){
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            intent.putExtra("USER_ID","0");
            startActivity(intent);
        } else if (v.getId() == signUpButton.getId()){
            Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
            startActivityForResult(intent,SIGN_UP_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SIGN_UP_REQUEST) {
            if (resultCode == RESULT_OK) {
                String tempEmail = data.getStringExtra("EMAIL");
                String tempPassword = data.getStringExtra("PASS");
                emailText.setText(tempEmail);
                passwordText.setText(tempPassword);
                mAuth.signInWithEmailAndPassword(tempEmail, tempPassword)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("USER_ID",firebaseUser.getUid());
                                    startActivity(intent);
                                }
                            }
                        });
            }
        }
    }
}
