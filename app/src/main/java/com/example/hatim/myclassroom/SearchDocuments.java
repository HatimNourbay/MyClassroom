package com.example.hatim.myclassroom;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hatim on 29/05/2016.
 */
public class SearchDocuments extends AsyncTask< File,File, List<String>>{

    ArrayList<String> docFiles = new ArrayList<String>();
    ArrayList<File> pdfFiles = new ArrayList<File>();



    public List<String> getListFiles(File parentDir) {

        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                docFiles.addAll(getListFiles(file));
            } else {
                if(file.getName().toLowerCase().endsWith(".pdf")){
                    docFiles.add(file.getName());
                    Log.wtf("NEW_FILE",file.getName());

                }
            }
        }
        return docFiles;
    }

    @Override
    protected List<String> doInBackground(File... params) {
        return getListFiles(params[0]);
    }
}
