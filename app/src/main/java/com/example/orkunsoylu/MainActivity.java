package com.example.orkunsoylu;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.orkunsoylu.bringit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity {
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private String[] drawerTitles;
    private ActionBarDrawerToggle drawerToggle;
    private int currentFragment = 0;
    Fragment fragmentToOpen;

    private String userID;

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectFragment(position);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();



        drawerTitles = new String[4];
        drawerTitles[0] = "Home";
        drawerTitles[1] = "Search";
        drawerTitles[2] = "Profile";

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
            setActionBarTitle(currentFragment);
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
                        setActionBarTitle(currentFragment);
                        drawerList.setItemChecked(currentFragment, true);
                    }
                }
        );
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
        setActionBarTitle(position);
        drawerLayout.closeDrawer(drawerList);
    }

    public void setActionBarTitle(int position) {
        String title;
        if (position == 0) {
            title = "TopFragment";
        } else {
            title = drawerTitles[position];
        }
        getActionBar().setTitle(title);
    }
}
