package com.example.orkunsoylu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.orkunsoylu.bringit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WishActivity extends Activity implements View.OnClickListener{
    private EditText detailTitle,detailPrice,detailAddress;
    private TextView userName,userCountry;
    private String wishOwnerID,userID,selectedWishID;
    private FirebaseUser firebaseUser;
    private Button bringItButton;
    private boolean isNewWish;
    private DatabaseReference userReference,wishReference;
    private String newID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        userReference = FirebaseDatabase.getInstance().getReference().child("users");
        wishReference = FirebaseDatabase.getInstance().getReference().child("wishes");

        detailTitle = (EditText) findViewById(R.id.wishDetailTitle);
        detailPrice = (EditText) findViewById(R.id.wishDetailPriceText);
        detailAddress = (EditText) findViewById(R.id.wishDetailAddressText);
        userName = (TextView) findViewById(R.id.wishUserInfoNameText);
        userCountry = (TextView) findViewById(R.id.wishUserInfoCountryText);

        bringItButton = (Button) findViewById(R.id.wishBringItButton);
        bringItButton.setOnClickListener(this);

        isNewWish = getIntent().getBooleanExtra("NEW_WISH",false);

        if (isNewWish){
            bringItButton.setText("Create My Wish");

            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String fname = (String) dataSnapshot.child(firebaseUser.getUid()).child("first_name").getValue();
                    String lname = (String) dataSnapshot.child(firebaseUser.getUid()).child("last_name").getValue();
                    String country = (String) dataSnapshot.child(firebaseUser.getUid()).child("country").getValue();

                    userName.setText(fname + " " + lname);
                    userCountry.setText(country);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            wishReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    newID = Long.toString(dataSnapshot.getChildrenCount());
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else {
            detailTitle.setClickable(false);
            detailAddress.setClickable(false);
            detailPrice.setClickable(false);

            bringItButton.setText("I Can BringIt");

            selectedWishID = getIntent().getStringExtra("WISH_ID");

            wishReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String title = (String) dataSnapshot.child(selectedWishID).child("title").getValue();
                    String price = (String) dataSnapshot.child(selectedWishID).child("price").getValue();
                    String address = (String) dataSnapshot.child(selectedWishID).child("address").getValue();
                    wishOwnerID = (String) dataSnapshot.child(selectedWishID).child("owner_ID").getValue();

                    detailTitle.setText(title);
                    detailPrice.setText(price);
                    detailAddress.setText(address);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String fname = (String) dataSnapshot.child(wishOwnerID).child("first_name").getValue();
                    String lname = (String) dataSnapshot.child(wishOwnerID).child("last_name").getValue();
                    String country = (String) dataSnapshot.child(wishOwnerID).child("country").getValue();

                    userName.setText(fname + " " + lname);
                    userCountry.setText(country);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void onClick(View v){
        if (v.getId() == bringItButton.getId()){
            if (isNewWish){
                userID = firebaseUser.getUid();
                wishReference.child(newID).child("owner_id").setValue(userID);
                wishReference.child(newID).child("title").setValue(detailTitle.getText().toString());
                wishReference.child(newID).child("price").setValue(detailPrice.getText().toString());
                wishReference.child(newID).child("address").setValue(detailAddress.getText().toString());
            } else {

            }
        }
    }
}
