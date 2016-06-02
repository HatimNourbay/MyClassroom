package com.example.hatim.myclassroom.DocRecycler;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.example.hatim.myclassroom.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DocFragment extends Fragment {

    private Subscription docSubsript;
    private SearchDocuments searchDocuments;
    private MultiSelector mMultiSelector = new MultiSelector();
    private RecyclerView docRecyclerView;
    private DocAdapter docAdapter;
    private ArrayList<Document> documentList = new ArrayList<>();



    private ModalMultiSelectorCallback mDeleteMode = new ModalMultiSelectorCallback(mMultiSelector) {

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        searchDocuments = new SearchDocuments(Environment.getExternalStorageDirectory());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedDoc = inflater.inflate(R.layout.fragment_doc, container, false);

        docRecyclerView = (RecyclerView) inflatedDoc.findViewById(R.id.DocRecycler);
        RecyclerView.LayoutManager aLayoutManager = new LinearLayoutManager(getContext());
        docRecyclerView.setLayoutManager(aLayoutManager);
        docRecyclerView.setItemAnimator(new DefaultItemAnimator());

        docAdapter = new DocAdapter(documentList,mMultiSelector);
        docRecyclerView.setAdapter(docAdapter);


        if (isExternalStorageReadable()){
            createObservable();
        }
        return inflatedDoc;
    }


    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


    private void createObservable() {
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
                }

            }});
    }


}
