package com.example.orkunsoylu;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.orkunsoylu.bringit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment {
    private TextView helloLabel;
    private FirebaseUser firebaseUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View currFragment = inflater.inflate(R.layout.fragment_home, container, false);

        helloLabel = (TextView) currFragment.findViewById(R.id.homeHelloLabel);
        firebaseUser = ((MainActivity) getActivity()).returnUser();
        if (firebaseUser != null) {
            helloLabel.setText("Hello," + firebaseUser.getEmail());
        } else {
            helloLabel.setText("Hello,Guest");
        }

        return currFragment;
    }

}
