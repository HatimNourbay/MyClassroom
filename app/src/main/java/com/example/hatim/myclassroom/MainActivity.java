package com.example.hatim.myclassroom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hatim.myclassroom.DocRecycler.DocumentPickActivity;
import com.example.hatim.myclassroom.Drawer.FragmentInsideDrawer;
import com.example.hatim.myclassroom.Log.LoginActivity;
import com.example.hatim.myclassroom.Tab.ContactTab.ContactFragment;
import com.example.hatim.myclassroom.Tab.DocTab.DocumentsFragment;
import com.example.hatim.myclassroom.Tab.ViewPagerAdapter;
import com.example.hatim.myclassroom.Tab.WelcomeTab.WelcomeFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements FragmentInsideDrawer.FragmentDrawerListener {


    FragmentInsideDrawer fragmentInsideDrawer;
    Fragment currentFragment;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.home,
            R.drawable.friendssmall,
            R.drawable.studysmall,
    };

    SharedPreferences myClassPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myClassPrefs = getSharedPreferences(getString(R.string.prefs_name),MODE_PRIVATE);


        if (myClassPrefs.getBoolean(getString(R.string.first_connection),true) == true){
            if (myClassPrefs.getBoolean(getString(R.string.acc_connected),false) == false){
                Intent loginStart = new Intent(this, LoginActivity.class);
                startActivity(loginStart);
                finish();
            }
            Intent startPicking = new Intent(this, DocumentPickActivity.class);
            startActivity(startPicking);
            finish();
        }

        setContentView(R.layout.activity_main);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
        }

        setupTabIcons();

        currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);

        fragmentInsideDrawer = (FragmentInsideDrawer)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        fragmentInsideDrawer.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout),null);
        fragmentInsideDrawer.setDrawerListener(this);



    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(0);
    }

        private void displayView(int position) {
            Intent intent = null;
            switch (position) {
                case 0:
                    intent = new Intent(this, LoginActivity.class);
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    /*Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                }
                            });*/
                    break;
                default:
                    break;
            }
            startActivity(intent);

        }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new WelcomeFragment(), "Welcome");
        adapter.addFragment(new ContactFragment(), "MyFriends");
        adapter.addFragment(new DocumentsFragment(), "MyDocuments");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

}