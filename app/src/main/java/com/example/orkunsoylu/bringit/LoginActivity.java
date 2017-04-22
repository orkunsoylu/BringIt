package com.example.orkunsoylu.bringit;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText emailText,passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailText = (EditText) findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        passwordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    Intent intent = new Intent();
                    intent.putExtra("EMAIL", emailText.getText().toString());
                    intent.putExtra("PASSWORD",passwordText.getText().toString());
                    if (getParent() == null) {
                        setResult(Activity.RESULT_OK, intent);
                    } else {
                        getParent().setResult(Activity.RESULT_OK, intent);
                    }
                    finish();
                }
                return false;
            }
        });

    }

    public void skip(View view){
        if(view.getId() == R.id.skipButton) {
            Intent intent = new Intent();
            intent.putExtra("CHECK", true);
            if (getParent() == null) {
                setResult(Activity.RESULT_CANCELED, intent);
            } else {
                getParent().setResult(Activity.RESULT_CANCELED, intent);
            }
            finish();
        }
    }
}
