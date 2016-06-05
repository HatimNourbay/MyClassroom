package com.example.hatim.myclassroom.Tab.DocTab;

import android.content.Context;
import android.content.Intent;
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
import com.example.hatim.myclassroom.DocumentChoosing.DocumentPickActivity;
import com.example.hatim.myclassroom.DocumentHelper.Document;
import com.example.hatim.myclassroom.DocumentHelper.ManageDocuments;
import com.example.hatim.myclassroom.MainActivity;
import com.example.hatim.myclassroom.MyClassApplication;
import com.example.hatim.myclassroom.R;
import com.example.hatim.myclassroom.Tab.ContactTab.ContactAddActivity;
import com.example.hatim.myclassroom.Tab.OnBackPressedListener;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DocumentsFragment extends Fragment implements OnBackPressedListener{


    private RecyclerView docRecyclerView;
    DocFragmentAdapter docFragmentAdapter;
    private MultiSelector mMultiSelector = new MultiSelector();
    private ArrayList<Document> documentList = new ArrayList<>();
    FloatingActionButton floatingActionButton;
    Button deleteButton;
    Button shareButton;

    private View hiddenPanel;

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
                ArrayList<Document> docDelete = null;
                /*for (Integer i : docToDeletePosition )
                {
                    Integer bb = documentList.size();
                    Log.wtf("existOrNot",bb.toString());

                    docDelete.add(documentList.get(i));
                }
                if (!docDelete.isEmpty()){
                    try {
                        manageDocuments.deleteDocuments(docDelete);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }*/
            }
        });

        return inflatedDoc;
    }

    public void slideUpDown(final View view) {
        if (!isPanelShown()) {
            // Show the panel
            Animation bottomUp = AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.slide_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
        }
        else {
            // Hide the Panel
            Animation bottomDown = AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.slide_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.GONE);
        }
    }

    private boolean isPanelShown() {
        return hiddenPanel.getVisibility() == View.VISIBLE;
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
        if (isPanelShown()){
            slideUpDown(hiddenPanel);
        }
        mMultiSelector.clearSelections();
    }


    public class DocFragmentAdapter extends DocAdapter {

        private MultiSelector mMultiSelector;

        public DocFragmentAdapter(ArrayList<Document> oncomingDocs, MultiSelector multiselectFrag) {
            super(oncomingDocs, multiselectFrag);

            mMultiSelector = multiselectFrag;
        }

        @Override
        public DoclHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.document_layout, parent, false);

            return new DoclHolder(itemView,mMultiSelector);
        }

        public class DoclHolder extends DocAdapter.DocHolder{

            public DoclHolder(View itemView, MultiSelector multiSelector) {
                super(itemView, multiSelector);
            }


            @Override
            public void onClick(View v) {
            }

            @Override
            public boolean onLongClick(View v) {

                //((AppCompatActivity) getActivity()).startSupportActionMode(mDeleteMode);

                if (!isPanelShown()){
                    slideUpDown(hiddenPanel);
                }
                mMultiSelector.setSelected(this,true);
                return true;
            }
        }



    }

}
