package com.example.hatim.myclassroom.Tab.DocTab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.example.hatim.myclassroom.DatabaseParams.DataBaseHelper;
import com.example.hatim.myclassroom.DocumentChoosing.DocAdapter;
import com.example.hatim.myclassroom.DocumentChoosing.DocPickAdding;
import com.example.hatim.myclassroom.DocumentChoosing.DocumentPickActivity;
import com.example.hatim.myclassroom.DocumentHelper.Document;
import com.example.hatim.myclassroom.DocumentHelper.ManageDocuments;
import com.example.hatim.myclassroom.MainActivity;
import com.example.hatim.myclassroom.MyClassApplication;
import com.example.hatim.myclassroom.R;
import com.example.hatim.myclassroom.Tab.ContactTab.ContactAddActivity;
import com.example.hatim.myclassroom.Tab.OnBackPressedListener;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.w3c.dom.DocumentFragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DocumentsFragment extends Fragment implements OnBackPressedListener{


    private RecyclerView docRecyclerView;
    DocFragmentAdapter docFragmentAdapter;
    private MultiSelector mMultiSelector = new MultiSelector();
    private ArrayList<Document> documentList = new ArrayList<>();
    FloatingActionButton floatingActionButton;
    SharedPreferences myClassPrefs;
    Button deleteButton;
    Button shareButton;
    Integer pressed = 0;

    private View hiddenPanel;
    SlidingHelper slideHelper;

    public DataBaseHelper dataBaseHelper = null;
    ManageDocuments manageDocuments = new ManageDocuments(getContext());


    private ModalMultiSelectorCallback mDeleteMode = new ModalMultiSelectorCallback(mMultiSelector) {


        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            mode.finish();

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

        hiddenPanel = inflatedDoc.findViewById(R.id.hidden_panel);

        slideHelper = new SlidingHelper(hiddenPanel);

        floatingActionButton = (FloatingActionButton) inflatedDoc.findViewById(R.id.add_new_doc);

        docFragmentAdapter = new DocFragmentAdapter(documentList,mMultiSelector);
        docFragmentAdapter.slideHelper = this.slideHelper;

        docRecyclerView.setAdapter(docFragmentAdapter);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myClassPrefs = v.getContext().getSharedPreferences(getString(R.string.prefs_name),v.getContext().MODE_PRIVATE);

                SharedPreferences.Editor editor = myClassPrefs.edit();
                editor.putBoolean(getString(R.string.first_connection),true);
                editor.commit();

                Intent i = new Intent(v.getContext(),DocPickAdding.class);
                startActivityForResult(i, 203);
            }
        });

        shareButton = (Button)inflatedDoc.findViewById(R.id.share);
        deleteButton = (Button)inflatedDoc.findViewById(R.id.delete);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "I am using MyClassroom !!" + mMultiSelector.getSelectedPositions().toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> docToDeletePosition = mMultiSelector.getSelectedPositions();
                List<Document> docDelete = new ArrayList<>();
                for (Integer i : docToDeletePosition )
                {
                    Integer bb = documentList.size();
                    Log.wtf("existOrNot",bb.toString());

                    Document doc1 = documentList.get(i);

                    docDelete.add(doc1);
                }
                if (!docDelete.isEmpty()){
                    try {
                        manageDocuments.deleteDocuments(docDelete);
                        documentList = manageDocuments.retrieveDatabaseList(getHelper().getDocumentDao());
                        docFragmentAdapter.notifyDataSetChanged();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return inflatedDoc;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 203){
            try {
                documentList = manageDocuments.retrieveDatabaseList(getHelper().getDocumentDao());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            docFragmentAdapter.notifyDataSetChanged();
        }
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

    @Override
    public void onBackPressed() {
        pressed++;
        if (slideHelper.isPanelShown()){
            slideHelper.slideUpDown(hiddenPanel);
            mMultiSelector.clearSelections();
        }
        else{
            if (pressed == 2){
                getActivity().finish();
                pressed = 0;
            }
        }

    }

}
