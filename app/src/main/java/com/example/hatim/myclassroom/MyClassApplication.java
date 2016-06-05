package com.example.hatim.myclassroom;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

public class MyClassApplication extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {


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

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {



    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

        if (activity instanceof MainActivity){



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
}
