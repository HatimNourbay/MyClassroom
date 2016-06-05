package com.example.hatim.myclassroom.DocumentHelper;

import android.content.Context;

import com.example.hatim.myclassroom.DatabaseParams.DataBaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ManageDocuments {

    private DataBaseHelper databaseHelper = null;

    Context context;

    private Dao<DocumentItemDB, Integer> documentItemDBs;

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

    public ArrayList<Document> retrieveDatabaseList(Dao<DocumentItemDB, Integer> DocDao){


        documentItemDBs = DocDao;
        ArrayList<Document> retrievedDocuments = new ArrayList<>();
        try {
            List<DocumentItemDB> documentInDB = documentItemDBs.queryForAll();

            if (!documentInDB.isEmpty()){
                for (DocumentItemDB docDb: documentInDB) {

                    Document docToAdd = new Document();
                    docToAdd.nameDoc = docDb.nameDoc;
                    docToAdd.filePath = docDb.pathDoc;
                    docToAdd.imageDoc = docDb.photoType;

                    retrievedDocuments.add(docToAdd);

                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
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
