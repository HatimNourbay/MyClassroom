package com.example.hatim.myclassroom.DocumentChoosing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hatim.myclassroom.DocumentHelper.ManageDocuments;
import com.example.hatim.myclassroom.DocumentHelper.SearchDocuments;
import com.example.hatim.myclassroom.R;

import java.sql.SQLException;

/**
 * Created by Hatim on 06/06/2016.
 */
public class DocPickAdding extends DocumentPickActivity {



    @Override
    public void onClick(View view) throws SQLException {
        if (!docAdapter.docSelected.isEmpty()){
            ManageDocuments manageDoc = new ManageDocuments(this);
            manageDoc.addDocuments(docAdapter.docSelected);
        }

        SharedPreferences.Editor editor = myClassPrefs.edit();
        editor.putBoolean(getString(R.string.first_connection),false);
        editor.commit();

        Intent resultIntent = new Intent();
        setResult(203, resultIntent);
        finish();


    }
}
