package com.example.hatim.myclassroom.DocRecycler;

import android.content.Context;

import com.example.hatim.myclassroom.DataBaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hatim on 04/06/2016.
 */
public class ManageDocuments {

    private DataBaseHelper databaseHelper = null;

    Context context;

    public ManageDocuments(Context usedContext){
        this.context = usedContext;
    }


    public void addDocuments(ArrayList<Document> DocToPersist) throws SQLException {
        for (Document doc : DocToPersist) {
            DocumentItemDB documentItemDB = new DocumentItemDB();

            documentItemDB.nameDoc = doc.nameDoc;
            documentItemDB.photoType = doc.imageDoc;
            documentItemDB.pathDoc = doc.filePath;

            Dao<DocumentItemDB,Integer> documentDao = getHelper().getDocumentDao();

            documentDao.create(documentItemDB);
        }
    }

    public ArrayList<Document> retrieveDatabaseList(List<DocumentItemDB> docDB){

        ArrayList<Document> retrievedDocuments = new ArrayList<>();

        for (DocumentItemDB docDb: docDB) {

            Document docToAdd = new Document();
            docToAdd.nameDoc = docDb.nameDoc;
            docToAdd.filePath = docDb.pathDoc;
            docToAdd.imageDoc = docDb.photoType;

            retrievedDocuments.add(docToAdd);

        }
        return retrievedDocuments;
    }



    private DataBaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this.context,DataBaseHelper.class);
        }
        return databaseHelper;
    }
}
