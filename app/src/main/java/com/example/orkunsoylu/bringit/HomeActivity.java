package com.example.orkunsoylu.bringit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.orkunsoylu.bringit.Constants.*;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        boolean check = getIntent().getBooleanExtra("CHECK", false);
        SharedPreferences preferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        String usernameCheck = preferences.getString(PREFERENCE_USERNAME, null);
        String passwordCheck = preferences.getString(PREFERENCE_PASSWORD, null);
        if (!check) {
            if (usernameCheck == null || passwordCheck == null) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivityForResult(intent, ANSWER_REQUEST);
            }
        }
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ANSWER_REQUEST) {
            if (resultCode == RESULT_OK) {
                String email = data.getStringExtra("EMAIL");
                String password = data.getStringExtra("PASSWORD");
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                                    Toast.makeText(HomeActivity.this, R.string.auth_failed,
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
            }
            if (resultCode == RESULT_CANCELED) {
                //Guest View
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
