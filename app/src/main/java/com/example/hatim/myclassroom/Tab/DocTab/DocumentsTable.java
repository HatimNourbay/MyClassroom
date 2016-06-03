package com.example.hatim.myclassroom.Tab.DocTab;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Richard on 01/06/2016.
 */
@DatabaseTable(tableName = "contact_table")
public class DocumentsTable implements Serializable{

    private static final long serialVersionUID = 208251239926468L;

    @DatabaseField(generatedId = true, columnName = "doc_id", canBeNull = false)
    public int id;

    @DatabaseField(columnName = "doc_type")
    public int photoType;

    @DatabaseField(columnName = "doc_name")
    public String nameDoc;

    @DatabaseField(columnName = "doc_path")
    public String pathDoc;

    // Default constructor is needed for the SQLite
    public DocumentsTable(){

    }

    public DocumentsTable(final int photoType, final String pathDoc, final String nameDoc){
        this.photoType = photoType;
        this.nameDoc = nameDoc;
        this.pathDoc = pathDoc;

    }



}