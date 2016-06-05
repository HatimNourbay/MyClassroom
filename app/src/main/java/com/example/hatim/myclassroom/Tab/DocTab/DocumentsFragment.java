package com.example.hatim.myclassroom.Tab.DocTab;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.example.hatim.myclassroom.DatabaseParams.DataBaseHelper;
import com.example.hatim.myclassroom.DocumentChoosing.DocumentPickActivity;
import com.example.hatim.myclassroom.DocumentHelper.Document;
import com.example.hatim.myclassroom.DocumentHelper.ManageDocuments;
import com.example.hatim.myclassroom.R;
import com.example.hatim.myclassroom.Tab.ContactTab.ContactAddActivity;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;


public class DocumentsFragment extends Fragment {


    private RecyclerView docRecyclerView;
    DocFragmentAdapter docFragmentAdapter;
    private MultiSelector mMultiSelector = new MultiSelector();
    private ArrayList<Document> documentList = new ArrayList<>();
    FloatingActionButton floatingActionButton;

    public DataBaseHelper dataBaseHelper = null;
    ManageDocuments manageDocuments = new ManageDocuments(getContext());

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

        try {
            documentList = manageDocuments.retrieveDatabaseList(getHelper().getDocumentDao());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedDoc = inflater.inflate(R.layout.fragment_documents, container, false);

        docRecyclerView = (RecyclerView) inflatedDoc.findViewById(R.id.DocRecyclerFragment);
        RecyclerView.LayoutManager aLayoutManager = new LinearLayoutManager(getContext());
        docRecyclerView.setLayoutManager(aLayoutManager);
        docRecyclerView.setItemAnimator(new DefaultItemAnimator());

        floatingActionButton = (FloatingActionButton) inflatedDoc.findViewById(R.id.add_new_doc);

        docFragmentAdapter = new DocFragmentAdapter(documentList,mMultiSelector);
        docRecyclerView.setAdapter(docFragmentAdapter);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DocumentPickActivity.class);
                getActivity().startActivity(intent);
            }
        });

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
