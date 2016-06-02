package com.example.hatim.myclassroom;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

/**
 * Created by Hatim on 29/05/2016.
 */
public class MyClassApplication extends Application {



    //SearchDocuments searchDocuments = new SearchDocuments(Environment.getExternalStorageDirectory());

    @Override
    public void onCreate() {
        super.onCreate();

        /*if (isExternalStorageReadable()){
            searchDocuments.execute(Environment.getExternalStorageDirectory());
        }*/

    }


    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

}
