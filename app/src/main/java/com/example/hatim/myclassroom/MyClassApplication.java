package com.example.hatim.myclassroom;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

public class MyClassApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        //SharedPreferences myClassPrefs = getSharedPreferences(getString(R.string.prefs_name),MODE_PRIVATE);

        /*if (myClassPrefs.contains(getString(R.string.first_connection))){
            SharedPreferences.Editor editor = myClassPrefs.edit();
            editor.putBoolean(getString(R.string.acc_connected),true);
            editor.commit();
        }*/

    }

}
