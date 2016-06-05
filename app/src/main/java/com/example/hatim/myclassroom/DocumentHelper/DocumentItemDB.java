package com.example.hatim.myclassroom.DocumentHelper;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;


@DatabaseTable(tableName = "doc_table")
public class DocumentItemDB implements Serializable{

    private static final long serialVersionUID = 208251231236468L;

    @DatabaseField(generatedId = true, columnName = "doc_id", canBeNull = false)
    public int id;

    @DatabaseField(columnName = "doc_type")
    public int photoType;

    @DatabaseField(columnName = "doc_name")
    public String nameDoc;

    @DatabaseField(columnName = "doc_path")
    public String pathDoc;

    // Default constructor is needed for the SQLite
    public DocumentItemDB(){

    }

    public DocumentItemDB(final int photoType, final String pathDoc, final String nameDoc){
        this.photoType = photoType;
        this.nameDoc = nameDoc;
        this.pathDoc = pathDoc;

    }



}