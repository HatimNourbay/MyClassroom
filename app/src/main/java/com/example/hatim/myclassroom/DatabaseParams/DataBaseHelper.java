package com.example.hatim.myclassroom.DatabaseParams;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hatim.myclassroom.DocumentHelper.DocumentItemDB;
import com.example.hatim.myclassroom.R;
import com.example.hatim.myclassroom.Tab.ContactTab.ContactTable;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Richard on 30/05/2016.
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "classroomDataBase.db";
    private static final int DATABASE_VERSION = 1;
    public SQLiteDatabase sqliteDatabase;

    private Dao<ContactTable, Integer> contactDao = null;
    private Dao<DocumentItemDB, Integer> documentDao = null;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            // lors de la première ouverture de l'app
            TableUtils.createTable(connectionSource, ContactTable.class);
            TableUtils.createTable(connectionSource, DocumentItemDB.class);

        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Unable to create databases", e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {

            // mettre à jour la db
            TableUtils.dropTable(connectionSource, ContactTable.class, true);
            TableUtils.dropTable(connectionSource, DocumentItemDB.class, true);
            onCreate(sqliteDatabase, connectionSource);

        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Unable to upgrade database from version " + oldVersion + " to new "
                    + newVersion, e);
        }

    }

    public Dao<ContactTable, Integer> getContactDao() throws SQLException {
        if (contactDao == null) {
            contactDao = getDao(ContactTable.class);
        }
        return contactDao;
    }

    public Dao<DocumentItemDB, Integer> getDocumentDao() throws SQLException {
        if (documentDao == null) {
            documentDao = getDao(DocumentItemDB.class);
        }
        return documentDao;
    }
}
