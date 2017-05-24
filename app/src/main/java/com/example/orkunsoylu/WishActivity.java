package com.example.orkunsoylu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.orkunsoylu.bringit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twitter.sdk.android.core.models.Search;

public class WishActivity extends Activity implements View.OnClickListener{
    private TextView detailTitle,detailPrice,detailAddress,userName,userCountry;
    private String wishOwnerID,userID,selectedWishID;
    private FirebaseUser firebaseUser;
    private Button bringItButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        detailTitle = (TextView) findViewById(R.id.wishDetailTitle);
        detailPrice = (TextView) findViewById(R.id.wishDetailPriceText);
        detailAddress = (TextView) findViewById(R.id.wishDetailAddressText);
        userName = (TextView) findViewById(R.id.wishUserInfoNameText);
        userCountry = (TextView) findViewById(R.id.wishUserInfoCountryText);

        bringItButton = (Button) findViewById(R.id.wishBringItButton);
        bringItButton.setOnClickListener(this);

        wishOwnerID = getIntent().getStringExtra("OWNER_ID");
        selectedWishID = getIntent().getStringExtra("WISH_ID");

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String fname = (String) dataSnapshot.child("users").child(wishOwnerID).child("first_name").getValue();
                String lname = (String) dataSnapshot.child("users").child(wishOwnerID).child("last_name").getValue();
                String country = (String) dataSnapshot.child("users").child(wishOwnerID).child("country").getValue();

                userName.setText(fname + " " + lname);
                userCountry.setText(country);

                String title = (String) dataSnapshot.child("wishes").child(selectedWishID).child("title").getValue();
                String price = (String) dataSnapshot.child("wishes").child(selectedWishID).child("price").getValue();
                String address = (String) dataSnapshot.child("wishes").child(selectedWishID).child("address").getValue();

                detailTitle.setText(title);
                detailPrice.setText(price);
                detailAddress.setText(address);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void onClick(View v){
        if (v.getId() == bringItButton.getId()){

        }
    }
}
