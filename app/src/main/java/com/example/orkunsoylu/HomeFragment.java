package com.example.orkunsoylu;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.orkunsoylu.bringit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private TextView helloLabel;
    private TextView appLabel;
    private FirebaseUser firebaseUser;
    private DatabaseReference wishReference;
    private ConstraintLayout featuredWish1,featuredWish2,featuredWish3,
    activeWish1,activeWish2,activeWish3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View currFragment = inflater.inflate(R.layout.fragment_home, container, false);

        helloLabel = (TextView) currFragment.findViewById(R.id.homeHelloLabel);
        appLabel = (TextView) currFragment.findViewById(R.id.homeAppLabel);
        appLabel.setClickable(true);
        appLabel.setOnClickListener(this);
        firebaseUser = ((MainActivity) getActivity()).returnUser();
        if (firebaseUser != null) {
            helloLabel.setText("Hello," + firebaseUser.getEmail());
        } else {
            helloLabel.setText("Hello,Guest");
        }

        featuredWish1 = (ConstraintLayout) getActivity().findViewById(R.id.homeFeaturedWish1);
        featuredWish1.setOnClickListener(this);
        featuredWish2 = (ConstraintLayout) getActivity().findViewById(R.id.homeFeaturedWish2);
        featuredWish2.setOnClickListener(this);
        featuredWish3 = (ConstraintLayout) getActivity().findViewById(R.id.homeFeaturedWish3);
        featuredWish3.setOnClickListener(this);

        activeWish1 = (ConstraintLayout) getActivity().findViewById(R.id.homeActiveWish1);
        activeWish1.setOnClickListener(this);
        activeWish2 = (ConstraintLayout) getActivity().findViewById(R.id.homeActiveWish2);
        activeWish2.setOnClickListener(this);
        activeWish3 = (ConstraintLayout) getActivity().findViewById(R.id.homeActiveWish3);
        activeWish3.setOnClickListener(this);

        wishReference = FirebaseDatabase.getInstance().getReference("wishes");

        return currFragment;
    }

    public void onClick(View v){
        if (v.getId() == appLabel.getId()){
            Intent intent = new Intent(getActivity(),WishActivity.class);
            intent.putExtra("NEW_WISH",true);
            startActivity(intent);
        } else {
            switch (v.getId()){
                case R.id.homeFeaturedWish1:
                    break;
                case R.id.homeFeaturedWish2:
                    break;
                case R.id.homeFeaturedWish3:
                    break;
                case R.id.homeActiveWish1:
                    break;
                case R.id.homeActiveWish2:
                    break;
                case R.id.homeActiveWish3:
                    break;
            }
        }
    }

    public void fillLayoutOf(final ConstraintLayout layout, String wishID){
        if (wishID != null) {
            DatabaseReference fillerReference = wishReference.child(wishID);
            fillerReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ((TextView) layout.getChildAt(1)).setText((String) dataSnapshot.child("title").getValue());
                    ((TextView) layout.getChildAt(2)).setText((String) dataSnapshot.child("country").getValue());
                    ((TextView) layout.getChildAt(3)).setText((String) dataSnapshot.child("price").getValue());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            layout.setVisibility(View.INVISIBLE);
        }
    }

}
