package com.example.orkunsoylu;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements View.OnClickListener{
    static final int SIGN_UP_REQUEST = 1;
    private Button editButton,logOutButton,seeAllButton;
    private TextView nameText,countryText,wishTitleText,wishPriceText,wishCountryText;
    private String userWishes[];
    private String selectedWishID;
    private FirebaseUser firebaseUser;
    private DatabaseReference userReference;
    private ConstraintLayout profileRow1Wish1,profileRow1Wish2,profileRow1Wish3,
            profileRow2Wish1,profileRow2Wish2,profileRow2Wish3,
            profileRow3Wish1,profileRow3Wish2,profileRow3Wish3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View currFragment = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseUser = ((MainActivity) getActivity()).returnUser();
        if (firebaseUser != null) {
            userReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());


            userWishes = new String[9];

            prepareLayout(currFragment);

            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String fname = (String) dataSnapshot.child("first_name").getValue();
                    String lname = (String) dataSnapshot.child("last_name").getValue();
                    String country = (String) dataSnapshot.child("country").getValue();
                    nameText.setText(fname + " " + lname);
                    countryText.setText(country);
                    for (int i = 0; i < 9; i++) {
                        userWishes[i] = (String) dataSnapshot.child("wishes").child(Integer.toString(i)).getValue();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
        } else {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(getActivity());
            }
            builder.setTitle("Guest Alert")
                    .setMessage("You need to be logged in to access this page.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(),SignUpActivity.class);
                            intent.putExtra("USER_ID","0");
                            startActivityForResult(intent,SIGN_UP_REQUEST);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        
        return currFragment;
    }

    public void onClick(View v){
        if (v.getId() == editButton.getId()){
            Intent intent = new Intent(getActivity(),SignUpActivity.class);
            intent.putExtra("USER_ID",firebaseUser.getUid());
            startActivityForResult(intent,SIGN_UP_REQUEST);
        } else if (v.getId() == logOutButton.getId()){
            FirebaseAuth.getInstance().signOut();
            Intent intent = getActivity().getBaseContext().getPackageManager().
                    getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else if (v.getId() == seeAllButton.getId()){
            SearchFragment fragment = new SearchFragment();
            fragment.setUserID(firebaseUser.getUid());
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.homeFrameLayout, fragment, "visible_fragment");
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        } else {
            switch (v.getId()){
                case R.id.profileRow1Wish1:
                    selectedWishID = userWishes[0];
                    break;
                case R.id.profileRow1Wish2:
                    selectedWishID = userWishes[1];
                    break;
                case R.id.profileRow1Wish3:
                    selectedWishID = userWishes[2];
                    break;
                case R.id.profileRow2Wish1:
                    selectedWishID = userWishes[3];
                    break;
                case R.id.profileRow2Wish2:
                    selectedWishID = userWishes[4];
                    break;
                case R.id.profileRow2Wish3:
                    selectedWishID = userWishes[5];
                    break;
                case R.id.profileRow3Wish1:
                    selectedWishID = userWishes[6];
                    break;
                case R.id.profileRow3Wish2:
                    selectedWishID = userWishes[7];
                    break;
                case R.id.profileRow3Wish3:
                    selectedWishID = userWishes[8];
                    break;
            }
            Intent intent = new Intent(getActivity(),WishActivity.class);
            intent.putExtra("WISH_ID",selectedWishID);
            startActivity(intent);
        }
    }



    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if (requestCode == SIGN_UP_REQUEST) {
            if (resultCode == RESULT_OK) {
                userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String fname = (String) dataSnapshot.child("first_name").getValue();
                        String lname = (String) dataSnapshot.child("last_name").getValue();
                        String country = (String) dataSnapshot.child("country").getValue();
                        nameText.setText(fname + " " + lname);
                        countryText.setText(country);
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
            }
        }
    }

    public void prepareLayout(View currFragment){
        nameText = (TextView) currFragment.findViewById(R.id.profileNameText);
        countryText = (TextView) currFragment.findViewById(R.id.profileCountryText);

        editButton = (Button) currFragment.findViewById(R.id.profileEditButton);
        editButton.setOnClickListener(this);
        logOutButton = (Button) currFragment.findViewById(R.id.profileLogOutButton);
        logOutButton.setOnClickListener(this);
        seeAllButton = (Button) currFragment.findViewById(R.id.profileSeeButton);
        seeAllButton.setOnClickListener(this);

        profileRow1Wish1 = (ConstraintLayout) currFragment.findViewById(R.id.profileRow1Wish1);
        profileRow1Wish2 = (ConstraintLayout) currFragment.findViewById(R.id.profileRow1Wish2);
        profileRow1Wish3 = (ConstraintLayout) currFragment.findViewById(R.id.profileRow1Wish3);

        profileRow2Wish1 = (ConstraintLayout) currFragment.findViewById(R.id.profileRow2Wish1);
        profileRow2Wish2 = (ConstraintLayout) currFragment.findViewById(R.id.profileRow2Wish2);
        profileRow2Wish3 = (ConstraintLayout) currFragment.findViewById(R.id.profileRow2Wish3);

        profileRow3Wish1 = (ConstraintLayout) currFragment.findViewById(R.id.profileRow3Wish1);
        profileRow3Wish2 = (ConstraintLayout) currFragment.findViewById(R.id.profileRow3Wish2);
        profileRow3Wish3 = (ConstraintLayout) currFragment.findViewById(R.id.profileRow3Wish3);

        profileRow1Wish1.setOnClickListener(this);
        profileRow1Wish2.setOnClickListener(this);
        profileRow1Wish3.setOnClickListener(this);

        profileRow2Wish1.setOnClickListener(this);
        profileRow2Wish2.setOnClickListener(this);
        profileRow2Wish3.setOnClickListener(this);

        profileRow3Wish1.setOnClickListener(this);
        profileRow3Wish2.setOnClickListener(this);
        profileRow3Wish3.setOnClickListener(this);
    }
}
