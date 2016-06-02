package com.example.hatim.myclassroom.Tab.ContactTab;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Richard on 01/06/2016.
 */
@DatabaseTable(tableName = "contact_table")
public class ContactTable implements Serializable{

    private static final long serialVersionUID = 208251239926468L;

    @DatabaseField(generatedId = true, columnName = "contact_id", canBeNull = false)
    public int id;

    @DatabaseField(columnName = "contact_photo")
    public int photo;

    @DatabaseField(columnName = "contact_prenom")
    public String prenom;

    @DatabaseField(columnName = "contact_nom")
    public String nom;

    // Default constructor is needed for the SQLite
    public ContactTable(){

    }

    public ContactTable(final int photo, final String nom, final String prenom){
        this.photo = photo;
        this.prenom = prenom;
        this.nom = nom;

    }



}
