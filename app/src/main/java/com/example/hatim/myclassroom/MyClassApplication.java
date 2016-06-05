package com.example.hatim.myclassroom;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.HeaderViewListAdapter;

import com.example.hatim.myclassroom.DatabaseParams.DataBaseHelper;
import com.example.hatim.myclassroom.DocumentHelper.Document;
import com.example.hatim.myclassroom.DocumentHelper.DocumentItemDB;
import com.example.hatim.myclassroom.DocumentHelper.ManageDocuments;
import com.example.hatim.myclassroom.Tab.DocTab.OnHeadlineSelectedListener;
import com.example.hatim.myclassroom.Tab.WelcomeTab.WelcomeFragment;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

public class MyClassApplication extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {


    private DataBaseHelper dataBaseHelper = null;
    private Dao<DocumentItemDB, Integer> documentItemDBs;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {



    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

        if (activity instanceof MainActivity){

            ManageDocuments manage = new ManageDocuments(this);
            try {
                documentItemDBs = getHelper().getDocumentDao();
                ArrayList<Document> dcoumentDb = manage.retrieveDatabaseList(documentItemDBs);

                WelcomeFragment welcomeFragment = (WelcomeFragment)((MainActivity) activity).getSupportFragmentManager().findFragmentById(R.id.welcome_fragment);
                //welcomeFragment.



            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


    private DataBaseHelper getHelper() {
        if (dataBaseHelper == null) {
            dataBaseHelper = OpenHelperManager.getHelper(this, DataBaseHelper.class);
        }
        return dataBaseHelper;
    }
}
