package com.example.orkunsoylu;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.orkunsoylu.bringit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity {
    private FirebaseAuth mAuth;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private String[] drawerTitles;
    private ActionBarDrawerToggle drawerToggle;
    private int currentFragment = 0;
    Fragment fragmentToOpen;
    private FirebaseUser firebaseUser;


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectFragment(position);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        String userID = getIntent().getStringExtra("USER_ID");
        if (!userID.equals("0")){
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        }

        drawerTitles = new String[4];
        drawerTitles[0] = "Home";
        drawerTitles[1] = "Search";
        drawerTitles[2] = "Profile";
        drawerTitles[3] = "o";

        drawerLayout = (DrawerLayout) findViewById(R.id.homeDrawerLayout);
        drawerList = (ListView) findViewById(R.id.homeLeftDrawer);

        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, drawerTitles));

        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);

        if (savedInstanceState != null) {
            currentFragment = savedInstanceState.getInt("position");

        } else if (userID.equals("0")){
            selectFragment(0);
        }

        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        FragmentManager fragMan = getFragmentManager();
                        Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
                        if (fragment instanceof HomeFragment) {
                            currentFragment = 0;
                        }
                        if (fragment instanceof SearchFragment) {
                            currentFragment = 1;
                        }
                        if (fragment instanceof ProfileFragment) {
                            currentFragment = 2;
                        }

                        drawerList.setItemChecked(currentFragment, true);
                    }
                }
        );

        selectFragment(0);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectFragment(int position) {

        switch (position) {
            case 1:
                fragmentToOpen = new SearchFragment();
                break;
            case 2:
                fragmentToOpen = new ProfileFragment();
                break;
            default:
                fragmentToOpen = new HomeFragment();
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.homeFrameLayout, fragmentToOpen, "visible_fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        drawerList.setItemChecked(position, true);

        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        String tempEmail = data.getStringExtra("EMAIL");
        String tempPassword = data.getStringExtra("PASS");
        mAuth.signInWithEmailAndPassword(tempEmail, tempPassword)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        }
                    }
                });
        super.onActivityResult(requestCode, resultCode, data);
    }

    public FirebaseUser returnUser(){
        return firebaseUser;
    }
}
