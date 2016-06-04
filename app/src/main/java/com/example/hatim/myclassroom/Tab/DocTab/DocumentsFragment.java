package com.example.hatim.myclassroom.Tab.DocTab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.example.hatim.myclassroom.DataBaseHelper;
import com.example.hatim.myclassroom.DocRecycler.DocAdapter;
import com.example.hatim.myclassroom.DocRecycler.DocFragmentAdapter;
import com.example.hatim.myclassroom.DocRecycler.Document;
import com.example.hatim.myclassroom.DocRecycler.DocumentItemDB;
import com.example.hatim.myclassroom.DocRecycler.ManageDocuments;
import com.example.hatim.myclassroom.R;
import com.example.hatim.myclassroom.Tab.ContactTab.ContactTable;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocumentsFragment extends Fragment {


    private RecyclerView docRecyclerView;
    DocFragmentAdapter docFragmentAdapter;
    private MultiSelector mMultiSelector = new MultiSelector();
    private ArrayList<Document> documentList = new ArrayList<>();

    public DataBaseHelper dataBaseHelper = null;
    private Dao<DocumentItemDB, Integer> documentItemDBs;




    private ModalMultiSelectorCallback mDeleteMode = new ModalMultiSelectorCallback(mMultiSelector) {

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }
    };

    public DocumentsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedDoc = inflater.inflate(R.layout.fragment_documents, container, false);

        docRecyclerView = (RecyclerView) inflatedDoc.findViewById(R.id.DocRecyclerFragment);
        RecyclerView.LayoutManager aLayoutManager = new LinearLayoutManager(getContext());
        docRecyclerView.setLayoutManager(aLayoutManager);
        docRecyclerView.setItemAnimator(new DefaultItemAnimator());

        docFragmentAdapter = new DocFragmentAdapter(documentList,mMultiSelector);
        docRecyclerView.setAdapter(docFragmentAdapter);

        ManageDocuments manageDocuments = new ManageDocuments(container.getContext());

        try {
            documentItemDBs = getHelper().getDocumentDao();
            List<DocumentItemDB> documentInDB = documentItemDBs.queryForAll();

            if (!documentInDB.isEmpty()){
                documentList = manageDocuments.retrieveDatabaseList(documentInDB);
                docFragmentAdapter.notifyDataSetChanged();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inflatedDoc;
    }


    private DataBaseHelper getHelper() {
        if (dataBaseHelper == null) {
            dataBaseHelper = OpenHelperManager.getHelper(getContext(), DataBaseHelper.class);
        }
        return dataBaseHelper;
    }

    @Override
    public void onPause() {
        super.onPause();

        if (dataBaseHelper != null) {
            OpenHelperManager.releaseHelper();
            dataBaseHelper = null;
        }
    }
}
