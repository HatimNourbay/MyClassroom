package com.example.hatim.myclassroom.DocumentChoosing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.example.hatim.myclassroom.DocumentHelper.Document;
import com.example.hatim.myclassroom.DocumentHelper.ManageDocuments;
import com.example.hatim.myclassroom.DocumentHelper.SearchDocuments;
import com.example.hatim.myclassroom.MainActivity;
import com.example.hatim.myclassroom.R;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DocumentPickActivity extends AppCompatActivity {

    private Subscription docSubsript;
    public SearchDocuments searchDocuments;
    public MultiSelector mMultiSelector = new MultiSelector();
    public RecyclerView docRecyclerView;
    public DocAdapter docAdapter;
    public ArrayList<Document> documentList = new ArrayList<>();
    Toolbar toolbar;

    private ProgressDialog progressSearch;
    SharedPreferences myClassPrefs;


    private ModalMultiSelectorCallback mDeleteMode = new ModalMultiSelectorCallback(mMultiSelector) {

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myClassPrefs = getSharedPreferences(getString(R.string.prefs_name),MODE_PRIVATE);

        if (myClassPrefs.getBoolean(getString(R.string.first_connection),true) == false){
            Intent loginStart = new Intent(this, MainActivity.class);
            startActivity(loginStart);
            finish();
        }

        setContentView(R.layout.activity_document_pick);

        toolbar = (Toolbar) findViewById(R.id.toolbar_pick);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setLogo(R.drawable.myclassroomlogo_small);


        searchDocuments = new SearchDocuments(Environment.getExternalStorageDirectory());

        docRecyclerView = (RecyclerView) findViewById(R.id.DocRecycler);
        RecyclerView.LayoutManager aLayoutManager = new LinearLayoutManager(this);
        docRecyclerView.setLayoutManager(aLayoutManager);
        docRecyclerView.setItemAnimator(new DefaultItemAnimator());

        docAdapter = new DocAdapter(documentList,mMultiSelector);
        docRecyclerView.setAdapter(docAdapter);


        if (isExternalStorageReadable()){
            showProgressDialog();
            createObservable();
        }

    }


    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


    public void createObservable() {
        Observable<List<File>> listObservable = Observable.fromCallable(new Callable<List<File>>() {
            @Override
            public List<File> call() throws Exception {
                return searchDocuments.getListFiles(Environment.getExternalStorageDirectory());
            }
        });
        docSubsript = listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<List<File>>() {
                            @Override
                            public void onCompleted() {
                                hideProgressDialog();
                                if (documentList.isEmpty()){

                                }
                            }
                            @Override
                            public void onError(Throwable e) {
                                Log.w("OBSERVE_ERROR",e);
                            }

                            @Override
                            public void onNext(List<File> documents) {
                                for (File f: documents) {
                                    if(f.getName().toLowerCase().endsWith(".pdf")){
                                        Document docToAdd = new Document();
                                        docToAdd.imageDoc = R.drawable.pdf_file;
                                        docToAdd.nameDoc = f.getName();
                                        docToAdd.filePath = f.getAbsolutePath();
                                        documentList.add(docToAdd);
                                        docAdapter.notifyDataSetChanged();
                                    }
                                    else if(f.getName().toLowerCase().endsWith(".doc") | f.getName().toLowerCase().endsWith(".docx")){
                                        Document docToAdd = new Document();
                                        docToAdd.imageDoc = R.drawable.doc_file;
                                        docToAdd.nameDoc = f.getName();
                                        docToAdd.filePath = f.getAbsolutePath();
                                        documentList.add(docToAdd);
                                        docAdapter.notifyDataSetChanged();
                                    }
                                }

                            }});
    }


    public void showProgressDialog() {
        if (progressSearch == null) {
            progressSearch = new ProgressDialog(this);
            progressSearch.setMessage(getString(R.string.searching));
            progressSearch.setIndeterminate(true);
        }

        progressSearch.show();
    }

    public void hideProgressDialog() {
        if (progressSearch != null && progressSearch.isShowing()) {
            progressSearch.hide();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //if (progressSearch != null)
            //progressSearch.dismiss();
    }

    public void onClick(View view) throws SQLException {
        if (!docAdapter.docSelected.isEmpty()){
            ManageDocuments manageDoc = new ManageDocuments(this);
            manageDoc.addDocuments(docAdapter.docSelected);
        }

        myClassPrefs = getSharedPreferences(getString(R.string.prefs_name),MODE_PRIVATE);
        SharedPreferences.Editor editor = myClassPrefs.edit();
        editor.putBoolean(getString(R.string.first_connection),false);
        editor.commit();


        Intent startPicking = new Intent(this, MainActivity.class);
        startActivity(startPicking);
        finish();

    }
}
