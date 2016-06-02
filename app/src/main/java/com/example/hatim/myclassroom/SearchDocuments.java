package com.example.hatim.myclassroom;

import android.os.AsyncTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.subjects.PublishSubject;

/**
 * Created by Hatim on 29/05/2016.
 */
public class SearchDocuments{


   // public PublishSubject<String> docPushed;
    File rootDir;
    public ArrayList<String> pdfList;

    public SearchDocuments(File directoryToPick){
        rootDir = directoryToPick;
        pdfList = new ArrayList<>();
    }

    public List<File> getListFiles(File parentDir)
    {
        List<File> resultList = new ArrayList<>();

        File[] fList = parentDir.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
                if(file.getName().toLowerCase().endsWith(".pdf")){
                    //docPushed.onNext(file.getName());
                }
            } else if (file.isDirectory()) {

                resultList.addAll(getListFiles(file));
            }
        }
        return resultList;
    }

}
