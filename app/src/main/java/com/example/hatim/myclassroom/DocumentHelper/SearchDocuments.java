package com.example.hatim.myclassroom.DocumentHelper;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



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
                }
            } else if (file.isDirectory()) {

                resultList.addAll(getListFiles(file));
            }
        }
        return resultList;
    }

}
